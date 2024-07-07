package com.covidtracker.controller;

import com.covidtracker.dto.CountryApiResponse;
import com.covidtracker.dto.CovidApiResponse;
import com.covidtracker.service.CountryApiService;
import com.covidtracker.service.impl.CovidApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covid")
public class CovidApiController {

    @Autowired
    private CovidApiServiceImpl covidService;

    @Autowired
    private CountryApiService countryApiService;

    @GetMapping("/get-all-data/{countryCode}")
    public ResponseEntity<List<CovidApiResponse>> getCovidDataForCountry(@PathVariable("countryCode") String countryCode) {

        return ResponseEntity.ok(covidService.getAllCovidData(countryCode));

    }

    @GetMapping("/get-all-countries")
    public ResponseEntity<List<CountryApiResponse>> getCountries() {

        return ResponseEntity.ok(countryApiService.getAllCountries());

    }

}
