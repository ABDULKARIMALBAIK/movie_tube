package com.abdalkarimalbiekdev.movietube.Security;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static String ENCRYPTION_KEY = "...................";
    private static String INITIALIZATIO_VECTOR = ".....................";


    public static String encrypt(String plainText) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/pkcs5padding");
        SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(INITIALIZATIO_VECTOR.getBytes("UTF-8")));
        byte[] encryptedMsg = cipher.doFinal(plainText.getBytes("UTF-8"));
        String cipherText = Base64.encodeToString(encryptedMsg , Base64.DEFAULT);

        return cipherText;
    }



    public static String decrypt(String cipherText) throws Exception{

        byte[] base64Encrypted = Base64.decode(cipherText , Base64.DEFAULT);

        Cipher cipher = Cipher.getInstance("AES/CBC/pkcs5padding");
        SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(INITIALIZATIO_VECTOR.getBytes("UTF-8")));

        String plainText = new String(cipher.doFinal(base64Encrypted),"UTF-8");

        return plainText;

    }

}
