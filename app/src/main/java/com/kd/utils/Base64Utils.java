package com.kd.utils;

import android.util.Base64;

public class Base64Utils
{
    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    public static String encodeToString(String str){
            return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }
    /**
     * 字符Base64解密
     * @param str
     * @return
     */
    public static String decodeToString(String str){
            return new String(Base64.decode(str.getBytes(), Base64.NO_WRAP));

    }
}