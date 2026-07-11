package com.kritika.spring_boot_url_shortener.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlExistenceValidator {

    private static final Logger log =
            LoggerFactory.getLogger(UrlExistenceValidator.class);

    public static boolean isUrlExists(String urlString) {

        try {
            log.debug(
                    "Kritika URL Shortener - Checking URL: {}",
                    urlString
            );

            URL url = new URI(urlString).toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            return responseCode >= 200
                    && responseCode < 400;

        } catch (Exception e) {

            log.error(
                    "Kritika URL Shortener - Invalid URL: {}",
                    urlString,
                    e
            );

            return false;
        }
    }
}