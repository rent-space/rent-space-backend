package com.rentspace.model.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Services {
    @Id
    private Long id;
    @OneToOne
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
    private Boolean isRelatedToSpace;
}
