package com.rentspace.model.products;

import com.rentspace.model.GenericModel;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@MappedSuperclass
@Data
public abstract class BasicProductInfo extends GenericModel {

    private String title;
    private String description;
    private List<String> media;
    private String address;
    private String city;
    private BigDecimal pricePerHour;

}
