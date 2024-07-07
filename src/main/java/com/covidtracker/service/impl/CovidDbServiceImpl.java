package com.covidtracker.service.impl;

import com.covidtracker.dto.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.exception.CovidRecordNotFoundException;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.CovidDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CovidDbServiceImpl implements CovidDbService {

    private final CovidDbRepository covidDbRepository;

    private final CovidMapper covidMapper;

    @Override
    public List<CovidRecordDto> findAllRecords() {
        return covidMapper.mapToDto(covidDbRepository.findAll());
    }

    @Override
    public CovidRecordDto findAllRecordsForCountry(String countryCode) {
        return covidMapper.mapToDto(covidDbRepository.findAllRecordsForCountry(countryCode));
    }

    @Override
    public void saveCovidData(CovidRecord covidRecord) {
        covidDbRepository.save(covidRecord);
    }

    @Override
    public String deleteCovidDataByCountryCode(String countryCode) {

        CovidRecord record = Optional.ofNullable(covidDbRepository.findAllRecordsForCountry(countryCode))
                .orElseThrow(() -> new CovidRecordNotFoundException(countryCode));

        if (record != null) {
            covidDbRepository.delete(record);

            return "Data successfully deleted.";
        }
        return "Could not retrieve data for desired country code.";
    }

    @Override
    public List<CovidRecordDto> findAllRecordsFromBalkan() {
        return covidMapper.mapToDto(covidDbRepository.findAllRecordsFromBalkan());
    }

    @Override
    public Page<CovidRecordDto> findAllRecords(Pageable pageable) {
        return covidDbRepository.findAll(pageable).map(covidMapper::mapToDto);
    }
}
