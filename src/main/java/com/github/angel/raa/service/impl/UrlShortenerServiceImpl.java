package com.github.angel.raa.service.impl;

import com.github.angel.raa.dto.Response;
import com.github.angel.raa.dto.ShortUrlRequest;
import com.github.angel.raa.dto.ShortUrlResponse;
import com.github.angel.raa.persistence.repository.ShortUrlRepository;
import com.github.angel.raa.service.UrlShortenerService;
import com.github.angel.raa.utils.ShortUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlUtil shortUrlUtil;
    @Transactional
    @Override
    public Response<ShortUrlResponse> createShortUrl(ShortUrlRequest url) {
        return null;
    }
}
