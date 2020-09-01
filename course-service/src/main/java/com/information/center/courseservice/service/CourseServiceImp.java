package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.CourseConverter;
import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.model.CourseDetailsDto;
import com.information.center.courseservice.model.CourseDto;
import com.information.center.courseservice.model.RatingDto;
import com.information.center.courseservice.model.request.CourseRequest;
import com.information.center.courseservice.model.request.FilterCourseRequest;
import com.information.center.courseservice.repository.CourseRepository;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.information.center.courseservice.service.FilterOperation.EQUALS;
import static org.apache.commons.lang.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class CourseServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final ReviewService reviewService;
    private CourseConverter courseConverter;

    @Autowired
    public void setCourseConverter(CourseConverter courseConverter) {
        this.courseConverter = courseConverter;
    }

    @Override
    public void saveCourse(CourseRequest courseRequest) {
        CourseEntity courseEntity = courseConverter.toEntity(courseRequest);

        courseEntity.setExternalId(getUid());
        courseEntity.setAuthor(getCurrentUser());

        try {
            courseRepository.save(courseEntity);
        } catch (Exception e) {
            throw new InsertFailedException(e.getMessage());
        }
    }

    @Override
    public void updateCourse(CourseRequest courseRequest) {
        CourseEntity courseEntity = getCourseEntity(courseRequest.getExternalId());

        courseEntity.setName(courseRequest.getName());
        courseEntity.setDescription(courseRequest.getDescription());
        courseEntity.setEnable(courseRequest.isEnable());
        courseEntity.setPrice(courseRequest.getPrice());

        try {
            courseRepository.save(courseEntity);
        } catch (Exception e) {
            throw new InsertFailedException(e.getMessage());
        }
    }

    @Override
    public void deleteCourse(String externalId) {
        CourseEntity courseEntity = getCourseEntity(externalId);
        courseRepository.delete(courseEntity);
    }

    @Override
    public Page<CourseDto> filterCourse(FilterCourseRequest filterCourseRequest) {
        List<CourseSpecification> courseSpecifications = getQuestionSpecifications(filterCourseRequest);
        Specification<CourseEntity> specification = getSpecification(courseSpecifications);
        return courseRepository.findAll(specification, PageRequest.of(filterCourseRequest.getPageNumber(), filterCourseRequest.getPageSize()))
                .map(this::toDtoWithRating);
    }

    @Override
    public CourseDetailsDto getCourseById(String externalId) {
        CourseEntity courseEntity = getCourseEntity(externalId);
        CourseDetailsDto courseDetailsDto = toDetailsDtoWithRating(courseEntity);
        courseDetailsDto.setNumberTopics(courseEntity.getTopics().size());

        return courseDetailsDto;
    }

    @Override
    public List<CourseDto> getAllActiveCourses() {
        return courseRepository.findAllByEnable(true)
                .stream()
                .map(this::toDtoWithRating)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toDtoWithRating)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getBestCourses() {
        return courseRepository.bestCourses()
                .stream()
                .map(this::toDtoWithRating)
                .collect(Collectors.toList());
    }

    private CourseDto toDtoWithRating(CourseEntity entity) {
        RatingDto rating = reviewService.getRating(entity);
        CourseDto courseDto = courseConverter.toDto(entity);
        courseDto.setRating(rating);
        return courseDto;
    }

    private CourseDetailsDto toDetailsDtoWithRating(CourseEntity entity) {
        RatingDto rating = reviewService.getRating(entity);
        CourseDetailsDto courseDetailsDto = courseConverter.toDetailsDto(entity);
        courseDetailsDto.setRating(rating);
        return courseDetailsDto;
    }

    private List<CourseSpecification> getQuestionSpecifications(FilterCourseRequest filterCourseRequest) {
        List<CourseSpecification> specifications = new ArrayList<>();

        if (!isBlank(filterCourseRequest.getExternalId()))
            specifications.add(getCourseSpecification("externalId", EQUALS, filterCourseRequest.getExternalId()));

        if (!isBlank(filterCourseRequest.getName()))
            specifications.add(getCourseSpecification("name", EQUALS, filterCourseRequest.getName()));

        if (filterCourseRequest.isPriceSet())
            specifications.add(getCourseSpecification("price", EQUALS, filterCourseRequest.getPrice()));

        specifications.add(getCourseSpecification("enable", EQUALS, filterCourseRequest.isEnable()));

        return specifications;
    }

    private CourseSpecification getCourseSpecification(String key, FilterOperation operation, Object value) {
        return new CourseSpecification(new SearchCriteria(key, operation, value));
    }

    private Specification<CourseEntity> getSpecification(List<CourseSpecification> courseSpecifications) {
        if (courseSpecifications.isEmpty()) {
            return null;
        } else if (courseSpecifications.size() > 1) {
            Specification<CourseEntity> result = courseSpecifications.get(0);
            for (Specification<CourseEntity> spec : courseSpecifications) {
                result = Specification.where(result).and(spec);
            }
            return result;
        } else {
            return courseSpecifications.get(0);
        }
    }

    private CourseEntity getCourseEntity(String externalId) {
        return courseRepository.findByExternalId(externalId).orElseThrow(
                () -> new NotFoundException("Course with external id: " + externalId + "not found"));
    }

    private String getCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (courseRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
