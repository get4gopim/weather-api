package com.weather.api.endpoint;

import com.weather.api.model.RateInfo;
import com.weather.api.model.WeatherInfo;
import com.weather.api.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecast")
public class WeatherEndpoint {

    @Autowired
    private ForecastService forecastService;

    @GetMapping(path = "/test")
    public String test() {
        return "API Running !!";
    }

    @GetMapping(path = "/weather")
    public WeatherInfo getWeatherInfo() {
        return forecastService.getWeatherInfo();
    }

    @GetMapping(path = "/gold")
    public RateInfo getGoldRateInfo() {
        return forecastService.getGoldRateInfo();
    }

}
