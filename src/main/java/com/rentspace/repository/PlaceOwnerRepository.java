package com.rentspace.repository;

import com.rentspace.model.user.PlaceOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceOwnerRepository extends UserRepository<PlaceOwner> {

    @Query("SELECT po FROM PlaceOwner po " +
            "JOIN po.places p " +
            "WHERE p.id = :placeId")
    Optional<PlaceOwner> findByPlaceId(Long placeId);
}
