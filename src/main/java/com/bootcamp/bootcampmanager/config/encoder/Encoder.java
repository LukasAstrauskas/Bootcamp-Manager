package com.bootcamp.bootcampmanager.config.encoder;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;


public class Encoder extends BCryptPasswordEncoder {

    public Encoder() {
        super(BCryptVersion.$2A, 12, new SecureRandom(new byte[16]));
    }

    public static BCryptPasswordEncoder get() {

        BCryptPasswordEncoder.BCryptVersion version = BCryptPasswordEncoder.BCryptVersion.$2Y;
        int strength = 12;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return new BCryptPasswordEncoder(version, strength, random);
    }

}
