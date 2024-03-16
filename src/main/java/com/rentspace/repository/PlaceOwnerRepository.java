package com.rentspace.repository;

import com.rentspace.model.user.PlaceOwner;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOwnerRepository extends UserRepository<PlaceOwner> {
}
