package com.information.center.quizservice.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CrunchifyTimeDiff {

    public String dateDifference(Date startDate, Date endDate) {

        long diff = endDate.getTime() - startDate.getTime();

        int diffhours = (int) (diff / (60 * 60 * 1000));

        int diffmin = (int) (diff / (60 * 1000));

        int diffsec = (int) (diff / (1000));

        return finalDateToString(diffhours, diffmin, diffsec);
    }

    private String finalDateToString(int hours, int minutes, int seconds) {
        String finalTime = "";
        int finalMinutes = minutes - hours * 60;
        int finalSeconds = seconds - minutes * 60;
        if (hours != 0)
            finalTime += hours;
        if (minutes != 0)
            finalTime += finalMinutes;
        if (seconds != 0)
            finalTime += finalSeconds;

        return finalTime;
    }
}
