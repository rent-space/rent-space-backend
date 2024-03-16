package com.rentspace.DTO.persist;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersistPlaceDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    private List<String> media;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private BigDecimal pricePerHour;

    @NotNull
    private Integer maximumCapacity;

    @NotNull
    private Long placeOwnerId;

}
