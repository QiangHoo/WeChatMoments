package com.vidge.imageloaderlib.imageloader.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

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

    /**
     * create image with corner
     *
     * @param bitmap    image bitmap
     * @param outWidth  image width
     * @param outHeight image height
     * @param radius    corner radius
     * @param boarder   boarder width
     */
    public static Bitmap getRoundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius, int boarder) {
        if (bitmap == null) {
            return null;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        float widthScale = outWidth * 1f / width;
        float heightScale = outHeight * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //create bitmap for output
        Bitmap desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        //create canvas to draw image
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //set matrix to BitmapShader
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //create Rect and leave blank for boarder
        RectF rect = new RectF(boarder, boarder, outWidth - boarder, outHeight - boarder);
        //draw the image
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (boarder > 0) {
            //draw the boarder
            Paint boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boarderPaint.setColor(Color.GREEN);
            boarderPaint.setStyle(Paint.Style.STROKE);
            boarderPaint.setStrokeWidth(boarder);
            canvas.drawRoundRect(rect, radius, radius, boarderPaint);
        }
        return desBitmap;
    }

}
