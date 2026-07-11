package com.kritika.spring_boot_url_shortener.web;

import com.kritika.spring_boot_url_shortener.domain.exceptions.ShortUrlNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public String handleShortUrlNotFoundException(
            ShortUrlNotFoundException ex) {

        log.error(
                "Kritika URL Shortener - Short URL not found: {}",
                ex.getMessage()
        );

        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception ex) {

        log.error(
                "Kritika URL Shortener - Unexpected error occurred: {}",
                ex.getMessage(),
                ex
        );

        return "error/500";
    }
}