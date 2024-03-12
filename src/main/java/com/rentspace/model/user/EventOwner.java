package com.rentspace.model.user;

import com.rentspace.model.place.PlaceReservation;
import com.rentspace.model.service.ServiceReservation;
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
