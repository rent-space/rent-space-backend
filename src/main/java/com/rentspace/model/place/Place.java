package com.rentspace.model.place;

import com.rentspace.model.BasicProductInfo;
import com.rentspace.model.service.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Place extends BasicProductInfo {

    private Integer maximumCapacity;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Service> services;

}
