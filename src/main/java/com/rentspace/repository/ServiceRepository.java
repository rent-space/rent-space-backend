package com.rentspace.repository;

import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query(value =
            "SELECT p FROM Place p " +
                    "JOIN p.services s WHERE s.id = :serviceId"
    )
    List<Place> findRelatedPlaces(Long serviceId);
}
