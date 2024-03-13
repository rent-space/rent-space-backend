package com.rentspace.model.place;

import com.rentspace.model.GenericModel;
import com.rentspace.model.service.Services;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class PlaceReservation extends GenericModel {

    @OneToOne
    private Place place;
    private Integer numberOfParticipants;
    @OneToMany
    private List<Services> hiredRelatedServices;

}
