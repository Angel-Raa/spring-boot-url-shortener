package com.github.angel.raa.service;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.dto.UserAndShortUrlsDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface UrlShortenerService {
    Response<ShortUrlResponse> createShortUrl(ShortUrlRequest url);
    Response<ShortUrlResponse> getFullUrl(ShortUrlRequest url);
    Response<UserAndShortUrlsDTO> getUserAndShortUrlsDTO ();
    String redirect (String key, HttpServletResponse response);


}
