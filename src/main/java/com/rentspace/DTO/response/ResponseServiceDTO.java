package com.rentspace.DTO.response;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.model.service.ServiceNature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseServiceDTO {

    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private BigDecimal pricePerHour;
    private ResponseUserDTO serviceOwner;
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
    private List<ListedPlaceDTO> placesRelated;
}
