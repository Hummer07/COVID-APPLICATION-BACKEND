package com.covid.app.CovidApp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CovidDataService {
    private static final String COVID_DATA_URL = "https://data.covid19india.org/state_district_wise.json";

    public JsonNode getCovidDataForCity(String city) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = COVID_DATA_URL.trim();
        String jsonData = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        for (JsonNode stateNode : rootNode) {
            JsonNode districtData = stateNode.path("districtData");
            if (districtData.has(city)) {
                return districtData.path(city);
            }
        }

        return null; // City not found
    }
}
