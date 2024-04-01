package com.rentspace.model.user;

import com.rentspace.model.products.Place;
import jakarta.validation.constraints.NotNull;
import com.rentspace.model.reservation.PlaceReservation;
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
    @NotNull
    private List<Place> places;
    
    @OneToMany
    private List<PlaceReservation> reservations;

}

