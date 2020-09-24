package com.vidge.wechatmoments.presenter;

import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.beans.UserInfoBean;
import com.vidge.wechatmoments.callbacks.CallBacks;
import com.vidge.wechatmoments.model.MomentsModel;
import com.vidge.wechatmoments.model.MomentsModelImpl;
import com.vidge.wechatmoments.views.MomentsView;

import java.util.List;

/**
 * ClassName MomentsPresenterImpl
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
 * Platform 16
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class MomentsPresenterImpl implements MomentsPresenter, CallBacks.MomentsListCallBack, CallBacks.UserInfoCallBack {
    private MomentsModel mMomentsModel;
    private MomentsView mMomentsView;

    @Override
    public void init(MomentsView view) {
        mMomentsView = view;
        mMomentsModel = new MomentsModelImpl(this,this);
    }

    @Override
    public void getUserInfo(String url) {
        if (null != mMomentsModel){
            mMomentsModel.getUserInfo(url);
        }
    }

    @Override
    public void getMomentsList(String url) {
        if (null != mMomentsModel){
            mMomentsModel.getMomentsList(url);
        }
    }

    @Override
    public void onDestroy() {
        if (null != mMomentsModel){
            mMomentsModel.onDestroy();
            mMomentsModel = null;
        }
        if (null != mMomentsView){
            mMomentsView = null;
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean userInfo) {
        if (null != mMomentsView){
            mMomentsView.onShowUserInfo(userInfo);
        }
    }

    @Override
    public void getUserInfoFailed(String errorInfo) {
        if (null != mMomentsView){
            mMomentsView.onGetUserInfoError(errorInfo);
        }
    }

    @Override
    public void getMomentsListSuccess(List<MomentBeans> moments) {
        if (null != mMomentsView){
            mMomentsView.onShowMomentsList(moments);
        }
    }

    @Override
    public void getMomentsListFailed(String errorInfo) {
        if (null != mMomentsView){
            mMomentsView.onGetMomentListError(errorInfo);
        }
    }
}
