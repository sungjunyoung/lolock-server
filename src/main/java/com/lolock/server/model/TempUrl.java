package com.lolock.server.model;

import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.Random;

public class TempUrl {
    private int key;
    private String pathVariable;

    public TempUrl(int key) {
        this.key = key;
        this.pathVariable = generatePathVariable();
    }

    public String getPathVariable(){
        return this.pathVariable;
    }

    private String generatePathVariable() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
