package com.vidge.wechatmoments.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.vidge.wechatmoments.R;

/**
 * ClassName BaseActivity
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 11/23/20
 * <p>
 * Version 1.0
 * <p>
 * ProjectName WeChatMoments
 * <p>
 * Platform 28
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public abstract class BaseActivity extends AppCompatActivity {
    private View loadingView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!isSupportActionBar()){
            if (getSupportActionBar() != null){
                getSupportActionBar().hide();
            }
        }
        setContentView(initLayout());
        initLoadingView();
        initView();
        initData();
    }

    private void initLoadingView() {
        if (loadingView == null){
            loadingView = LayoutInflater.from(this).inflate(R.layout.layout,null,false);
            loadingView.setClickable(true);
            loadingView.setAlpha(0);
        }
    }
    abstract void initData();
    abstract void initView();
    abstract boolean isSupportActionBar();
    abstract int initLayout();
    protected void showLoadingDialog(){
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        if (loadingView != null && loadingView.getParent() != null){
            rootView.removeView(loadingView);
        }
        rootView.addView(loadingView);
        if (loadingView != null){
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(loadingView,"alpha",0f,1f);
            objAnimator.setDuration(300);
            objAnimator.setInterpolator(new LinearInterpolator());
            objAnimator.start();
        }
    }
    protected void dismissLoadingDialog(){
        if (loadingView != null){
            View decorView = getWindow().getDecorView();
            final ViewGroup rootView = decorView.findViewById(android.R.id.content);
            ObjectAnimator objAnimator = ObjectAnimator.ofFloat(loadingView,"alpha",1f,0f);
            objAnimator.setDuration(300);
            objAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.removeView(loadingView);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    rootView.removeView(loadingView);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            objAnimator.setInterpolator(new LinearInterpolator());
            objAnimator.start();

        }
    }
}
