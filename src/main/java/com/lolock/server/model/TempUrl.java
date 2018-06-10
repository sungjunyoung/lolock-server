package com.lolock.server.model;

import java.util.Random;

public class TempUrl {
    private int key;
    private String pathVariable;

    public TempUrl() {
    }

    public TempUrl(int key) {
        this.key = key;
        this.pathVariable = generatePathVariable();
    }

    public String getPathVariable(){
        return this.pathVariable;
    }

    public int getKey(){
        return this.key;
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
