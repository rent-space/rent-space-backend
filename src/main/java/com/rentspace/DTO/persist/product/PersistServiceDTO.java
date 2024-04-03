package com.rentspace.DTO.persist.product;

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
public class PersistServiceDTO extends PersistProductDTO{

    @NotNull
    private ServiceNature serviceNature;

    @NotNull
    private Integer peopleInvolved;

    @NotNull 
    private List<Long> placesIdsRelated;

    public PersistServiceDTO(
        String title,
        String description,
        List<String> media,
        String addess,
        String city,
        String neighborhood,
        BigDecimal pricePerHour,
        Long serviceOwnerId,
        ServiceNature serviceNature,
        Integer peopleInvolved,
        List<Long> placesIdsRelated
    ) {
        super(title, description, media, addess, city, neighborhood, pricePerHour, serviceOwnerId);
        this.serviceNature = serviceNature;
        this.peopleInvolved = peopleInvolved;
        this.placesIdsRelated = placesIdsRelated;
    }

}
