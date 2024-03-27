package com.rentspace.model.reservation;

import com.rentspace.model.products.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class ServiceReservation extends Reservation {

    @OneToOne
    private Service service;
    private String address;
    private String city;

}
