package com.rentspace.model.user;

import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.ServiceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class EventOwner extends AppUser {

    @OneToMany
    private List<PlaceReservation> places;
    @OneToMany
    private List<ServiceReservation> services;
}
