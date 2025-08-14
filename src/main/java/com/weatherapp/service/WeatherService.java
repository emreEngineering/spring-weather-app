package com.weatherapp.service;

import com.weatherapp.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;
import java.util.List;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponse getWeather(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "tr")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        WeatherResponse weather = new WeatherResponse();
        weather.setCity((String) response.get("name"));

        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        weather.setDescription((String) weatherList.get(0).get("description"));

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        weather.setTemperature(((Number) main.get("temp")).doubleValue());
        weather.setFeelsLike(((Number) main.get("feels_like")).doubleValue());
        weather.setHumidity(((Number) main.get("humidity")).intValue());

        return weather;
    }
}
