package com.vidge.wechatmoments.ui.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * ClassName CustomMaterialProgressDrawable
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
 * Platform 54
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class CustomMaterialProgressDrawable extends MaterialProgressDrawable {
    private float rotation;
    private Bitmap mBitmap;
    final Paint mPaint = new Paint();

    public CustomMaterialProgressDrawable(View parent) {
        super(parent);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public void setProgressRotation(float rotation) {
//    取负号是为了和微信保持一致，下拉时逆时针转加载时顺时针转，旋转因子是为了调整转的速度。
        this.rotation = -rotation * 200f;
        invalidateSelf();
    }


    @Override
    public void draw(Canvas c) {
        Rect bound = getBounds();
        c.rotate(rotation, bound.exactCenterX(), bound.exactCenterY());
        Rect src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        c.drawBitmap(mBitmap, src, bound, mPaint);
    }

}
