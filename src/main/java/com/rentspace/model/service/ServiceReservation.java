package com.rentspace.model.service;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class ServiceReservation {
    @Id
    private Long id;
    @OneToOne
    private Services services;
    private String address;
    private String city;

}
