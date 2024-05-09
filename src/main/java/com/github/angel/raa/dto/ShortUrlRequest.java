package com.github.angel.raa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ShortUrlRequest(
        @NotBlank(message = "La URL es obligatoria")
        @Pattern(regexp = "^(http|https)://.*$", message = "La URL debe empezar con http:// o https://")
        String url

) {
}
