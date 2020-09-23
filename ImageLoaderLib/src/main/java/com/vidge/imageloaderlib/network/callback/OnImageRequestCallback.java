package com.vidge.imageloaderlib.network.callback;

import java.io.InputStream;

/**
 * ClassName OnImageRequestCallback
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 2020/9/23
 * <p>
 * Version 1.0
 * <p>
 * ProjectName WeChatMoments
 * <p>
 * Platform 42
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface OnImageRequestCallback {
    void onSuccess(InputStream is);
    void onError(String error);
}
