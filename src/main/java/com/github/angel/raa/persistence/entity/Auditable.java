package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.angel.raa.configuration.RequestContext;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updateAt"} , allowGetters = true)
abstract class Auditable implements Serializable {
    private static final Long serialVersionUID = 17_33_183_613_712_021_386L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    private final String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @CreatedBy
    @Column(name = "create_by", nullable = false, updatable = false)
    private Long createdBy;
    @LastModifiedBy
    @Column(name = "update_by", nullable = false)
    private Long updateBy;
    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;
    @PrePersist
    public void beforePersist(){
       Long  userId = getDefaultUserId();
        setUpdateAt(now());
        setCreateAt(now());
        setCreatedBy(userId);
        setUpdateBy(userId);
    }

    @PreUpdate
    public void beforeUpdate(){
        Long userId = RequestContext.getUserId();
        setUpdateBy(userId);
        setUpdateAt(now());
    }

    private @NotNull Long getDefaultUserId (){
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }
}
