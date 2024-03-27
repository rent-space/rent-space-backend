package com.rentspace.repository;

import com.rentspace.model.reservation.ServiceReservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceReservationService extends ReservationRepository<ServiceReservation> {
}
