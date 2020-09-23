package com.vidge.imageloaderlib.network;

import com.vidge.imageloaderlib.network.callback.OnImageRequestCallback;
import com.vidge.imageloaderlib.network.callback.OnNetRequestResultGot;

import java.util.HashMap;

/**
 * ClassName HttpRequest
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
 * Platform 11
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface HttpRequest {
    void init();
    void getRequest(String url, OnNetRequestResultGot netRequestResult);
    void getImageRequest(String url, OnImageRequestCallback callback);
    void postRequest(String url, HashMap<String,String> params, OnNetRequestResultGot netRequestResult);
}
