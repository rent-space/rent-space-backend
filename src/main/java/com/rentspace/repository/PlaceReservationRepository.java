package com.rentspace.repository;

import com.rentspace.model.products.Place;
import com.rentspace.model.reservation.PlaceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PlaceReservationRepository extends JpaRepository<PlaceReservation, Long> {


    @Query("SELECT pr FROM PlaceReservation pr " +
            "WHERE pr.status = 'ACCEPTED' " +
            "AND pr.place = :place " +
            "AND ((:startsAt >= pr.startsAt AND :startsAt <= pr.endsAt) " +
            "OR (:endsAt >= pr.startsAt AND :endsAt <= pr.endsAt) " +
            "OR (:startsAt <= pr.endsAt AND :endsAt >= pr.endsAt))"
    )
    Optional<PlaceReservation> getReservationInProgress(LocalDateTime startsAt, LocalDateTime endsAt, Place place);

}
