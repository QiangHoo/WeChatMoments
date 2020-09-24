package com.vidge.wechatmoments.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vidge.imageloaderlib.imageloader.config.ImageLoaderParams;
import com.vidge.imageloaderlib.imageloader.impl.ImageLoaderImp;
import com.vidge.imageloaderlib.imageloader.interfaces.ImageLoader;
import com.vidge.wechatmoments.R;
import com.vidge.wechatmoments.beans.MomentBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class MomentsImageLayout extends ViewGroup {
    private static final float DEFAULT_SPACING = 15f;
    private float image_ratio = 1.7f;
    private int oneImageWidth;
    private int oneImageHeight;
    protected Context mContext;
    private float mSpacing = DEFAULT_SPACING;
    private int mColumns;
    private int mRows;
    private int mTotalWidth;
    private int mSingleWidth;

    private boolean mIsFirst = true;
    private List<MomentBeans.Image> mUrlList = new ArrayList<>();

    private ImageLoader mLoader;

    public MomentsImageLayout(Context context) {
        this(context, null);
    }

    public MomentsImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MomentsImageLayout);
        mSpacing = typedArray.getDimension(R.styleable.MomentsImageLayout_spacing, DEFAULT_SPACING);
        oneImageWidth = (int) typedArray.getDimension(R.styleable.MomentsImageLayout_oneImageWidth, 0);
        oneImageHeight = (int) typedArray.getDimension(R.styleable.MomentsImageLayout_oneImageHeight, 0);
        image_ratio =   typedArray.getFloat(R.styleable.MomentsImageLayout_image_ratio, image_ratio);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mLoader = ImageLoaderImp.getInstance(context);
        if (getListSize(mUrlList) == 0) {
            setVisibility(GONE);
        }else {
            setVisibility(VISIBLE);
        }
    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mTotalWidth = right - left;
        mSingleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
        if (mIsFirst) {
            notifyDataSetChanged();
            mIsFirst = false;
        }
    }


    public void setSpacing(float spacing) {
        mSpacing = spacing;
    }


    public void setUrlList(List<MomentBeans.Image> urlList) {
        if (getListSize(urlList) == 0) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);

        mUrlList.clear();
        mUrlList.addAll(urlList);
        if (!mIsFirst) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        post(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    private void refresh() {
        removeAllViews();
        int size = getListSize(mUrlList);
        if (size > 0) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
            return;
        }

        if (size == 1) {
            String url = mUrlList.get(0).getUrl();
            ImageView imageView = createImageView(0, url);

            getRealOneImageSize();
            imageView.layout(0, 0, oneImageWidth, oneImageHeight);
            LayoutParams params = getLayoutParams();
            params.height = oneImageHeight;
            setLayoutParams(params);
            addView(imageView);
            displayImage(imageView, url);
            return;
        }

        generateChildrenLayout(size);
        layoutParams();

        for (int i = 0; i < size; i++) {
            String url = mUrlList.get(i).getUrl();
            ImageView imageView = createImageView(i, url);
            layoutImageView(imageView, i, url);
        }
    }

    private void getRealOneImageSize() {
        if(oneImageWidth==0){
            oneImageWidth = mSingleWidth;
        }

        if(oneImageHeight==0){
            oneImageHeight = (int) (oneImageWidth * image_ratio);
        }
    }

    private void layoutParams() {
        int singleHeight = mSingleWidth;
        LayoutParams params = getLayoutParams();
        params.height = (int) (singleHeight * mRows + mSpacing * (mRows - 1));
        setLayoutParams(params);
    }

    private ImageView createImageView(final int i, final String url) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    private void layoutImageView(ImageView imageView, int i, String url) {
        final int singleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
        int singleHeight = singleWidth;

        int[] position = findPosition(i);
        int left = (int) ((singleWidth + mSpacing) * position[1]);
        int top = (int) ((singleHeight + mSpacing) * position[0]);
        int right = left + singleWidth;
        int bottom = top + singleHeight;

        imageView.layout(left, top, right, bottom);
        addView(imageView);
        displayImage(imageView, url);
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                if ((i * mColumns + j) == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }

    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3;
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mColumns = 3;
            mRows = 3;
        }

    }

    private int getListSize(List<MomentBeans.Image> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }



    private void displayImage(ImageView imageView, String url){
        ImageLoaderParams params = new ImageLoaderParams
                .ImageLoaderParamsBuilder(url, imageView)
                .setPlaceholderResId(R.mipmap.ic_default_user_logo).build();
        mLoader.loadImage(params);
    }

}
