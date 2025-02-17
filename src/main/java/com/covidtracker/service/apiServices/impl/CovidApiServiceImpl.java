package com.covidtracker.service.apiServices.impl;

import com.covidtracker.dto.covid.CovidApiResponse;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.exception.CovidRecordNotFoundException;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.apiServices.CovidApiService;
import com.covidtracker.service.dbServices.CovidDbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CovidApiServiceImpl implements CovidApiService {
    @Autowired
    private CovidDbRepository covidDbRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CovidDbService covidDbService;

    @Autowired
    private CovidMapper covidMapper;

    @Value("${rapidApiUrl}")
    private String rapidApiUrl;
    @Value("${x-rapidapi-key}")
    private String key;
    @Value("${x-rapidapi-host}")
    private String host;

    @Override
    public List<CovidApiResponse> getAllCovidData(String countryCode) {

        List<CovidApiResponse> covidApiResponse = new ArrayList<>();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-key", key);
            headers.set("x-rapidapi-host", host);

            ResponseEntity<String> response = restTemplate.exchange(rapidApiUrl + countryCode,
                    HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Response: {}", response.getBody());

            log.info("Fetching covid data for [{}] country...", countryCode.toUpperCase());

            ObjectMapper mapper = new ObjectMapper();
            covidApiResponse = mapper.readValue(response.getBody(), new TypeReference<List<CovidApiResponse>>() {});

            Optional<CovidRecord> retrievedRecord = Optional.ofNullable(
                    covidDbRepository.findAllRecordsForCountry(covidApiResponse.get(0).getCode()));

            if (retrievedRecord.isPresent()) {
                assembleRecord(covidApiResponse, retrievedRecord);
                covidDbService.saveCovidData(retrievedRecord.get());
            } else {
                CovidRecord covidRecord = covidMapper.mapFromApiResponseToDbEntity(covidApiResponse.get(0));
                covidDbService.saveCovidData(covidRecord);
            }

            return covidApiResponse;

        } catch (CovidRecordNotFoundException | JsonProcessingException e) {
            log.error("Incorrect Country Code!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }

    private CovidRecord assembleRecord(List<CovidApiResponse> covidApiResponse,
                                       Optional<CovidRecord> retrievedRecord) {

        if (covidApiResponse != null && !covidApiResponse.isEmpty()) {
            retrievedRecord.get().setCode(covidApiResponse.get(0).getCode());
            retrievedRecord.get().setCountry(covidApiResponse.get(0).getCountry());
            retrievedRecord.get().setConfirmed(covidApiResponse.get(0).getConfirmed());
            retrievedRecord.get().setRecovered(covidApiResponse.get(0).getRecovered());
            retrievedRecord.get().setCritical(covidApiResponse.get(0).getCritical());
            retrievedRecord.get().setDeaths(covidApiResponse.get(0).getDeaths());
            retrievedRecord.get().setLastChange(covidApiResponse.get(0).getLastChange());
            retrievedRecord.get().setLastUpdate(covidApiResponse.get(0).getLastUpdate());
        }

        return retrievedRecord.get();

    }
}
