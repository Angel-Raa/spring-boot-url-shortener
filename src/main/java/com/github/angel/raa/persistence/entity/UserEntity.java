package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity extends Auditable{
    @NotNull
    @Column(name = "user_id", updatable = false, unique = true, nullable = false)
    private String userId;
    @NotNull
    @Column(name = "email", unique = true)
    private String email;
    @NotNull
    @Column(name = "username")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class)
    @JoinTable(name = "user_role", joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id")
    ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private RoleEntity role;
}
