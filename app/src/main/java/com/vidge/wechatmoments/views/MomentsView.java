package com.vidge.wechatmoments.views;

import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.beans.UserInfoBean;

import java.util.List;

/**
 * ClassName MomentsView
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
 * Platform 17
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface MomentsView {
    void onShowUserInfo(UserInfoBean userInfo);

    void onGetUserInfoError(String errorInfo);

    void onShowMomentsList(List<MomentBeans> moments);

    void onGetMomentListError(String error);
}
