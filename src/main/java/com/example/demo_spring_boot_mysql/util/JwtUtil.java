package com.example.demo_spring_boot_mysql.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // Tạo Access Token
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .compact();
    }
    // Tạo Refresh Token
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .compact();
    }
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Constants.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            System.out.println("refreshAccessToken " + refreshToken);
            if (isTokenExpired(refreshToken)) {
                throw new RuntimeException("Refresh token is expired");
            }

            String email = extractEmail(refreshToken);
            return generateAccessToken(email);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid token format", e);
        }
    }
    public  String encrypt(String password) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(Constants.CRYPTO_KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.INIT_VECTOR.getBytes());

            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);


        } catch (Exception ex) {
            return null;
        }

    }

    public  String decrypt(String message) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(Constants.CRYPTO_KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.INIT_VECTOR.getBytes());

            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(message);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted);
        } catch (Exception ex) {

            return null;

        }
    }

    public static class RefreshTokenRequest {
        private String refreshToken;
        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}

