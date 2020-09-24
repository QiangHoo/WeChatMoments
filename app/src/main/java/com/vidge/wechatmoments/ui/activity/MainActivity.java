package com.vidge.wechatmoments.ui.activity;

import android.Manifest;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.vidge.wechatmoments.R;
import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.beans.UserInfoBean;
import com.vidge.wechatmoments.presenter.MomentsPresenter;
import com.vidge.wechatmoments.presenter.MomentsPresenterImpl;
import com.vidge.wechatmoments.ui.adapter.MomentsAdapter;
import com.vidge.wechatmoments.ui.widget.RecyclerScrollListener;
import com.vidge.wechatmoments.ui.widget.SwipeRefreshLayout;
import com.vidge.wechatmoments.utils.DpPxUtils;
import com.vidge.wechatmoments.utils.Urls;
import com.vidge.wechatmoments.views.MomentsView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.vidge.wechatmoments.ui.adapter.MomentsAdapter.LOADING;
import static com.vidge.wechatmoments.ui.adapter.MomentsAdapter.LOAD_END;
import static com.vidge.wechatmoments.ui.adapter.MomentsAdapter.LOAD_FINISH;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, MomentsView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ONE_PAGE_COUNT = 5;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final float ALPHA_TRANSLUCENT = 0.6f;
    protected String[] needPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ImageView mIvCamera, mIvBack;
    private TextView mTvTitle;
    private SwipeRefreshLayout mRefreshLayout;


    private MomentsAdapter mMomentsAdapter;

    private int mBannerHeight;

    private int currentPage = 0;
    private int totalDy = 0;

    private List<MomentBeans> mMomentList = new ArrayList<>();
    private List<MomentBeans> mCurrentMomentList = new ArrayList<>();

    private MomentsPresenter mMomentsPresenter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initViews();
        initImmersionBar();
        init();
        checkPermission();
    }

    private void init() {
        mMomentsPresenter = new MomentsPresenterImpl();
        mMomentsPresenter.init(this);
    }

    private Runnable mLoadMoreRunnable = new Runnable() {
        @Override
        public void run() {
            mMomentsAdapter.setLoadState(LOAD_FINISH);
            loadMore();
        }
    };

    private void initViews() {
        mBannerHeight = DpPxUtils.dip2px(this, 360)
                - ImmersionBar.getStatusBarHeight(this);
        mRecyclerView = findViewById(R.id.rv_moments);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));

        mRefreshLayout = findViewById(R.id.srl);

        mIvBack = findViewById(R.id.iv_back);
        mIvCamera = findViewById(R.id.iv_camera);
        mTvTitle = findViewById(R.id.tv_moments_title);

        mRecyclerView.addOnScrollListener(new RecyclerScrollListener() {


            @Override
            public void onLoadMore() {
                mMomentsAdapter.setLoadState(LOADING);
                mHandler.removeCallbacks(mLoadMoreRunnable);
                mHandler.postDelayed(mLoadMoreRunnable, 2000);
            }

            @Override
            public void onScrolled(int dx, int dy) {
                totalDy += dy;
                if (totalDy < 0) {
                    totalDy = 0;
                }
                showToolbarOrNot();
                Log.e("TAG", "高度:" + totalDy + " mBannerHeight:" + mBannerHeight);
            }
        });
        mMomentsAdapter = new MomentsAdapter(this, mCurrentMomentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMomentsAdapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                totalDy = 0;
                showToolbarOrNot();
                getDataByNet();
            }
        });
    }

    private void showToolbarOrNot(){
        if (totalDy < mBannerHeight) {
            float alpha = (float) totalDy / mBannerHeight;
            if (alpha <= ALPHA_TRANSLUCENT) {
                mToolbar.setAlpha(1);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorTransport));
                mIvBack.setImageResource(R.mipmap.ic_back);
                mIvCamera.setImageResource(R.mipmap.ic_camera);
                mTvTitle.setVisibility(View.GONE);
            } else {
                mToolbar.setAlpha(alpha);
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIvBack.setImageResource(R.mipmap.ic_back_dark);
                mIvCamera.setImageResource(R.mipmap.ic_camera_dark);
                mTvTitle.setVisibility(View.VISIBLE);
            }
        } else {
            mToolbar.setAlpha(1);
        }
    }

    private void loadMore() {
        if (currentPage * ONE_PAGE_COUNT >= mMomentList.size()) {
            mMomentsAdapter.setLoadState(LOAD_END);
            return;
        }
        int offset = currentPage * ONE_PAGE_COUNT;
        Log.e("TAG", "当前：" + offset);
        addData(offset, mMomentList);
    }

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.toolbar).init();
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
        } else {
            ImmersionBar.with(MainActivity.this).statusBarDarkFont(true).init();
        }

    }

    public void checkPermission() {
        PermissionRequest request = new PermissionRequest.Builder(this, PERMISSION_REQUEST_CODE, needPermissions).build();
        if (EasyPermissions.hasPermissions(this, needPermissions)) {
            getDataByNet();
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
                getDataByNet();
            }
        }
    }

    private void getDataByNet() {
        mMomentsPresenter.getUserInfo(Urls.GET_USER_INFO_URL);
        mMomentsPresenter.getMomentsList(Urls.GET_MOMENTS_LIST_URL);
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

    @Override
    public void onShowUserInfo(UserInfoBean userInfo) {

    }

    @Override
    public void onGetUserInfoError(String errorInfo) {
    }

    @Override
    public void onShowMomentsList(List<MomentBeans> moments) {
        currentPage = 0;
        mMomentList.clear();
        mCurrentMomentList.clear();
        for (int i = 0; i < moments.size(); i++) {
            MomentBeans bean = moments.get(i);
            ArrayList<MomentBeans.Image> images = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                MomentBeans.Image image = new MomentBeans.Image();
                image.setUrl("http://farm1.staticflickr.com/134/325376313_4ed1988001.jpg");
                images.add(image);
            }
            bean.setImages(images);
            if (null != bean.getSender()) {
                mMomentList.add(bean);
            }
        }

        addData(0, mMomentList);
    }

    private void addData(int offset, List<MomentBeans> list) {
        if (null == list) {
            return;
        }
        if (offset >= list.size()) {
            return;
        }
        int count = Math.min((list.size() - offset), ONE_PAGE_COUNT);
        for (int i = offset; i < offset + count; i++) {
            mCurrentMomentList.add(list.get(i));
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMomentsAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
        currentPage++;
    }

    @Override
    public void onGetMomentListError(String error) {
    }
}