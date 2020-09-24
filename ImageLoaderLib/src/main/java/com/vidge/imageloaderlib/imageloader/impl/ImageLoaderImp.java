package com.vidge.imageloaderlib.imageloader.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.vidge.imageloaderlib.imageloader.config.ImageConfigs;
import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageCache;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageLoader;
import com.vidge.imageloaderlib.imageloader.interfaces.OnImageLoadCallback;
import com.vidge.imageloaderlib.imageloader.utils.ImageResizer;
import com.vidge.imageloaderlib.imageloader.utils.MD5Utils;
import com.vidge.imageloaderlib.network.HttpRequest;
import com.vidge.imageloaderlib.network.HttpRequestImpl;
import com.vidge.imageloaderlib.network.callback.OnImageRequestCallback;

import java.io.InputStream;

/**
 * ClassName ImageLoaderImp
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
 * Platform 47
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class ImageLoaderImp implements ImageLoader {
    private static ImageLoader mImageLoader;
    private static HttpRequest mClient;
    private static ImageCache mImageCache;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ImageLoaderImp() {
    }

    public static ImageLoader getInstance(Context context) {
        if (null == mImageLoader) {
            synchronized (ImageLoaderImp.class) {
                mImageLoader = new ImageLoaderImp();
            }
        }
        if (null == mClient) {
            synchronized (ImageLoaderImp.class) {
                mClient = new HttpRequestImpl();
                mClient.init();
            }
        }
        if (null == mImageCache) {
            synchronized (ImageLoaderImp.class) {
                mImageCache = new ImageCacheImpl();
                ImageConfigs configs = new ImageConfigs.ImageConfigsBuilder()
                        .setImageCachePath(context.getExternalFilesDir(null).getPath())
                        .build();
                mImageCache.init(context,configs);
            }
        }
        return mImageLoader;
    }

    @Override
    public void loadImage(final ImageLoaderParams params) {
        loadImage(params,null);
    }

    @Override
    public void loadImage(final ImageLoaderParams params, final OnImageLoadCallback callback) {
        if (null == params) {
            throw new IllegalArgumentException("ImageLoaderParams can not be null");
        }
        if (null == params.getUrl()) {
            throw new IllegalArgumentException("Url can not be null");
        }
        if (null == params.getImageView()) {
            throw new IllegalArgumentException("ImageView can not be null");
        }
        if (params.getPlaceholderResId() != -1) {
            params.getImageView().setImageResource(params.getPlaceholderResId());
        }
        int requestWith = params.getImageView().getWidth();
        int requestHeight = params.getImageView().getHeight();
        if (params.getRequestWidth() != -1) {
            requestWith = params.getRequestWidth();
        }
        if (params.getRequestHeight() != -1) {
            requestHeight = params.getRequestHeight();
        }
        if (null == mImageCache.getBitmapFromMemory(MD5Utils.MD5(params.getUrl()))){
            Bitmap diskBmp = mImageCache.getBitmapFromDisk(params.getUrl(),requestWith,requestHeight);
            if (null == diskBmp){
                mClient.getImageRequest(params.getUrl(), new OnImageRequestCallback() {
                    @Override
                    public void onSuccess(InputStream is) {
                        int requestWith = params.getImageView().getWidth();
                        int requestHeight = params.getImageView().getHeight();
                        if (params.getRequestWidth() != -1) {
                            requestWith = params.getRequestWidth();
                        }
                        if (params.getRequestHeight() != -1) {
                            requestHeight = params.getRequestHeight();
                        }
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        mImageCache.saveImageToDisk(params.getUrl(), is);
                        mImageCache.saveImageToMemory(MD5Utils.MD5(params.getUrl()), bitmap);
                        setImageBitmap(params.getImageView(),bitmap,params.getCirCleCorner(),requestWith,requestHeight);
                        if (null != callback){
                            callback.onSuccess();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if (null != callback){
                            callback.onError();
                        }
                    }
                });
            }else {
                setImageBitmap(params.getImageView(),diskBmp,params.getCirCleCorner(),requestWith,requestHeight);
                if (null != callback){
                    callback.onSuccess();
                }
            }
        }else {
            setImageBitmap(params.getImageView(),
                    mImageCache.getBitmapFromMemory(MD5Utils.MD5(params.getUrl())),params.getCirCleCorner(),requestWith,requestWith);
            if (null != callback){
                callback.onSuccess();
            }
        }
    }

    private void setImageBitmap(final ImageView iv, final Bitmap bmp, final int circleCorner, final int reqW, final int reqH){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (circleCorner != 0){
                    Bitmap bitmap = ImageResizer.getRoundBitmapByShader(bmp, reqW,reqH, circleCorner,0);
                    iv.setImageBitmap(bitmap);
                }else {
                    iv.setImageBitmap(bmp);
                }
            }
        });
    }

}
