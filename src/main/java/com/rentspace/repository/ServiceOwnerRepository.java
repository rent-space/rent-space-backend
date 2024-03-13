package com.rentspace.repository;

import com.rentspace.model.user.ServiceOwner;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOwnerRepository extends UserRepository<ServiceOwner> {
}
