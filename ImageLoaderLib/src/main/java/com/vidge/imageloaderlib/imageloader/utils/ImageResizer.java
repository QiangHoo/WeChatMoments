package com.vidge.imageloaderlib.imageloader.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * ClassName ImageResizer
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
 * Platform 54
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class ImageResizer {
    public static Bitmap getFromResourceId(Resources res, int resId, int requireW, int requireH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int inSampleSize = calculateInSampleSize(options, requireW, requireH);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap getFromFileDescriptor(FileDescriptor fd, int requireW, int requireH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        int inSampleSize = calculateInSampleSize(options, requireW, requireH);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);

    }
    public static Bitmap getFromStream(InputStream is, int requireW, int requireH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        int inSampleSize = calculateInSampleSize(options, requireW, requireH);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeStream(is, null, options);

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int requireW, int requireH) {
        int inSample = 1;
        int outW = options.outWidth;
        int outH = options.outHeight;
        if (outW > requireW * 2 && outH > requireH * 2) {
            while (true) {
                if (outW / inSample > requireW && outH / inSample > requireH) {
                    inSample++;
                } else {
                    break;
                }
            }
        }
        return inSample;
    }
}
