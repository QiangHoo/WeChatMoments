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
    private ObjectAnimator objAnimatorOut;
    private ObjectAnimator objAnimatorIn;
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
        removeLoadingView();
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        rootView.addView(loadingView);
        if (loadingView != null){
            if (objAnimatorIn == null){
                objAnimatorIn = ObjectAnimator.ofFloat(loadingView,"alpha",0f,1f);
                objAnimatorIn.setDuration(300);
                objAnimatorIn.setInterpolator(new LinearInterpolator());
            }
            objAnimatorIn.start();
        }
    }
    protected void dismissLoadingDialog(){
        if (loadingView != null){
            if (objAnimatorOut == null){
                objAnimatorOut = ObjectAnimator.ofFloat(loadingView,"alpha",1f,0f);
                objAnimatorOut.setDuration(300);
                objAnimatorOut.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (loadingView != null && loadingView.getParent() != null) {
                            removeLoadingView();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        removeLoadingView();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objAnimatorOut.setInterpolator(new LinearInterpolator());
            }
            objAnimatorOut.start();
        }
    }
    private void removeLoadingView(){
        View decorView = getWindow().getDecorView();
        final ViewGroup rootView = decorView.findViewById(android.R.id.content);
        if (loadingView != null && loadingView.getParent() != null) {
            rootView.removeView(loadingView);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objAnimatorOut!= null){
            objAnimatorOut.cancel();
            objAnimatorOut.reverse();
            objAnimatorOut = null;
        }
        if (objAnimatorIn != null){
            objAnimatorIn.cancel();
            objAnimatorIn.reverse();
            objAnimatorIn = null;
        }
        shouldDestroy();
    }
    protected void shouldDestroy(){}
}
