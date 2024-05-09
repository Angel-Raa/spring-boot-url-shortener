package com.github.angel.raa.controller;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/shortener")
@RequiredArgsConstructor
@Validated
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    @GetMapping
    public ResponseEntity<Response<ShortUrlResponse>> shortenUrl(@RequestBody  ShortUrlRequest url) {
        return ResponseEntity.ok(urlShortenerService.createShortUrl(url));
    }
}
