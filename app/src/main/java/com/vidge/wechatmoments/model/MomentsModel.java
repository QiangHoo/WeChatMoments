package com.vidge.wechatmoments.model;

import com.vidge.wechatmoments.callbacks.CallBacks;

/**
 * ClassName MomentsModel
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
 * Platform 15
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface MomentsModel {
    void init(CallBacks.UserInfoCallBack userInfoCallBack,CallBacks.MomentsListCallBack momentsListCallBack);

    void getUserInfo(String url);

    void getMomentsList(String url);

    void onDestroy();
}
