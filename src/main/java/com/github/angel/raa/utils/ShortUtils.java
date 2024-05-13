package com.github.angel.raa.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ShortUtils {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private @NotNull String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(BASE62.charAt((int) (number % 62)));
            number /= 62;
        }
        return sb.reverse().toString();
    }

    private long decode(@NotNull String shortUrl) {
        long number = 0;
        for (int i = 0; i < shortUrl.length(); i++) {
            char ch = shortUrl.charAt(i);
            number = number * 62 + BASE62.indexOf(ch);
        }
        return number;
    }

    public String generateShortUrl(@NotNull String url) {
        return encode(url.hashCode());
    }
}
