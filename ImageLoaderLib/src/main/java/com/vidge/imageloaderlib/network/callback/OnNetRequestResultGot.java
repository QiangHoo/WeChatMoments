package com.vidge.imageloaderlib.network.callback;

/**
 * ClassName OnNetRequestResultGot
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
 * Platform 13
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface OnNetRequestResultGot {
    void onSuccess(String response);
    void onError(String error);
}
