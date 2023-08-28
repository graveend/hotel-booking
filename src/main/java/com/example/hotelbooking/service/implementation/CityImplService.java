package com.example.hotelbooking.service.implementation;

import com.example.hotelbooking.exception.CustomEntityNotFoundException;
import com.example.hotelbooking.model.City;
import com.example.hotelbooking.repository.CityRepository;
import com.example.hotelbooking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.hotelbooking.utils.Constants.*;

@Service
public class CityImplService implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityByID(String cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, CITY, cityId)));
    }

    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City updateCity(City city, String cityId) {
        Optional<City> cityDetails =  cityRepository.findById(cityId);
        if(cityDetails.isPresent()) {
            City updatedCity = new City();
            updatedCity.setCode(cityId);
            updatedCity.setName(city.getName());
            updatedCity.setLatitude(city.getLatitude());
            updatedCity.setLatitude(city.getLongitude());
            return cityRepository.save(updatedCity);
        } else {
            throw new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, CITY, cityId));
        }
    }

    @Override
    public boolean deleteCity(String cityId) {
        return false;
    }
}
