package com.kritika.spring_boot_url_shortener;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {

    public static void main(String[] args) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("USER Password:");
        System.out.println(encoder.encode("secret"));

        System.out.println();

        System.out.println("ADMIN Password:");
        System.out.println(encoder.encode("admin"));
    }
}