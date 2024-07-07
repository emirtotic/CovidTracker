package com.covidtracker.mapper;

import com.covidtracker.dto.CountryApiResponse;
import com.covidtracker.dto.CountryDto;
import com.covidtracker.dto.CovidApiResponse;
import com.covidtracker.dto.CovidRecordDto;
import com.covidtracker.entity.Country;
import com.covidtracker.entity.CovidRecord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country mapFromApiResponseToDbEntity(CountryApiResponse countryApiResponse);
    CountryDto mapToDto(Country country);
    List<CountryDto> mapToDto(List<Country> countries);
}
