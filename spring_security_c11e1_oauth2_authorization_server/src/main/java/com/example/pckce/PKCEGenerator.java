package com.example.pckce;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PKCEGenerator {

    public static void main(String[] args) {
        // Step 1: Generate a random code_verifier
        String codeVerifier = generateCodeVerifier();

        // Step 2: Generate the code_challenge
        String codeChallenge = generateCodeChallenge(codeVerifier);

        System.out.println("code_verifier: " + codeVerifier);
        System.out.println("code_challenge: " + codeChallenge);
    }

    private static String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    private static String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(codeVerifier.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}

/**
 code_verifier: Pr1aaJNdJnhRdeNakYdP_fAqc4TOXXi-E_2ZR_ARdmM
 code_challenge: e1EUD3UbLMuTgaedSxse09Ri1T1L-G8cpkzx1AHIdHU
 */


