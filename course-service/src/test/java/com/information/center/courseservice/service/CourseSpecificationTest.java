package com.information.center.courseservice.service;

import com.information.center.courseservice.entity.CourseEntity;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseSpecificationTest {

	public static final String KEY = "key";
	public static final String VALUE = "value";
	private CourseSpecification courseSpecification;
	private Root<CourseEntity> root;
	private CriteriaQuery<?> criteriaQuery;
	private CriteriaBuilder builder;
	private Path<String> stringPath;
	private Predicate predicate;

	@Before
	public void setUp() {
		SearchCriteria searchCriteria = new SearchCriteria(KEY, FilterOperation.EQUALS, VALUE);
		courseSpecification = new CourseSpecification(searchCriteria);

		root = mock(Root.class);
		criteriaQuery = mock(CriteriaQuery.class);
		builder = mock(CriteriaBuilder.class);
		stringPath = mock(Path.class);
		predicate = mock(Predicate.class);
	}

	@Test
	public void testToPredicateForEqualsOperation() {
		when(root.<String>get(KEY)).thenReturn(stringPath);
		when(builder.equal(stringPath, VALUE)).thenReturn(predicate);

		Predicate response = courseSpecification.toPredicate(root, criteriaQuery, builder);

		assertEquals(predicate, response);
	}

	@Test
	public void testToPredicateForNotEqualsOperation() {
		SearchCriteria searchCriteria = new SearchCriteria(KEY, null, VALUE);
		courseSpecification = new CourseSpecification(searchCriteria);

		Predicate response = courseSpecification.toPredicate(root, criteriaQuery, builder);

		assertNull(response);
	}
}
