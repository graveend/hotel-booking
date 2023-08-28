package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelWithDistanceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface HotelService {

    List<Hotel> getAllHotels();
    Hotel getHotelByID(Long hotelId);
    Hotel addHotel(Hotel hotel);

    List<Hotel> addAllHotels(List<Hotel> hotels);

    Hotel updateHotel(Hotel hotel, Long hotelId);

    void deleteHotel(Long hotelId);

    public List<Hotel> searchHotels(String cityId);

    public List<HotelWithDistanceDTO> searchHotelsByCity(String cityId);

    public List<HotelWithDistanceDTO> searchHotelsClosestToCity(String cityId, Long maxDistance);
}
