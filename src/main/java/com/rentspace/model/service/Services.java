package com.rentspace.model.service;

import com.rentspace.model.GenericModel;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Services extends GenericModel {

    @Enumerated(EnumType.STRING)
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
    private Boolean isRelatedToSpace;
}
