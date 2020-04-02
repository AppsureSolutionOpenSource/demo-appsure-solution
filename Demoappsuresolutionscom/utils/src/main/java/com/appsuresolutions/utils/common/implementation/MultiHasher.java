package com.appsuresolutions.utils.common.implementation;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MultiHasher {

    public static byte[] Derivate(@NotNull byte[] input, int iterations) throws NoSuchAlgorithmException {
        if(input == null || input.length == 0){
            throw new RuntimeException("Invalid input.");
        }
        if(iterations < 10){
            throw new RuntimeException("Invalid iterations.");
        }
        final MessageDigest sha = MessageDigest.getInstance("SHA-1");
        for (int i = 0; i < iterations; i++) {
            sha.update(input);
        }
        return sha.digest();
    }
}
