package com.github.angel.raa.controller;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.dto.UserAndShortUrlsDTO;
import com.github.angel.raa.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shortener")
@RequiredArgsConstructor
@Validated
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<Response<ShortUrlResponse>> shortenUrl(@Valid @RequestBody  ShortUrlRequest url) {
        return new ResponseEntity<>(urlShortenerService.createShortUrl(url), HttpStatus.CREATED);
    }

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Response<ShortUrlResponse>> getFullUrl (@PathVariable ShortUrlRequest key){
        return ResponseEntity.ok(urlShortenerService.getFullUrl(key));
    }
    @GetMapping("/urls")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Response<UserAndShortUrlsDTO>> getUserUrls(){
        return ResponseEntity.ok(urlShortenerService.getUserAndShortUrlsDTO());
    }


    @GetMapping("/r/{key}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAuthority('READ')")
    @PreAuthorize("permitAll")
    public String redirect(@PathVariable String key, HttpServletResponse response){
        return urlShortenerService.redirect(key, response);
    }

    @GetMapping("/hey")
    @PreAuthorize("permitAll")
    public String hey (){
        return "Hola Mundo";
    }



}
