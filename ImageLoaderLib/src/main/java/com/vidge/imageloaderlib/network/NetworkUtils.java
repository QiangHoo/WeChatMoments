package com.vidge.imageloaderlib.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

/**
 * ClassName NetworkUtils
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
 * Platform 13
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class NetworkUtils {
    public static boolean networkAvailable(Context context) {
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (null == capabilities) {
                return false;
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                result = true;
            }
        } else {
            int type = connectivityManager.getActiveNetworkInfo().getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                result = true;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                result = true;
            }
        }
        return result;
    }
}
