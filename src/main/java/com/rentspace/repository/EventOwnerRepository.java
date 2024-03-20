package com.rentspace.repository;

import com.rentspace.model.user.EventOwner;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOwnerRepository extends UserRepository<EventOwner> {
}
