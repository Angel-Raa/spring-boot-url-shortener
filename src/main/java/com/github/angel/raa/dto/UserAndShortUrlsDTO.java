package com.github.angel.raa.dto;

import java.util.List;

public record UserAndShortUrlsDTO(
        String username,
        String email,
        List<ShortUrlResponse> shortUrlResponseList
) {
}
