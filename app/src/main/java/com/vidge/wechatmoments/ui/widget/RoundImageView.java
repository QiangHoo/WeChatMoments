package com.vidge.wechatmoments.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.vidge.wechatmoments.R;

/**
 * ClassName RoundImageView
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 2020/9/24
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

public class RoundImageView extends AppCompatImageView {

    private static final int DEFAULT_RADIUS = 10;
    private int mRadius = DEFAULT_RADIUS;
    private Path mPath;
    private RectF mRec;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_radius,10);
        init();
        typedArray.recycle();
    }

    private void init() {
        mPath = new Path();
        mRec = new RectF(0, 0, getWidth(), getHeight());
    }


    public void setRadius(int radius) {
        this.mRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRec.set(0,0,getWidth(),getHeight());
        mPath.addRoundRect(mRec, mRadius, mRadius, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }


}
