package com.rentspace.repository;

import com.rentspace.model.products.Place;
import com.rentspace.model.products.Product;
import com.rentspace.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository<T extends Reservation> extends JpaRepository<T, Long> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.status = 'ACCEPTED' " +
            "AND r.product = :product " +
            "AND ((:startsAt >= r.startsAt AND :startsAt <= r.endsAt) " +
            "OR (:endsAt >= r.startsAt AND :endsAt <= r.endsAt) " +
            "OR (:startsAt <= r.endsAt AND :endsAt >= r.endsAt))"
    )
    Optional<T> getReservationInProgress(LocalDateTime startsAt, LocalDateTime endsAt, Product product);
}
