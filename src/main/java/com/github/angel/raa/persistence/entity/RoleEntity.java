package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RoleEntity extends Auditable{
    private String name;
    @Enumerated(EnumType.STRING)
    private Authorities authorities;
}
