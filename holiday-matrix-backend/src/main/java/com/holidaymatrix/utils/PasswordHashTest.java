package com.holidaymatrix.utils; // Adaptez selon votre package

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String motDePasse = "skander123";
        String hachage = encoder.encode(motDePasse);
        System.out.println(hachage);
    }
}