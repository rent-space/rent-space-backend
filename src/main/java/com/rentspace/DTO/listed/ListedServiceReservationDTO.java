package com.rentspace.DTO.listed;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListedServiceReservationDTO {

    private String address;
    private String city;
    private BigDecimal finalPrice;
}
