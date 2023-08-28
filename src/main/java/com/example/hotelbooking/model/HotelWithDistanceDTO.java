package com.example.hotelbooking.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelWithDistanceDTO {
    private Hotel hotel;
    private double distance;
}
