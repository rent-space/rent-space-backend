package com.rentspace.repository;

import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceOwnerRepository extends UserRepository<ServiceOwner> {

    @Query(value = "SELECT so FROM ServiceOwner so " +
            "JOIN so.services s " +
            "WHERE s.id = :serviceId")
    Optional<ServiceOwner> findByServiceId(@Param("serviceId") Long serviceId);

}
