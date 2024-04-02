package com.rentspace.model.products;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private List<Service> services;

}
