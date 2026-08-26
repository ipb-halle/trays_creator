package com.location.creator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class LocationClient {
    private final RestClient restClient;
    private final ObjectMapper mapper;

    public LocationClient(RestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    public String createLocation(CreateLocationRequest requestBody) {
        return null;
    }

    


}
