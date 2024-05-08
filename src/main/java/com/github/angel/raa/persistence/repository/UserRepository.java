package com.github.angel.raa.persistence.repository;

import com.github.angel.raa.persistence.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(@NotNull String email);
    Optional<UserEntity> findByUserId(@NotNull String userId);

}
