package com.rentspace.model.products;

import com.rentspace.model.GenericModel;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Product extends GenericModel {

    private String title;
    private String description;
    private List<String> media;
    private String address;
    private String city;
    private BigDecimal pricePerHour;

}
