package com.stampcrush.backend.application.manager.owner.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryptor {

    private static final String ALGORITHM = "SHA-256";

    public static String encrypt(String password)  {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(password.getBytes());
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException("암호화 알고리즘이 존재하지 않습니다.");
        }
    }
}
