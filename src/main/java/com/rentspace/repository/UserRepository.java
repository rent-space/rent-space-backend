package com.rentspace.repository;

import com.rentspace.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<T extends AppUser> extends JpaRepository<T, Long> {}
