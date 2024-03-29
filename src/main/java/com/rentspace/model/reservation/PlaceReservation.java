package com.rentspace.model.reservation;

import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class PlaceReservation extends Reservation {

    private Integer numOfParticipants;
    @OneToMany
    private List<Service> hiredRelatedServices;

    private BigDecimal placeFinalPrice;

    private BigDecimal servicesFinalPrice;

}
