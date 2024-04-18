package com.rentspace.repository;

import com.rentspace.model.products.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {


    @Query("SELECT p " +
            "FROM Place p " +
            "JOIN p.services s " +
            "WHERE s.id = :serviceId"
    )
    List<Place> getAllByExclusiveService(Long serviceId);

}
