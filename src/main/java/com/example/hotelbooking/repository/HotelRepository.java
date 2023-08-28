package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(value = "SELECT h FROM Hotel h WHERE FUNCTION('calculateHaversineDistance', h.latitude, h.longitude, :latitude, :longitude) <= :maxDistance")
    List<Hotel> findHotelsWithinDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("maxDistance") double maxDistance);

    @Query("SELECT h, " +
            "   (6371 * " +
            "       acos(" +
            "           cos(radians(:cityLatitude)) * cos(radians(h.latitude)) * " +
            "           cos(radians(h.longitude) - radians(:cityLongitude)) + " +
            "           sin(radians(:cityLatitude)) * sin(radians(h.latitude))" +
            "       )" +
            "   ) AS distance " +
            "FROM Hotel h " +
            "ORDER BY distance")
    List<Object[]> findHotelsByDistance(@Param("cityLatitude") Float cityLatitude, @Param("cityLongitude") Float cityLongitude);

}
