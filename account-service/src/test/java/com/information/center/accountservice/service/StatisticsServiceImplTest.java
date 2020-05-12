package com.information.center.accountservice.service;

import com.information.center.accountservice.repository.AccountRepositoryImpl;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    private static final String YEAR = "2019";
    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Mock
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void findAllByYearForDays_expectedResultReturnedByDays() {
        List<String> metrics = new ArrayList<>();
        metrics.add("days");
        when(accountRepository.findAllByYearForDays(YEAR)).thenReturn(resultsByDays());
        var result = statisticsService.getAnalytics(YEAR, metrics);
        assertEquals(13L, result.getPerDays().get(13L).longValue());
    }

    @Test
    public void findAllByYearForWeeks_expectedResultReturnedByWeeks() {
        List<String> metrics = new ArrayList<>();
        metrics.add("weeks");
        when(accountRepository.findAllByYearForWeeks(YEAR)).thenReturn(resultsByWeeks());
        var result = statisticsService.getAnalytics(YEAR, metrics);
        assertEquals(14L, result.getPerWeeks().get(14L).longValue());
    }

    @Test
    public void findAllByYearForWeeksAndDays_expectedResultReturnedByWeeksAndDays() {
        List<String> metrics = new ArrayList<>();
        metrics.add("weeks");
        metrics.add("days");
        when(accountRepository.findAllByYearForDays(YEAR)).thenReturn(resultsByDays());
        when(accountRepository.findAllByYearForWeeks(YEAR)).thenReturn(resultsByWeeks());
        var result = statisticsService.getAnalytics(YEAR, metrics);
        assertEquals(14L, result.getPerWeeks().get(14L).longValue());
        assertEquals(13L, result.getPerDays().get(13L).longValue());
    }

    private Map<Long, Long> resultsByDays() {
        var statisticsByDays = new HashMap<Long, Long>();
        statisticsByDays.put(13L, 13L);
        return statisticsByDays;
    }

    private Map<Long, Long> resultsByWeeks() {
        var statisticsByWeeks = new HashMap<Long, Long>();
        statisticsByWeeks.put(14L, 14L);
        return statisticsByWeeks;
    }
}
