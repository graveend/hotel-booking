package com.example.hotelbooking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city")
@Getter
@Setter
public class City {
    @Id
    @Column(name = "code")
    private String code;
    private String name;
    private Float latitude;
    private Float longitude;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Hotel> hotels = new ArrayList<>();
}
