package com.rentspace.model.user;

import com.rentspace.model.place.Place;
import com.rentspace.model.place.PlaceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class PlaceOwner extends AppUser {

    @OneToMany
    private List<Place> places;
    @OneToMany
    private List<PlaceReservation> reservations;

}

