package com.kritika.spring_boot_url_shortener.domain.models;

public record CreateShortUrlCmd(
        String originalUrl,
        Boolean isPrivate,
        Integer expirationInDays,
        Long userId
) {
}