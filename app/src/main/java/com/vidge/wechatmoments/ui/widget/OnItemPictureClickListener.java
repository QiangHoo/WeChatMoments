package com.vidge.wechatmoments.ui.widget;

import android.widget.ImageView;

import com.vidge.wechatmoments.beans.MomentBeans;

import java.util.List;

public interface OnItemPictureClickListener {
    void onItemPictureClick(int itemPostion, int i, String url, List<MomentBeans.Image> urlList, ImageView imageView);
}
