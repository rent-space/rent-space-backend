package com.rentspace.model.products;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Service extends Product {

    @Enumerated(EnumType.STRING)
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
}
