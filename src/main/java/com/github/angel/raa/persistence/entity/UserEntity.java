package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity extends Auditable implements UserDetails {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username")
    private String username;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CredentialEntity.class, orphanRemoval = true)
    @JoinColumn(name = "password")
    private CredentialEntity credential;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private RoleEntity role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return Collections.emptyList();
        if (role.getAuthorities() == null) return Collections.emptyList();
        return role.getAuthorities().getPermissions().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return credential.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
