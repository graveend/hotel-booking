package com.example.hotelbooking.controller;


import com.example.hotelbooking.model.City;
import com.example.hotelbooking.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CityController {
    @Autowired
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> Citys = cityService.getAllCities();
        return ResponseEntity.ok().body(Citys);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<City> getCityByID(@PathVariable String id) {
        City city = cityService.getCityByID(id);
        return ResponseEntity.ok().body(city);
    }

    @PostMapping("/city")
    public ResponseEntity<City> addCity(@RequestBody City City) {
        City newCity = cityService.addCity(City);
        return ResponseEntity.ok().body(newCity);
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<City> updateCity(@RequestBody City City, @PathVariable String id) {
        City newCity = cityService.updateCity(City, id);
        return ResponseEntity.ok().body(newCity);
    }

}
