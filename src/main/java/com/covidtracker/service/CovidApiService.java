package com.covidtracker.service;

import com.covidtracker.dto.CovidApiResponse;

import java.util.List;

public interface CovidApiService {

    List<CovidApiResponse> getAllCovidData(String countryCode);
}
