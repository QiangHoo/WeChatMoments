package com.vidge.imageloaderlib.imageloader.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.vidge.imageloaderlib.imageloader.config.ImageConfigs;

import java.io.InputStream;

/**
 * ClassName ImageCache
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
 * Platform 04
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface ImageCache {
    /**
     * initial ImageCache
     * @param context Context
     * @param imageConfigs ImageConfigs
     */
    void init(Context context, ImageConfigs imageConfigs);
    /**
     * save image to memory
     * @param key LruCache key
     * @param bmp LruCache content is bitmap
     */
    void saveImageToMemory(String key, Bitmap bmp);

    /**
     * save image to disk
     * @param key LruCache key
     * @param is LruCache content is inputStream
     */
    void saveImageToDisk(String key, InputStream is);

    /**
     * get image from memory
     * @param key LruCache key
     * @return bitmap
     */
    Bitmap getBitmapFromMemory(String key);

    /**
     * get image from disk
     * @param key LruCache key
     * @param requireW image width
     * @param requireH image height
     * @return bitmap
     */
    Bitmap getBitmapFromDisk(String key, int requireW, int requireH);
}
