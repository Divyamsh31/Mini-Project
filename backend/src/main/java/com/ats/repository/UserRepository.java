package com.ats.repository;

import com.ats.model.User;
import com.ats.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email, UserRole role);
    Boolean existsByEmailAndRole(String email, UserRole role);
    Boolean existsByEmail(String email);
    long countByRole(UserRole role);
    long countByStatus(String status);
    java.util.List<User> findByRole(UserRole role);


}
