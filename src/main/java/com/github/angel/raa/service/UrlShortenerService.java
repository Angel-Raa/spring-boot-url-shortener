package com.github.angel.raa.service;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import org.jetbrains.annotations.NotNull;

public interface UrlShortenerService {
    Response<ShortUrlResponse> createShortUrl(ShortUrlRequest url);
    Response<ShortUrlResponse> getFullUrl(ShortUrlRequest url);


}
