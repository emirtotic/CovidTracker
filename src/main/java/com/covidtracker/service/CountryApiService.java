package com.covidtracker.service;

import com.covidtracker.dto.CountryApiResponse;
import com.covidtracker.dto.CovidApiResponse;

import java.util.List;

public interface CountryApiService {

    List<CountryApiResponse> getAllCountries();
}
