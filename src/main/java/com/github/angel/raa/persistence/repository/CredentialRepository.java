package com.github.angel.raa.persistence.repository;

import com.github.angel.raa.persistence.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

}
