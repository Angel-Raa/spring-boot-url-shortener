package com.github.angel.raa.persistence.repository;

import com.github.angel.raa.persistence.entity.ShortUrlEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    Optional<ShortUrlEntity> findByKey( @NotNull String key);
    Optional<ShortUrlEntity> findByFullUrl(@NotNull String fullUrl);
}
