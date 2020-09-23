package com.vidge.imageloaderlib.imageloader.config;

import android.os.Environment;

/**
 * ClassName ImageConfigs
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
 * Platform 06
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class ImageConfigs {
    /**
     * default disk cacheSize is
     */
    public static final String DEFAULT_DISK_CACHE_PATH = Environment.getDownloadCacheDirectory().getPath()+"/images";
    public static final long DEFAULT_MEMORY_CACHE_SIZE = Runtime.getRuntime().maxMemory() / 1024 / 8;
    public static final long DEFAULT_DISK_CACHE_SIZE = 512 * 1024 * 1024;


    /**
     * memory cache size
     */
    private long mMemoryCacheSize = DEFAULT_MEMORY_CACHE_SIZE;
    /**
     * disk cache size
     */
    private long mDiskCacheSize = DEFAULT_DISK_CACHE_SIZE;

    public long getMemoryCacheSize() {
        return mMemoryCacheSize;
    }

    public long getDiskCacheSize() {
        return mDiskCacheSize;
    }

    public String getImageCachePath() {
        return mImageCachePath;
    }

    /**
     * image disk cache file path
     */
    private String mImageCachePath = DEFAULT_DISK_CACHE_PATH;


    private ImageConfigs(){

    }
    public void setMemoryCacheSize(long cacheSize){
        this.mMemoryCacheSize = cacheSize;
    }
    public void setDiskCacheSize(long cacheSize){
        this.mDiskCacheSize = cacheSize;
    }
    public void setImageCachePath(String cachePath){
        this.mImageCachePath = cachePath;
    }

    public static class ImageConfigsBuilder{
        private ImageConfigsBuilder mInstance;
        private ImageConfigs mConfigs;
        public ImageConfigsBuilder(){
            mInstance = this;
            mConfigs = new ImageConfigs();
        }
        public ImageConfigsBuilder setMemoryCacheSize(long cacheSize){
            mConfigs.setMemoryCacheSize(cacheSize);
            return mInstance;
        }
        public ImageConfigsBuilder setDiskCacheSize(long diskSize){
            mConfigs.setDiskCacheSize(diskSize);
            return mInstance;
        }
        public ImageConfigsBuilder setImageCachePath(String cachePath){
            mConfigs.setImageCachePath(cachePath);
            return mInstance;
        }
        public ImageConfigs build(){
            return mConfigs;
        }

    }

}
