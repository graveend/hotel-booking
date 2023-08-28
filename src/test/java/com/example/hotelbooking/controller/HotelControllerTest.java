package com.example.hotelbooking.controller;


import com.example.hotelbooking.HotelBookingApplication;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.service.HotelService;
import com.example.hotelbooking.service.implementation.HotelImplService;
import com.example.hotelbooking.utils.GenerateHotelData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @InjectMocks
    private HotelService hotelService = new HotelImplService();

    @Before
    public void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void valiHotelID_shouldReturnWith200() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        Hotel hotel = GenerateHotelData.getAHotel();
        when(restTemplate.exchange(createURLWithPort("/hotel/1"), HttpMethod.GET, entity, String.class))
                .thenReturn(new ResponseEntity(hotel, HttpStatus.OK));

        Hotel expected = hotelService.getHotelByID(1L);

        assertEquals(expected, hotel);
    }

    private URI createURLWithPort(String uri) {
        return URI.create("http://localhost:" + port + uri);
    }

}
