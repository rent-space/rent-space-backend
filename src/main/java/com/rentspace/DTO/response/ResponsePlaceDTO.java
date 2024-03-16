package com.rentspace.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsePlaceDTO {

    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private BigDecimal pricePerHour;
    private Integer maximumCapacity;
    private Long placeOwnerId;

}
