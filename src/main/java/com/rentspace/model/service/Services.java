package com.rentspace.model.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class Services {
    @Id
    private Long id;
    @OneToOne
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
    private Boolean isRelatedToSpace;
}
