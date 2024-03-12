package com.rentspace.model.place;

import com.rentspace.model.service.Services;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Place {
    @Id
    private Long id;
    private Integer maximumCapacity;
    @OneToMany
    private List<Services> services;

}
