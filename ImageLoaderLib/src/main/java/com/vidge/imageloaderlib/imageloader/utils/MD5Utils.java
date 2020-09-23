package com.vidge.imageloaderlib.imageloader.utils;

import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName MD5Utils
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 2020/9/22
 * <p>
 * Version 1.0
 * <p>
 * ProjectName WeChatMoments
 * <p>
 * Platform 52
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class MD5Utils {
    private static final String TAG = MD5Utils.class.getSimpleName();
    private static final String[] HEX_DIG_ITEMS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String MD5(String str) {
        String resultString = null;
        try {
            resultString = str;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(TAG).log(Level.SEVERE,"MD5 encode error");
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        AtomicReference<StringBuilder> resultSb = new AtomicReference<>(new StringBuilder());
        for (byte value : b) {
            resultSb.get().append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIG_ITEMS[d1] + HEX_DIG_ITEMS[d2];
    }
}
