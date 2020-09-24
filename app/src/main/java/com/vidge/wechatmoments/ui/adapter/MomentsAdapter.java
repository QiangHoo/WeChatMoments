package com.vidge.wechatmoments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;
import com.vidge.imageloaderlib.imageloader.impl.ImageLoaderImp;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageLoader;
import com.vidge.wechatmoments.R;
import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.ui.widget.MomentsImageLayout;
import com.vidge.wechatmoments.ui.widget.RoundImageView;

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
    private static final int ITEM_TYPE_LOADING = 3;

    public static final int LOADING = 0;
    public static final int LOAD_FINISH = 1;
    public static final int LOAD_END =2;

    private int loadState = LOAD_FINISH;

    private Context mContext;
    private List<MomentBeans> mData;

    private ImageLoader mImageLoader;

    public MomentsAdapter(Context context, List<MomentBeans> data) {
        mContext = context;
        mData = data;
        mImageLoader = ImageLoaderImp.getInstance(mContext);
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = null;
        if (viewType == ITEM_TYPE_HEADER) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_header_view, parent, false);
        } else if (viewType == ITEM_TYPE_LIST){
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, parent, false);
        }else {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_footer_view,parent,false);
        }
        return new MViewHolder(contentView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        if (holder.mItemType == ITEM_TYPE_HEADER){

        }else if (holder.mItemType == ITEM_TYPE_LIST){
            MomentBeans data = mData.get(position - 1);
            if (null != data.getSender().getAvatar()){
                ImageLoaderParams params = new ImageLoaderParams
                        .ImageLoaderParamsBuilder(data.getSender().getAvatar()
                        ,holder.ivItemUserHead)
                        .setPlaceholderResId(R.mipmap.ic_user_default_head)
                        .build();
                mImageLoader.loadImage(params);
            }
            holder.tvItemContent.setText(data.getContent());
            String nickName = data.getSender().getNick();
            if (null != nickName){
                holder.tvItemUserNickName.setText(nickName);
            }
            holder.mImagesLayout.setUrlList(data.getImages());
            holder.llCommentsContainer.removeAllViews();
            if (null != data.getComments() && data.getComments().size() != 0){
                holder.vDividerLine.setVisibility(View.VISIBLE);
                for (MomentBeans.Comments comments : data.getComments()){
                    View commentsView = LayoutInflater.from(mContext).inflate(R.layout.item_comments_list,null);
                    TextView tvNickName = commentsView.findViewById(R.id.tv_comment_user_nick);
                    tvNickName.setText(comments.getSender().getNick());
                    TextView tvContent = commentsView.findViewById(R.id.tv_comment_content);
                    tvContent.setText(comments.getContent());
                    holder.llCommentsContainer.addView(commentsView);
                }
            }else {
                holder.vDividerLine.setVisibility(View.GONE);
            }

        }else {
            if(loadState == LOADING){
                holder.rlFooterContainer.setVisibility(View.VISIBLE);
                holder.tvLoading.setText(R.string.string_loading);
            }else if(loadState == LOAD_FINISH){
                holder.rlFooterContainer.setVisibility(View.GONE);
            }else if(loadState == LOAD_END){
                holder.rlFooterContainer.setVisibility(View.VISIBLE);
                holder.tvLoading.setText(R.string.string_all_data);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 2 : mData.size() + 2;
    }

    public class MViewHolder extends RecyclerView.ViewHolder {
        public int mItemType;

        // type Header
        ImageView ivUserLogo;
        RoundImageView ivUserHead;
        TextView tvUserNickName;
        // type List
        RoundImageView ivItemUserHead;
        TextView tvItemUserNickName;
        TextView tvItemContent;
        LinearLayout llCommentsContainer;
        View vDividerLine;
        MomentsImageLayout mImagesLayout;
        //type footer
        RelativeLayout rlFooterContainer;
        TextView tvLoading;

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
                    ivItemUserHead = itemView.findViewById(R.id.iv_item_user_head);
                    tvItemUserNickName = itemView.findViewById(R.id.tv_item_user_nick_name);
                    tvItemContent = itemView.findViewById(R.id.tv_item_moments_content);
                    llCommentsContainer = itemView.findViewById(R.id.ll_comments_container);
                    vDividerLine = itemView.findViewById(R.id.view_divider_line);
                    mImagesLayout = itemView.findViewById(R.id.mil_images);
                    break;
                case ITEM_TYPE_LOADING:
                    tvLoading = itemView.findViewById(R.id.tv_loading);
                    rlFooterContainer = itemView.findViewById(R.id.rl_footer_container);
                    break;
                default:
                    break;
            }
        }
    }
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else if (position == mData.size() + 1){
            return ITEM_TYPE_LOADING;
        }else {
            return ITEM_TYPE_LIST;
        }

    }
}
