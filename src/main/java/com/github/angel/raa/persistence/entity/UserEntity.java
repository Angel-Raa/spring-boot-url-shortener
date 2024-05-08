package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity extends Auditable{
    @Column(name = "user_id", updatable = false, unique = true, nullable = false)
    private String userId;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class)
    @JoinTable(name = "user_role", joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id")
    ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private RoleEntity role;
}
