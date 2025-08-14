package com.weatherapp.model;


import lombok.Data;

@Data
public class WeatherResponse {
    private String city;
    private String description;
    private double temperature;
    private double feelsLike;
    private int humidity;

}
