package com.rentspace.model.user;

import com.rentspace.model.place.PlaceReservation;
import com.rentspace.model.service.ServiceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class EventOwner extends User {

    @Id
    private Long id;

    @OneToMany
    private List<PlaceReservation> places;
    @OneToMany
    private List<ServiceReservation> services;

    public EventOwner(String name, String profilePhoto, String email, String telephone, String webSite) {
        super();
    }
}
