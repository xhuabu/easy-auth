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
public class CryptUtil {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 对字符串进行MD5加密
     *
     * @param text 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String md5(String text) {
        return md5(text, null);
    }


    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text, String charsetName) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }
        try {
            if (charsetName == null) {
                msgDigest.update(text.getBytes());
            } else {
                msgDigest.update(text.getBytes(charsetName));
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");
        }

        byte[] bytes = msgDigest.digest();
        return new String(encodeHex(bytes));
    }


    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }


}
