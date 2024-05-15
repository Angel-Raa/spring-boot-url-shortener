package com.github.angel.raa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Login(
        @NotBlank(message = "El nombre de usuario es obligatorio\"")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")

        String password
) {
}
