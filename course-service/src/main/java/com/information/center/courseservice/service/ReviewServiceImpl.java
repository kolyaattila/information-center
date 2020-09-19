package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.ReviewConverter;
import com.information.center.courseservice.entity.CourseEntity;
import com.information.center.courseservice.entity.ReviewEntity;
import com.information.center.courseservice.model.RatingDto;
import com.information.center.courseservice.model.request.ReviewRequest;
import com.information.center.courseservice.repository.CourseRepository;
import com.information.center.courseservice.repository.ReviewRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private ReviewConverter reviewConverter;

    @Autowired
    public void setReviewConverter(ReviewConverter reviewConverter) {
        this.reviewConverter = reviewConverter;
    }

    @Override
    public void reviewCourse(ReviewRequest reviewRequest) {
        CourseEntity courseEntity = findByCourseExternalId(reviewRequest.getCourseExternalId());
        ReviewEntity reviewEntity;
        Optional<ReviewEntity> optionalReviewEntity = reviewRepository.findByCourseExternalIdAndAccountExternalId(
                reviewRequest.getCourseExternalId(), reviewRequest.getAccountExternalId());

        if (optionalReviewEntity.isPresent()) {
            reviewEntity = optionalReviewEntity.get();
            reviewEntity.setRating(reviewRequest.getRating());
        } else {
            reviewEntity = reviewConverter.toEntity(reviewRequest);
            reviewEntity.setCourse(courseEntity);
            reviewEntity.setExternalId(getUid());
        }
        try {
            reviewRepository.save(reviewEntity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException(e.getMessage());
        }
    }

    @Override
    public RatingDto getRating(CourseEntity entity) {
        RatingDto ratingDto = new RatingDto();
        int count = entity.getReviews().size();
        float average = calculateAverage(entity);

        ratingDto.setFiveStarsReviews(getNumberOfReviewsByStar(entity, 5));
        ratingDto.setFourStarsReviews(getNumberOfReviewsByStar(entity, 4));
        ratingDto.setThreeStarsReviews(getNumberOfReviewsByStar(entity, 3));
        ratingDto.setTwoStarsReviews(getNumberOfReviewsByStar(entity, 2));
        ratingDto.setOneStarsReviews(getNumberOfReviewsByStar(entity, 1));
        ratingDto.setNumberOfReviews(count);
        ratingDto.setAverage(average);
        return ratingDto;
    }

    private float calculateAverage(CourseEntity entity) {
        return (float) entity.getReviews()
                .stream()
                .map(ReviewEntity::getRating)
                .mapToInt(Integer::intValue)
                .summaryStatistics()
                .getAverage();
    }

    private int getNumberOfReviewsByStar(CourseEntity course, int rating) {
        return (int) course.getReviews()
                .stream()
                .filter(reviewEntity -> reviewEntity.getRating() == rating)
                .count();
    }

    private CourseEntity findByCourseExternalId(String externalId) {
        return courseRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceExceptions.NotFoundException("Course with external id " + externalId + " not found"));
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (reviewRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
