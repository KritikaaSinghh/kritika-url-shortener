package com.kritika.spring_boot_url_shortener.domain.models;

import java.io.Serializable;

public record UserDto(
        Long id,
        String name
) implements Serializable {
}