package com.rentspace.DTO.response.product;

import com.rentspace.DTO.response.ResponseUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsePlaceDTO extends ResponseProductDTO {

    private Integer maximumCapacity;

    private String neighborhood;

    private String complement;

    private String zipCode;

    private List<String> media;

    public ResponsePlaceDTO(
        Long id,
        String title,
        String description,
        String address,
        String city,
        String neighborhood,
        String complement,
        String zipCode,
        BigDecimal pricePerHour,
        Integer maximumCapacity,
        ResponseUserDTO owner,
        List<String> media
    ) {
        super(id, title, description, address, city, pricePerHour, owner, media);
        this.maximumCapacity = maximumCapacity;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.zipCode = zipCode;
    }

}
