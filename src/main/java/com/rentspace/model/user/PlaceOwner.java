package com.rentspace.model.user;

import com.rentspace.model.place.Place;
import com.rentspace.model.place.PlaceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class PlaceOwner extends User {

    @Id
    private Long id;
    @OneToMany
    private List<Place> places;
    @OneToMany
    private List<PlaceReservation> reservations;

    public PlaceOwner(String name, String profilePhoto, String email, String telephone, String webSite) {
        super();
    }
}

