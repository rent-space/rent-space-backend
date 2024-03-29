package com.rentspace.DTO.response.product;

import com.rentspace.DTO.response.ResponseUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseProductDTO {

    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private BigDecimal pricePerHour;
    private ResponseUserDTO owner;

}
