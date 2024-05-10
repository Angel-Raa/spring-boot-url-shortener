package com.github.angel.raa.service.impl;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.persistence.entity.ShortUrlEntity;
import com.github.angel.raa.persistence.repository.ShortUrlRepository;
import com.github.angel.raa.service.UrlShortenerService;
import com.github.angel.raa.utils.ShortUrlUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlUtil shortUrlUtil;
    @Transactional
    @Override
    public Response<ShortUrlResponse> createShortUrl(@NotNull ShortUrlRequest url) {
        String key = shortUrlUtil.generateShortUrl(url.url());
        ShortUrlEntity shortUrl = new ShortUrlEntity();
        shortUrl.setKey(key);
        shortUrl.setFullUrl(url.url());
        System.out.println(key);
        shortUrlRepository.save(shortUrl);
        ShortUrlResponse response = new ShortUrlResponse();
        response.setKey(key);
        return Response.<ShortUrlResponse>builder()
                .message("short url created")
                .data(response)
                .status(CREATED)
                .error(false)
                .code(200)
                .build();


    }
}
