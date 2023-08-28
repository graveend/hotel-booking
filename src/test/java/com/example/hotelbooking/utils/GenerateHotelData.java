package com.example.hotelbooking.utils;

import com.example.hotelbooking.model.Hotel;

public class GenerateHotelData {
    public static Hotel getAHotel() {
        return new Hotel(1L, "ibis", "London", 1F, 2F);
    }
}
