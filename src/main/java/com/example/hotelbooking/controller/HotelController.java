package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelWithDistanceDTO;
import com.example.hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {
    @Autowired
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel")
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok().body(hotels);
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<Hotel> getHotelByID(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelByID(id);
        return ResponseEntity.ok().body(hotel);
    }

    @PostMapping("/hotel")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        Hotel newHotel = hotelService.addHotel(hotel);
        return ResponseEntity.ok().body(newHotel);
    }

    @PostMapping("/hotels")
    public ResponseEntity<List<Hotel>> addAllHotels(@RequestBody List<Hotel> hotels) {
        List<Hotel> newHotels = hotelService.addAllHotels(hotels);
        return ResponseEntity.ok().body(newHotels);
    }

    @PutMapping("/hotel/{id}")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel, @PathVariable Long id) {
        Hotel newHotel = hotelService.updateHotel(hotel, id);
        return ResponseEntity.ok().body(newHotel);
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<Long> deleteHotel( @PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/hotel/search/{cityId}")
    public ResponseEntity<List<HotelWithDistanceDTO>> searchHotels(@PathVariable String cityId) {
        List<HotelWithDistanceDTO> hotels = hotelService.searchHotelsByCity(cityId);
        return ResponseEntity.ok().body(hotels);
    }

    @GetMapping("/hotel/closest/{cityId}/{distance}")
    public ResponseEntity<List<HotelWithDistanceDTO>> searchHotels(@PathVariable String cityId, @PathVariable Long distance) {
        List<HotelWithDistanceDTO> hotels = hotelService.searchHotelsClosestToCity(cityId, distance);
        return ResponseEntity.ok().body(hotels);
    }

}
