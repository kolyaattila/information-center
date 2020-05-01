package com.information.center.quizservice.service;

import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CrunchifyTimeDiffTest {

    private Date startDate;
    private Date endDate;
    private CrunchifyTimeDiff crunchifyTimeDiff = new CrunchifyTimeDiff();

    @Before
    public void setUp() throws ParseException {
        startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        endDate = calendar.getTime();
    }

    @Test
    public void dateDifference_expectedDateDifference() {
        var timeDifference = crunchifyTimeDiff.dateDifference(startDate, endDate);
        assertEquals(String.valueOf(100), timeDifference);
    }
}
