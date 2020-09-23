package com.vidge.imageloaderlib.imageloader.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.vidge.imageloaderlib.imageloader.config.ImageConfigs;
import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageCache;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageLoader;
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
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        mImageCache.saveImageToDisk(params.getUrl(), is);
                        mImageCache.saveImageToMemory(MD5Utils.MD5(params.getUrl()), bitmap);
                        setImageBitmap(params.getImageView(),bitmap);
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
            }else {
                setImageBitmap(params.getImageView(),diskBmp);
            }
        }else {
            setImageBitmap(params.getImageView(),
                    mImageCache.getBitmapFromMemory(MD5Utils.MD5(params.getUrl())));
        }


    }
    private void setImageBitmap(final ImageView iv, final Bitmap bmp){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iv.setImageBitmap(bmp);
            }
        });
    }

}
