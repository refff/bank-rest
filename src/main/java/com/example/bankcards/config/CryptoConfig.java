package com.example.bankcards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Configuration
public class CryptoConfig {

    @Bean
    public SecretKey cardSecretKey(@Value("${cryptoKey}") String base64Key) {

        byte[] decoded = Base64.getDecoder().decode(base64Key);

        return new SecretKeySpec(decoded, "AES");
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
