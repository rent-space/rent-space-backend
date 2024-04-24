package com.rentspace.DTO.persist.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersistPlaceDTO extends PersistProductDTO {

    @NotNull
    private Integer maximumCapacity;

    private String complement;

    private List<String> media;

    @NotNull
    private String zipCode;
    public PersistPlaceDTO(
            String title,
            String description,
            List<String> media,
            String address,
            String city,
            String neighborhood,
            String complement,
            String zipCode,
            BigDecimal pricePerHour,
            Integer maximumCapacity,
            Long ownerId
    ) {
        super(title, description, media, address, neighborhood, city, pricePerHour, ownerId);
        this.maximumCapacity = maximumCapacity;
        this.complement = complement;
        this.zipCode = zipCode;

    }

}
