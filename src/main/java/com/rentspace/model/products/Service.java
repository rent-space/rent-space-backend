package com.rentspace.model.products;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Service extends BasicProductInfo {

    @Enumerated(EnumType.STRING)
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
}
