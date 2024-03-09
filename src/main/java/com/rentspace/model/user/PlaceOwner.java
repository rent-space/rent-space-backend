package com.rentspace.model.user;

import com.rentspace.model.place.Place;
import com.rentspace.model.place.PlaceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

