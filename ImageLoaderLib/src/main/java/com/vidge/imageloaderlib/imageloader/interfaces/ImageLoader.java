package com.vidge.imageloaderlib.imageloader.interfaces;

import android.widget.ImageView;

import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;

/**
 * ClassName NetRequest
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
 * Platform 10
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface ImageLoader {
    void loadImage(ImageLoaderParams params);

    void loadImage(ImageLoaderParams params, OnImageLoadCallback callback);
}
