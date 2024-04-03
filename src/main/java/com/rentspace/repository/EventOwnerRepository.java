package com.rentspace.repository;

import com.rentspace.model.user.EventOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventOwnerRepository extends UserRepository<EventOwner> {

    @Query("SELECT eo FROM EventOwner eo " +
            "JOIN eo.places p " +
            "WHERE p.id = :id")
    Optional<EventOwner> findByPlaceReservation(Long id);

    @Query("SELECT eo FROM EventOwner eo " +
            "JOIN eo.services s " +
            "WHERE s.id = :id")
    Optional<EventOwner> findByServiceReservation(Long id);
}
