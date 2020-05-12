package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.Analytics;
import com.information.center.accountservice.service.StatisticsService;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {

    private static final String YEAR = "2019";
    @InjectMocks
    private StatisticsController statisticsController;

    @Mock
    private StatisticsService statisticsService;
    private Analytics analytics;

    @Before
    public void setUp() {
        analytics = Analytics.builder()
                .perDays(new HashMap<>())
                .perWeeks(new HashMap<>())
                .build();
    }

    @Test
    public void getAnalytics() {
        List<String> metrics = new ArrayList<>();
        metrics.add("weeks");
        metrics.add("days");
        when(statisticsService.getAnalytics(YEAR, metrics)).thenReturn(analytics);
        var response = statisticsController.getAnalytics(YEAR, metrics);
        assertNotNull(response.getPerDays());
        assertNotNull(response.getPerWeeks());
    }
}
