package com.covidtracker.mapper;

import com.covidtracker.dto.CovidApiResponse;
import com.covidtracker.dto.CovidRecordDto;
import com.covidtracker.entity.CovidRecord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CovidMapper {

    CovidRecord mapFromApiResponseToDbEntity(CovidApiResponse covidApiResponse);
    CovidRecordDto mapToDto(CovidRecord covidRecord);
    List<CovidRecordDto> mapToDto(List<CovidRecord> covidRecords);
}
