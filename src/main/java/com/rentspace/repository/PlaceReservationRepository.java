package com.rentspace.repository;

import com.rentspace.model.reservation.PlaceReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceReservationRepository extends JpaRepository<PlaceReservation, Long> {
}
