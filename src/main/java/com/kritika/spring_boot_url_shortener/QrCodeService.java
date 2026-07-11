package com.kritika.spring_boot_url_shortener.domain.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    public byte[] generateQrCode(String text) throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        var bitMatrix = qrCodeWriter.encode(
                text,
                BarcodeFormat.QR_CODE,
                250,
                250
        );

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                bitMatrix,
                "PNG",
                outputStream
        );

        return outputStream.toByteArray();
    }
}