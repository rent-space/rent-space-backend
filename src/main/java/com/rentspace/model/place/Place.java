package com.rentspace.model.place;

import com.rentspace.model.BasicProductInfo;
import com.rentspace.model.GenericModel;
import com.rentspace.model.service.Services;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Place extends BasicProductInfo {

    private Integer maximumCapacity;
    @OneToMany
    private List<Services> services;

}
