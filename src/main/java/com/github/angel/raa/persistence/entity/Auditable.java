package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.angel.raa.configuration.RequestContext;
import com.github.angel.raa.exception.MissingIdException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import static java.time.Instant.now;
@NoArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updateAt"} , allowGetters = true)
abstract class Auditable implements Serializable {
    @Serial
    private static final Long serialVersionUID = 17_33_183_613_712_021_386L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    private final String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @Column(name = "create_by", nullable = false, updatable = false)
    private Long createdBy;
    @Column(name = "update_by", nullable = false)
    private Long updateBy;
    @Column(name = "create_at", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createAt;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;
    @PrePersist
    public void beforePersist(){
        Long userId = RequestContext.getId();
        if(userId == null){
            throw  new MissingIdException("Cannot persist entity without user ID in RequestContext for this thread");
        }
        setUpdateAt(now());
        setCreateAt(now());
        setCreatedBy(id);
        setUpdateBy(id);
    }

    @PreUpdate
    public void beforeUpdate(){
        Long userId = RequestContext.getId();
        if(userId == null){
            throw  new MissingIdException("Cannot update entity without user ID in RequestContext for this thread");
        }

        setUpdateBy(id);
        setUpdateAt(now());
    }
}
