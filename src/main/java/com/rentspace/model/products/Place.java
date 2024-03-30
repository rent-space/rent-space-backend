package com.rentspace.model.products;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Place extends Product {

    private Integer maximumCapacity;

    private String neighborhood;

    private String complement;

    private String zipCode;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Service> services;

}
