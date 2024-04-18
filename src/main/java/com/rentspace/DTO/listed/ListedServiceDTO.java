package com.rentspace.DTO.listed;

import com.rentspace.model.products.ServiceNature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListedServiceDTO {

    private Long id;
    private String title;
    private String firstMedia;
    private ServiceNature serviceNature;
    private BigDecimal pricePerHour;

}
