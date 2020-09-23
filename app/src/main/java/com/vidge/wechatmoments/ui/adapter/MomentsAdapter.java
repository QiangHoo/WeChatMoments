package com.vidge.wechatmoments.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidge.imageloaderlib.imageloader.utils.ImageResizer;
import com.vidge.wechatmoments.R;
import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.utils.DpPxUtils;

import java.util.List;

/**
 * ClassName MomentsAdapter
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

public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.MViewHolder> {
    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_LIST = 2;

    private Context mContext;
    private List<MomentBeans> mData;

    public MomentsAdapter(Context context, List<MomentBeans> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = null;
        if (viewType == ITEM_TYPE_HEADER) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_header_view, parent, false);
        } else {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, parent, false);
        }
        return new MViewHolder(contentView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        if (holder.mItemType == ITEM_TYPE_HEADER){
            Bitmap bitmap = ImageResizer.getRoundBitmapByShader(
                    BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_user_default_head),
                    DpPxUtils.dip2px(mContext,80)
                    ,DpPxUtils.dip2px(mContext,80),
                    DpPxUtils.dip2px(mContext,6),0);
            holder.ivUserHead.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 10 : mData.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        public int mItemType;

        // type Header
        ImageView ivUserLogo;
        ImageView ivUserHead;
        TextView tvUserNickName;
        // type List

        public MViewHolder(@NonNull View itemView, int itemType) {
            super(itemView);
            mItemType = itemType;
            switch (itemType){
                case ITEM_TYPE_HEADER:
                    ivUserHead = itemView.findViewById(R.id.iv_user_head);
                    ivUserLogo = itemView.findViewById(R.id.iv_user_logo);
                    tvUserNickName = itemView.findViewById(R.id.tv_user_nick_name);
                    break;
                case ITEM_TYPE_LIST:

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_LIST;
        }

    }
}
