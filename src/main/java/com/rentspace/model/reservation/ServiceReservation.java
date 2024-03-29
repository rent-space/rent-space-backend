package com.rentspace.model.reservation;

import com.rentspace.model.products.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
public class ServiceReservation extends Reservation {

    private String address; // TODO vamos relacionar serviços a lugares caso o lugar já esteja cadastrado no sistema?
    private String city;
    private BigDecimal finalPrice;

}
