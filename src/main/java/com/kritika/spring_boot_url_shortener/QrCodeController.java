package com.kritika.spring_boot_url_shortener.web.controllers;

import com.kritika.spring_boot_url_shortener.domain.services.QrCodeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping(
            value = "/qr/{key}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getQrCode(
            @PathVariable String key
    ) throws Exception {

        String shortUrl =
                "https://kritika-url-shortener-production.up.railway.app/s/" + key;

        return qrCodeService.generateQrCode(shortUrl);
    }
}