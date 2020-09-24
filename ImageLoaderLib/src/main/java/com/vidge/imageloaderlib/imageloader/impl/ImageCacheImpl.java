package com.vidge.imageloaderlib.imageloader.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;


import com.jakewharton.disklrucache.DiskLruCache;
import com.vidge.imageloaderlib.imageloader.config.ImageConfigs;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageCache;
import com.vidge.imageloaderlib.imageloader.utils.ImageResizer;
import com.vidge.imageloaderlib.imageloader.utils.MD5Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName ImageCacheImpl
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
 * Platform 35
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class ImageCacheImpl implements ImageCache {
    private static final String TAG = ImageCacheImpl.class.getSimpleName();
    private static final String EXCEPTION_CONTEXT = "context can not be null";
    private static final int APP_VERSION_CODE = 1;

    private LruCache<String, Bitmap> mMemoryCache;
    private long mMemoryCacheSize;
    private long mDiskCacheSize;
    private String mDiskPath;
    private DiskLruCache mDiskLruCache;

    @Override
    public void init(Context context, ImageConfigs imageConfigs) {
        if (null == context) {
            throw new IllegalArgumentException(EXCEPTION_CONTEXT);
        }
        if (imageConfigs != null) {
            mMemoryCacheSize = imageConfigs.getMemoryCacheSize();
            mDiskCacheSize = imageConfigs.getDiskCacheSize();
            mDiskPath = imageConfigs.getImageCachePath();
        } else {
            mMemoryCacheSize = ImageConfigs.DEFAULT_MEMORY_CACHE_SIZE;
            mDiskCacheSize = ImageConfigs.DEFAULT_DISK_CACHE_SIZE;
            mDiskPath = ImageConfigs.DEFAULT_DISK_CACHE_PATH;
        }


        mMemoryCache = new LruCache<String, Bitmap>((int) mMemoryCacheSize) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

        File file = new File(mDiskPath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                boolean isOk = file.mkdirs();
                Logger.getLogger(TAG).log(Level.SEVERE,
                        "File mkdirs " + (isOk ? "success" : "failed") + ":" + mDiskPath);
            }
        }
        try {
            mDiskLruCache = DiskLruCache.open(file, APP_VERSION_CODE, 1, mDiskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(TAG).log(Level.SEVERE, "DiskLruCache open failed");
        }
    }

    @Override
    public void saveImageToMemory(String key, Bitmap bmp) {
        if (null == key) {
            throw new IllegalArgumentException("key can not be null");
        }
        if (null == bmp) {
            throw new IllegalArgumentException("image bitmap can not be null");
        }
        if (null == mMemoryCache) {
            throw new IllegalStateException("ImageLoader maybe not init,please init ImageLoader!");
        }
        if (null == mMemoryCache.get(key)) {
            mMemoryCache.put(key, bmp);
        }

    }

    @Override
    public void saveImageToDisk(String key, InputStream is) {
        if (null == key) {
            throw new IllegalArgumentException("key can not be null");
        }
        if (null == is) {
            throw new IllegalArgumentException("image bitmap can not be null");
        }

        String encodeKey = MD5Utils.MD5(key);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(encodeKey);
            OutputStream os = editor.newOutputStream(0);
            if (writeFileToDisk(os, is)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeFileToDisk(OutputStream os, InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int len;
        try {
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
            bis.close();
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Bitmap getBitmapFromMemory(String key) {
        if (null == key) {
            throw new IllegalArgumentException("key can not be null");
        }
        if (null == mMemoryCache) {
            throw new IllegalStateException("ImageLoader maybe not init,please init ImageLoader!");
        }
        if (mMemoryCache.size() == 0) {
            return null;
        }
        return mMemoryCache.get(key);
    }

    @Override
    public Bitmap getBitmapFromDisk(String key, int requireW, int requireH) {
        Bitmap bmp = null;
        String encodeKey = MD5Utils.MD5(key);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(encodeKey);
            if (null != snapshot) {
                FileInputStream is = (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fd = is.getFD();
                bmp = ImageResizer.getFromFileDescriptor(fd, requireW, requireH);
                if (null != bmp) {
                    saveImageToMemory(encodeKey, bmp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
