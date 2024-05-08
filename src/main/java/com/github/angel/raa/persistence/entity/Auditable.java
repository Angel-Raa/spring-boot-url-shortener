package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.angel.raa.configuration.RequestContext;
import com.github.angel.raa.exception.MissingIdException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updateAt"} , allowGetters = true)
public abstract class Auditable implements Serializable {
    @Serial
    private static final Long serialVersionUID = 17_33_183_613_712_021_386L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    private final String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @Column(name = "create_by", nullable = false, updatable = false)
    @NotNull
    private Long createdBy;
    @Column(name = "update_by", nullable = false)
    @NotNull
    private Long updateBy;
    @Column(name = "create_at", nullable = false, updatable = false)
    @NotNull
    @CreatedDate
    private LocalDateTime createAt;
    @NotNull
    @CreatedDate
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    private void checkIdExists() {
        Long id = RequestContext.getId();
        if (id == null) {
            throw new MissingIdException("Cannot persist entity without user ID in RequestContext for this thread.");
        }
    }
    @PrePersist
    public void beforePersist(){
       checkIdExists();
        setUpdateAt(now());
        setCreateAt(now());
        setCreatedBy(id);
        setUpdateBy(id);
    }

    @PreUpdate
    public void beforeUpdate(){
        checkIdExists();
        setUpdateBy(id);
        setUpdateAt(now());
    }
}
