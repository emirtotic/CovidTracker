package com.covidtracker.service;

import com.covidtracker.dto.embassy.EmbassyApiResponse;

public interface EmbassyApiService {

    EmbassyApiResponse findYourEmbassies(String source, String destination);
}
