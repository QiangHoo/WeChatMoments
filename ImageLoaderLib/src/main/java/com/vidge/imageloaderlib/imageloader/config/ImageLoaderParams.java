package com.vidge.imageloaderlib.imageloader.config;

import android.widget.ImageView;

/**
 * ClassName ImageLoaderParams
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
 * Platform 14
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class ImageLoaderParams {
    private ImageView mImageView;
    private String mUrl;
    private int mPlaceholderResId = -1;
    private int mRequestWidth = -1;
    private int mRequestHeight = -1;

    public ImageView getImageView() {
        return mImageView;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getPlaceholderResId() {
        return mPlaceholderResId;
    }

    public int getRequestWidth() {
        return mRequestWidth;
    }

    public int getRequestHeight() {
        return mRequestHeight;
    }

    private ImageLoaderParams(){}
    public void setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setPlaceholderResId(int mPlaceholderResId) {
        this.mPlaceholderResId = mPlaceholderResId;
    }

    public void setRequestWidth(int mRequestWidth) {
        this.mRequestWidth = mRequestWidth;
    }

    public void setRequestHeight(int mRequestHeight) {
        this.mRequestHeight = mRequestHeight;
    }

    public static class ImageLoaderParamsBuilder{
        private ImageLoaderParamsBuilder mInstance;
        private ImageLoaderParams mImageLoaderParams;
        public  ImageLoaderParamsBuilder(String url,ImageView imageView){
            mInstance = this;
            mImageLoaderParams = new ImageLoaderParams();
            mImageLoaderParams.setImageView(imageView);
            mImageLoaderParams.setUrl(url);
        }
        public ImageLoaderParamsBuilder setPlaceholderResId(int resId){
            mImageLoaderParams.setPlaceholderResId(resId);
            return mInstance;
        }
        public ImageLoaderParamsBuilder setRequestWidth(int mRequestWidth){
            mImageLoaderParams.setRequestWidth(mRequestWidth);
            return mInstance;
        }
        public ImageLoaderParamsBuilder setRequestHeight(int mRequestHeight){
            mImageLoaderParams.setRequestHeight(mRequestHeight);
            return mInstance;
        }
        public ImageLoaderParams build(){
            return mImageLoaderParams;
        }
    }
}
