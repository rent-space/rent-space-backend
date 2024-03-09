package com.rentspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

}
