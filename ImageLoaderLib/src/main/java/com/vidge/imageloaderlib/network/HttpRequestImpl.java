package com.vidge.imageloaderlib.network;

import com.vidge.imageloaderlib.network.callback.OnImageRequestCallback;
import com.vidge.imageloaderlib.network.callback.OnNetRequestResultGot;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ClassName HttpRequestImpl
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
 * Platform 15
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class HttpRequestImpl implements HttpRequest {
    OkHttpClient mClient;

    @Override
    public void init() {
        if (null == mClient) {
            synchronized (HttpRequestImpl.class) {
                mClient = new OkHttpClient();
            }
        }
    }

    @Override
    public void getRequest(final String url, final OnNetRequestResultGot netRequestResult) {
        if (null == mClient){
            throw new IllegalStateException("OkHttpClient is null,please call HttpRequestImpl.init()");
        }
        final Request request = new Request.Builder().get().url(url).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != netRequestResult) {
                    netRequestResult.onError("OkHttp getRequest \" " + url + "\" failed");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != netRequestResult) {
                    try {
                        netRequestResult.onSuccess(response.body().string());
                    } catch (NullPointerException e) {
                        netRequestResult.onError("OkHttp response.body is null");
                    }
                }
            }
        });
    }

    @Override
    public void getImageRequest(final String url, final OnImageRequestCallback callback) {
        if (null == mClient){
            throw new IllegalStateException("OkHttpClient is null,please call HttpRequestImpl.init()");
        }
        final Request request = new Request.Builder().get().url(url).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != callback) {
                    callback.onError("OkHttp getImageRequest \" " + url + "\" failed");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != callback) {
                    try {
                        callback.onSuccess(response.body().byteStream());
                    } catch (NullPointerException e) {
                        callback.onError("OkHttp image response.body is null");
                    }
                }
            }
        });
    }

    @Override
    public void postRequest(final String url, HashMap<String, String> params, final OnNetRequestResultGot netRequestResult) {
        if (null == mClient){
            throw new IllegalStateException("OkHttpClient is null,please call HttpRequestImpl.init()");
        }
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            String value = params.get(entry.getKey());
            formBuilder.add(entry.getKey(), null == value ? "" : value);
        }
        FormBody formBody = formBuilder.build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != netRequestResult) {
                    netRequestResult.onError("OkHttp postRequest \" " + url + "\" failed");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != netRequestResult) {
                    try {
                        netRequestResult.onSuccess(response.body().toString());
                    } catch (NullPointerException e) {
                        netRequestResult.onError("OkHttp response.body is null");
                    }
                }
            }
        });
    }
}
