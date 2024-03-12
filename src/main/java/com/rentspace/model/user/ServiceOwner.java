package com.rentspace.model.user;

import com.rentspace.model.service.Services;
import com.rentspace.model.service.ServiceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceOwner extends User {

    @Id
    private Long id;
    @OneToMany
    private List<Services> services;
    @OneToMany
    private List<ServiceReservation> reservations;

    public ServiceOwner(String name, String profilePhoto, String email, String telephone, String webSite) {
        super();
    }
}
