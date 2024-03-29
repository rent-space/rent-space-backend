package com.rentspace.repository;

import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceOwnerRepository extends UserRepository<ServiceOwner> {

    @Query("SELECT so FROM ServiceOwner so " +
            "JOIN so.services s " +
            "WHERE s.id = :serviceId")
    Optional<ServiceOwner> findByServiceId(Long serviceId);

}
