package com.rentspace.DTO.persist;

import com.rentspace.model.products.ServiceNature;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersistServiceDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    private List<String> media;

    private String address;

    private String city;

    @NotNull
    private BigDecimal pricePerHour;

    @NotNull
    private Long serviceOwnerId;

    @NotNull
    private ServiceNature serviceNature;

    @NotNull
    private Integer peopleInvolved;

    private List<Long> placesIdsRelated;

}
