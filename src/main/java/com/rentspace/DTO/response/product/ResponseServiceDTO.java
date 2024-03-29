package com.rentspace.DTO.response.product;

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.DTO.response.product.ResponseProductDTO;
import com.rentspace.model.products.ServiceNature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseServiceDTO extends ResponseProductDTO {

    private ServiceNature serviceNature;
    private Integer peopleInvolved;
    private List<ListedPlaceDTO> placesRelated;

    public ResponseServiceDTO(
            Long id,
            String title,
            String description,
            String address,
            String city,
            BigDecimal pricePerHour,
            ResponseUserDTO owner,
            ServiceNature serviceNature,
            Integer peopleInvolved,
            List<ListedPlaceDTO> placesRelated
    ) {
        super(id, title, description, address, city, pricePerHour, owner);
        this.serviceNature = serviceNature;
        this.peopleInvolved = peopleInvolved;
        this.placesRelated = placesRelated;
    }
}
