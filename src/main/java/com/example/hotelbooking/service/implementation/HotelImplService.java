package com.example.hotelbooking.service.implementation;

import com.example.hotelbooking.exception.CustomEntityNotFoundException;
import com.example.hotelbooking.model.City;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelWithDistanceDTO;
import com.example.hotelbooking.repository.CityRepository;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.hotelbooking.utils.Constants.*;

@Service
public class HotelImplService implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelByID(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, HOTEL, hotelId)));
    }

    @Override
    public Hotel addHotel(Hotel hotel) {
        if(hotel.getCity() == null || hotel.getCity().getCode() == null) {
            throw new CustomEntityNotFoundException("Required element not found");
        }
        String cityCode = hotel.getCity().getCode();
        // Check if the city with the provided code exists in the database
        City city = cityRepository.findById(cityCode)
                .orElseThrow(() -> new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, CITY, cityCode)));
        // Associate the retrieved or newly created city with the hotel
        hotel.setCity(city);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> addAllHotels(List<Hotel> hotels) {
        return hotelRepository.saveAll(hotels);
    }

    @Override
    public Hotel updateHotel(Hotel hotel, Long hotelId) {
        Optional<Hotel> existingHotel =  hotelRepository.findById(hotelId);
        if(existingHotel.isPresent()) {
            Hotel updatedHotel = new Hotel();
            updatedHotel.setHotelId(hotelId);
            updatedHotel.setCity(hotel.getCity());
            updatedHotel.setLatitude(hotel.getLatitude());
            updatedHotel.setLongitude(hotel.getLongitude());
            updatedHotel.setName(hotel.getName());
            return hotelRepository.save(updatedHotel);
        } else {
            throw new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, HOTEL, hotelId));
        }
    }

    @Override
    public void deleteHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public List<Hotel> searchHotels(String cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CustomEntityNotFoundException(String.format(ELEMENT_NOT_FOUND, CITY, cityId)));

        double cityLatitude = city.getLatitude();
        double cityLongitude = city.getLongitude();

        Long MAX_DISTANCE_THRESHOLD = 1L;
        List<Hotel> closestHotels = hotelRepository.findHotelsWithinDistance(
                cityLatitude, cityLongitude, MAX_DISTANCE_THRESHOLD);

        return closestHotels;
    }

    @Override
    public List<HotelWithDistanceDTO> searchHotelsByCity(String cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CustomEntityNotFoundException("City not found"));

        List<Hotel> allHotels = hotelRepository.findAll();
        List<HotelWithDistanceDTO> hotelsWithDistance = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            double distance = calculateDistance(city.getLatitude(), city.getLongitude(),
                    hotel.getLatitude(), hotel.getLongitude());
            hotelsWithDistance.add(new HotelWithDistanceDTO(hotel, distance));
        }

        hotelsWithDistance.sort(Comparator.comparingDouble(HotelWithDistanceDTO::getDistance));

        return hotelsWithDistance;
    }

    @Override
    public List<HotelWithDistanceDTO> searchHotelsClosestToCity(String cityId, Long maxDistance) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CustomEntityNotFoundException("City not found"));

        List<Hotel> allHotels = hotelRepository.findAll();
        List<HotelWithDistanceDTO> hotelsWithinMaxDistance = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            double distance = calculateDistance(city.getLatitude(), city.getLongitude(),
                    hotel.getLatitude(), hotel.getLongitude());
            if (distance <= maxDistance) {
                hotelsWithinMaxDistance.add(new HotelWithDistanceDTO(hotel, distance));
            }
        }

        hotelsWithinMaxDistance.sort(Comparator.comparingDouble(HotelWithDistanceDTO::getDistance));

        return hotelsWithinMaxDistance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
