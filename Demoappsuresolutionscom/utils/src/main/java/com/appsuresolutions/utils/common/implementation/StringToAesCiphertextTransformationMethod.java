package com.appsuresolutions.utils.common.implementation;

import android.util.Base64;
import android.util.Xml;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StringToAesCiphertextTransformationMethod implements IDataTransformationMethod<String,String> {
    private final char[] pin;


    public StringToAesCiphertextTransformationMethod(char[] pin) {
        this.pin = pin;
    }

    @Override
    public String process(@NotNull String data) throws Throwable {
        final byte[] initial = Charset.forName("UTF-8").encode(CharBuffer.wrap(pin)).array();
        final byte[] forKey = MultiHasher.Derivate(initial,413);
        final byte[] forIv = MultiHasher.Derivate(initial,137);
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        final SecretKeySpec secretKeySpec = new SecretKeySpec(forKey,0,16, "AES");
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(forIv,0,16);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        final byte[] ciphered = cipher.doFinal(data.getBytes("UTF-8"));
        Arrays.fill(initial, (byte) 0x00);
        Arrays.fill(forKey, (byte) 0x00);
        Arrays.fill(forIv, (byte) 0x00);
        return Hex.Encode(ciphered);
    }

    @Override
    protected void finalize() throws Throwable {
        Arrays.fill(pin, '™');
        super.finalize();
    }
}
