package com.rentspace.model.service;

import com.rentspace.model.BasicProductInfo;
import com.rentspace.model.GenericModel;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Service extends BasicProductInfo {

    @Enumerated(EnumType.STRING)
    private ServiceNature serviceNature;
    private Integer peopleInvolved;
}
