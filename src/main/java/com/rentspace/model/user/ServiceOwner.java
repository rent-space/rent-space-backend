package com.rentspace.model.user;

import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.ServiceReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class ServiceOwner extends AppUser {

    @OneToMany
    private List<Service> services;
    @OneToMany
    private List<ServiceReservation> reservations;
}
