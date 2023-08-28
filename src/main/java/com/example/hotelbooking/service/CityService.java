package com.example.hotelbooking.service;

import com.example.hotelbooking.model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City getCityByID(String cityId);
    City addCity(City city);

    City updateCity(City city, String cityId);

    boolean deleteCity(String cityId);
}
