package com.vidge.wechatmoments.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;
import com.vidge.imageloaderlib.imageloader.impl.ImageLoaderImp;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageLoader;
import com.vidge.wechatmoments.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private ImageLoader mLoader;
    private ImageView mIv;
    protected String[] needPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = findViewById(R.id.iv);
        checkPermission();
        doNetWork();
    }
    public void doNetWork(){

    }

    public void checkPermission() {
        PermissionRequest request = new PermissionRequest.Builder(this, PERMISSION_REQUEST_CODE, needPermissions).build();
        if (EasyPermissions.hasPermissions(this, needPermissions)) {
            loadImage();
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
                loadImage();
            }
        }
    }

    public void loadImage() {
        mLoader = ImageLoaderImp.getInstance(this.getApplicationContext());
        ImageLoaderParams params = new ImageLoaderParams
                .ImageLoaderParamsBuilder("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600839767003&di=4bbe0ee86bef3b80cdce261becfff0ec&imgtype=0&src=http%3A%2F%2Fimg02.tooopen.com%2Fimages%2F20160409%2Ftooopen_sy_158828214345.jpg", mIv)
                .build();
        mLoader.loadImage(params);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}