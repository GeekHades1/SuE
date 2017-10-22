package org.hades.sue.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Hades on 17/7/31.
 *
 * MD5密码加密工具
 */

public class MD5Utils {

    //盐
    private static final String SLAT = "ThisIsSuETeam";

    public static String encoder(String psw) {
        //进行加盐操作
        String password = psw +SLAT;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;// 获取低8位内容
                String hexString = Integer.toHexString(i);

                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }

                sb.append(hexString);
            }

            String md5 = sb.toString();
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
