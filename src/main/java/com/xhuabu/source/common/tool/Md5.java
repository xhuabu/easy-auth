package com.xhuabu.source.common.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lee on 17/9/1.
 * 加密解密工具类
 *
 * @CreatedBy lee
 * @Date 17/9/1
 */
public class Md5 {

    public static String md5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
        byte[] md5Bytes;
        try {
            md5Bytes = md5.digest(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toLowerCase();
    }

    public static void main(String[] args) {
        System.out.println(md5("s155964671a"));
        System.out.println(md5("s155964671a"));
    }

}
