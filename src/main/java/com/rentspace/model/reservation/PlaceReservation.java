package com.rentspace.model.reservation;

import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class PlaceReservation extends BasicReservationInfo {

    @OneToOne
    private Place place;
    private Integer numOfParticipants;
    @OneToMany
    private List<Service> hiredRelatedServices;

}
