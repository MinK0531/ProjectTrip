package com.mink.projecttrip.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256HashingEncoder {

    public static String encode(String message){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("sha256");

            byte[] bytes = message.getBytes();

            messageDigest.update(bytes);

            byte[] digest = messageDigest.digest();

            StringBuilder result = new StringBuilder();

            for ( int i = 0; i < digest.length; i++ ) {
                result.append(Integer.toHexString(digest[i] & 0xff));
            }

            return result.toString();

        }catch(NoSuchAlgorithmException e){
            return null;

        }
    }
}
