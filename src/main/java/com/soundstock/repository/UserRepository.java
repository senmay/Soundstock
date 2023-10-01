package com.soundstock.repository;

import com.soundstock.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByEmail(String userEmail);
}
