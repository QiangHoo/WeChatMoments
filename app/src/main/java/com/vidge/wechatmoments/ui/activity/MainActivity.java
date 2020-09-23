package com.vidge.wechatmoments.ui.activity;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.vidge.imageloaderlib.imageloader.utils.ImageResizer;
import com.vidge.wechatmoments.R;
import com.vidge.wechatmoments.ui.adapter.MomentsAdapter;
import com.vidge.wechatmoments.utils.DpPxUtils;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 1000;
    protected String[] needPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private RecyclerView mRecyclerView;
    private View mStatusBar;

    private MomentsAdapter mMomentsAdapter;

    private int mBannerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar  = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        initViews();
        initImmersionBar();
        checkPermission();
    }

    private void initViews() {
        mBannerHeight = DpPxUtils.dip2px(this, 400)
                - ImmersionBar.getStatusBarHeight(this);
        mStatusBar = findViewById(R.id.status_bar_view);
        mStatusBar.setVisibility(View.GONE);
        mRecyclerView = findViewById(R.id.rv_moments);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                } else {

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (totalDy < 0) {
                    totalDy = 0;
                }
                if (totalDy < mBannerHeight) {
                    float alpha = (float) totalDy / mBannerHeight;
                } else {

                }

            }
        });
        mMomentsAdapter = new MomentsAdapter(this,null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMomentsAdapter);
    }

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        ImmersionBar.with(MainActivity.this).statusBarColor(R.color.colorAccent,0).init();
        //ImmersionBar.with(MainActivity.this).transparentStatusBar().init();
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
        }else {
            ImmersionBar.with(MainActivity.this).statusBarDarkFont(true).init();
        }

    }

    public void checkPermission() {
        PermissionRequest request = new PermissionRequest.Builder(this, PERMISSION_REQUEST_CODE, needPermissions).build();
        if (EasyPermissions.hasPermissions(this, needPermissions)) {

        } else {
            EasyPermissions.requestPermissions(request);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (perms.contains(needPermissions[0])) {

            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ImmersionBar.with(this).init();
    }
}