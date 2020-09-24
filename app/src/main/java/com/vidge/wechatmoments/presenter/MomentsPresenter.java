package com.vidge.wechatmoments.presenter;

import com.vidge.wechatmoments.views.MomentsView;

/**
 * ClassName MomentsPresenter
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

public interface MomentsPresenter {
    void init(MomentsView view);

    void getUserInfo(String url);

    void getMomentsList(String url);

    void onDestroy();
}
