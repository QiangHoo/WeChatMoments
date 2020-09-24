package com.vidge.wechatmoments.callbacks;

import com.vidge.wechatmoments.beans.MomentBeans;
import com.vidge.wechatmoments.beans.UserInfoBean;

import java.util.List;

/**
 * ClassName UserInfoCallBack
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
 * Platform 21
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public final class CallBacks {
    public interface UserInfoCallBack{
        void getUserInfoSuccess(UserInfoBean userInfo);
        void getUserInfoFailed(String errorInfo);
    }
    public interface MomentsListCallBack{
        void getMomentsListSuccess(List<MomentBeans> moments);
        void getMomentsListFailed(String errorInfo);
    }
}
