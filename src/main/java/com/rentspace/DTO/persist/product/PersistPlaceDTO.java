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
    public PersistPlaceDTO(
            String title,
            String description,
            List<String> media,
            String address,
            String city,
            BigDecimal pricePerHour,
            Integer maximumCapacity,
            Long ownerId
    ) {
        super(title, description, media, address, city, pricePerHour, ownerId);
        this.maximumCapacity = maximumCapacity;

    }

}
