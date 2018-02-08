package com.xhuabu.source.common.tool;

/**
 * Created by lee on 17/4/24.
 * 字符串相关工具类
 *
 * @CreatedBy lee
 * @Date 17/4/24
 */
public class StringUtil {

    /**
     *  字符串是否为null或""
     *  @param str 待验证字符串
     *  @return boolean 是否为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}

