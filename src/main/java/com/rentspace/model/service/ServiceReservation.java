package com.rentspace.model.service;


import com.rentspace.model.GenericModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class ServiceReservation extends GenericModel {

    @OneToOne
    private Services services;
    private String address;
    private String city;

}
