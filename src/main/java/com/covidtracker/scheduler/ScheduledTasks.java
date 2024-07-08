package com.covidtracker.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    @Autowired
    private RestTemplate restTemplate;

    private final String REFRESH_COVID_DATA_FOR_SERBIA = "http://localhost:8080/covid/get-all-data/rs";
    @Scheduled(cron = "0 0 10 * * ?") // Timer set at 10h every day. (@Scheduled(cron = "0 * * * * *") is every second)
    public void refreshDataForSerbia() {
        restTemplate.getForObject(REFRESH_COVID_DATA_FOR_SERBIA, String.class);
    }

}
