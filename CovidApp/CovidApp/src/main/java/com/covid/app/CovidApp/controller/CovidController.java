package com.covid.app.CovidApp.controller;

import com.covid.app.CovidApp.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class CovidController {

    @Autowired
    private CovidDataService covidDataService;

    @GetMapping("/covid-data/citywise/{city}")
    public String getCovidCases(@PathVariable String city) {
        try {
            JsonNode cityData = covidDataService.getCovidDataForCity(city);
            if (cityData != null && cityData.has("active")) {
                int activeCases = cityData.get("active").asInt();
                return "Covid Active Cases in " + city + " = " + activeCases;
            } else {
                return "City not found or data unavailable";
            }
        } catch (Exception e) {
            return "Error fetching data for city: " + city;
        }
    }
}
