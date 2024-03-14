package com.rentspace.repository;

import com.rentspace.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository<T extends AppUser> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);

}
