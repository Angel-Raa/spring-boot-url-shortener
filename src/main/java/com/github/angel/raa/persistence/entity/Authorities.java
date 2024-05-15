package com.github.angel.raa.persistence.entity;

import com.github.angel.raa.utils.Permission;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public enum Authorities {
    ROLE_ADMINISTRATOR(
            List.of(
            Permission.CREATE,
            Permission.DELETE,
            Permission.READ,
            Permission.DISABLE,
            Permission.UPDATE

            )
    ),
    ROLE_USER(
            List.of(
                    Permission.DISABLE,
                    Permission.UPDATE,
                    Permission.CREATE,
                    Permission.READ
            )
    );

    private final List<Permission> permissions;

    Authorities(List<Permission> permissions) {
        this.permissions = permissions;
    }


}
