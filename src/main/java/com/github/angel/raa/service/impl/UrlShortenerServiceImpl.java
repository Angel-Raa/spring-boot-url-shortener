package com.github.angel.raa.service.impl;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.dto.UserAndShortUrlsDTO;
import com.github.angel.raa.exception.ShortUrlNotFoundException;
import com.github.angel.raa.persistence.entity.ShortUrlEntity;

import com.github.angel.raa.persistence.entity.UserEntity;
import com.github.angel.raa.persistence.repository.ShortUrlRepository;
import com.github.angel.raa.service.AuthenticationService;
import com.github.angel.raa.service.UrlShortenerService;
import com.github.angel.raa.utils.ShortUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log
@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortUrlRepository shortUrlRepository;
    private final AuthenticationService service;
    private final ShortUtils shortUtils;
    @Transactional
    @Override
    public Response<ShortUrlResponse> createShortUrl(@NotNull ShortUrlRequest url) {
        String key = shortUtils.generateShortUrl(url.url());
        var user = service.getAuthenticatedUser();
        ShortUrlEntity shortUrl = new ShortUrlEntity();
        shortUrl.setKey(key);
        shortUrl.setUser(user);
        shortUrl.setFullUrl(url.url());
        shortUrl.setClickCount(0L);
        log.info(String.valueOf(url));
        shortUrlRepository.save(shortUrl);
        ShortUrlResponse response = new ShortUrlResponse();
        response.setKey(key);
        response.setFullUrl(shortUrl.getFullUrl());
        return Response.<ShortUrlResponse>builder()
                .message("short url created")
                .data(response)
                .status(CREATED)
                .error(false)
                .timestamp(now())
                .code(200)
                .build();
    }
    @Transactional(readOnly = true)
    @Override
    public Response<ShortUrlResponse> getFullUrl(@NotNull ShortUrlRequest url) {
        ShortUrlEntity shortUrl = shortUrlRepository.findByKey(url.url()).orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found for key: "));
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlRepository.save(shortUrl);
        ShortUrlResponse response = new ShortUrlResponse();
        response.setFullUrl(shortUrl.getFullUrl());
        response.setKey(shortUrl.getKey());
        return Response.<ShortUrlResponse>builder()
                .message("Full URL found")
                .data(response)
                .status(OK)
                .error(false)
                .timestamp(now())
                .code(200)
                .build();


    }
    @Transactional(readOnly = true)
    @Override
    public Response<UserAndShortUrlsDTO> getUserAndShortUrlsDTO() {
        UserEntity user = service.getAuthenticatedUser();
        List<ShortUrlEntity> shortUrls =  shortUrlRepository.findAllByUserUsername(user.getUsername());
        List<ShortUrlResponse> shortUrlResponses = shortUrls.stream()
                .map(url -> new ShortUrlResponse(url.getKey(), url.getFullUrl()))
                .toList();
        UserAndShortUrlsDTO shortUrlsDTO = new UserAndShortUrlsDTO(user.getUsername(), user.getEmail(),shortUrlResponses);
        return Response.<UserAndShortUrlsDTO>builder()
                .message("User and short URLs fetched successfully")
                .status(OK)
                .error(false)
                .timestamp(now())
                .code(200)
                .data(shortUrlsDTO)
                .build();
    }

    @Override
    public String redirect(String key, HttpServletResponse response) {
        ShortUrlEntity shortener = shortUrlRepository.findByKey(key).orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found for key: "));
        if(shortener != null){
            return "redirect:"+ shortener.getFullUrl();
        }
        else {
            return "error";
        }



    }


}
