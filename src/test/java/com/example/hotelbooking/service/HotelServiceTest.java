package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.CustomEntityNotFoundException;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.service.implementation.HotelImplService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelImplService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetHotelByIDExistingHotel() {
        Long hotelId = 1L;
        Hotel mockHotel = new Hotel();
        mockHotel.setHotelId(hotelId);

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotel));
        Hotel hotel = hotelService.getHotelByID(hotelId);
        assertNotNull(hotel);
        assertEquals(hotelId, hotel.getHotelId());
    }

    @Test
    public void testGetHotelByIDNonExistingHotel() {
        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());
        assertThrows(CustomEntityNotFoundException.class, () -> {
            hotelService.getHotelByID(hotelId);
        });
    }

}
