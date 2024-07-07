package com.covidtracker.service.impl;

import com.covidtracker.dto.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import com.covidtracker.mapper.CovidMapper;
import com.covidtracker.repository.CovidDbRepository;
import com.covidtracker.service.CovidDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

class CovidDbServiceImplTest {

    private CovidDbRepository covidDbRepository;
    private CovidMapper covidMapper;
    private CovidDbService covidDbService;

    @BeforeEach
    void setUp() {
        covidDbRepository = mock(CovidDbRepository.class);
        covidMapper = mock(CovidMapper.class);
        covidDbService = new CovidDbServiceImpl(covidDbRepository, covidMapper);

    }

    @Test
    @DisplayName("Find All Records from DB Test")
    void getAllCovidData() {
        CovidRecord covidRecord = createCovidRecord();
        List<CovidRecord> entityList = Collections.singletonList(covidRecord);
        when(covidDbRepository.findAll()).thenReturn(entityList);
        when(covidMapper.mapToDto(entityList)).thenReturn(Collections.singletonList(new CovidRecordDto()));

        covidDbService.findAllRecords();
        verify(covidDbRepository, times(1)).findAll();
        verify(covidMapper, times(1)).mapToDto(entityList);

    }

    private CovidRecord createCovidRecord() {
        CovidRecord covidRecord = new CovidRecord();
        covidRecord.setId(1l);
        covidRecord.setCode("IT");
        covidRecord.setCountry("Italy");
        covidRecord.setConfirmed(100L);
        covidRecord.setCritical(5L);
        covidRecord.setRecovered(100L);
        covidRecord.setDeaths(0L);
        covidRecord.setLastChange(new Date());
        covidRecord.setLastUpdate(new Date());

        return covidRecord;
    }

}