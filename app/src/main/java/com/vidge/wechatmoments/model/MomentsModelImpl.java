package com.vidge.wechatmoments.model;

import com.google.gson.JsonParseException;
import com.vidge.imageloaderlib.network.HttpRequest;
import com.vidge.imageloaderlib.network.HttpRequestImpl;
import com.vidge.imageloaderlib.network.callback.OnNetRequestResultGot;
import com.vidge.wechatmoments.beans.MomentsResultBean;
import com.vidge.wechatmoments.beans.UserInfoBean;
import com.vidge.wechatmoments.callbacks.CallBacks;
import com.vidge.wechatmoments.utils.Urls;
import com.vidge.wechatmoments.utils.common.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName MomentsModelImpl
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

public class MomentsModelImpl implements MomentsModel {
    private static final String TAG = MomentsModelImpl.class.getSimpleName();
    private static final int USER_INFO_TYPE = 1;
    private static final int MOMENTS_LIST_TYPE = 2;
    private HttpRequest mRequest;
    private CallBacks.UserInfoCallBack mUserInfoCallback;
    private CallBacks.MomentsListCallBack mMomentsListCallback;

    public MomentsModelImpl(CallBacks.UserInfoCallBack userInfoCallBack,CallBacks.MomentsListCallBack momentsListCallBack) {
        init(userInfoCallBack,momentsListCallBack);
    }

    @Override
    public void init(CallBacks.UserInfoCallBack userInfoCallBack,CallBacks.MomentsListCallBack momentsListCallBack) {
        mUserInfoCallback = userInfoCallBack;
        mMomentsListCallback = momentsListCallBack;
        if (null != mRequest) {
            return;
        }
        mRequest = new HttpRequestImpl();
        mRequest.init();
    }


    private void netWork(final int type, String url) {
        mRequest.getRequest(url, new OnNetRequestResultGot() {
            @Override
            public void onSuccess(String response) {
                if (type == MOMENTS_LIST_TYPE) {
                    if (null == mMomentsListCallback) {
                        return;
                    }
                    String json = "{\"result\":" + response + "}";
                    Logger.getLogger(TAG).log(Level.SEVERE, json);
                    MomentsResultBean result = JsonParser.JsonToObject(MomentsResultBean.class, json);
                    if (null != result && null != result.getResult()) {
                        mMomentsListCallback.getMomentsListSuccess(result.getResult());
                    } else {
                        mMomentsListCallback.getMomentsListFailed("get moments list failed");
                    }
                } else {
                    if (null == mUserInfoCallback) {
                        return;
                    }
                    if (null != response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            UserInfoBean userInfo = new UserInfoBean();
                            String profileImage = jObj.getString("profile-image");
                            userInfo.setProfileImage(profileImage);
                            String avatar = jObj.getString("avatar");
                            userInfo.setAvatar(avatar);
                            String nick = jObj.getString("nick");
                            userInfo.setNick(nick);
                            String username = jObj.getString("username");
                            userInfo.setUsername(username);
                            mUserInfoCallback.getUserInfoSuccess(userInfo);
                        } catch (JsonParseException | JSONException e) {
                            e.printStackTrace();
                            mUserInfoCallback.getUserInfoFailed("parse user info json error");
                        }
                    } else {
                        mUserInfoCallback.getUserInfoFailed("get user info error");
                    }
                }
            }

            @Override
            public void onError(String error) {
                if (type == MOMENTS_LIST_TYPE) {
                    if (null != mMomentsListCallback) {
                        mMomentsListCallback.getMomentsListFailed(error);
                    }
                } else {
                    if (null != mUserInfoCallback) {
                        mUserInfoCallback.getUserInfoFailed(error);
                    }
                }
            }
        });
    }

    @Override
    public void getUserInfo(String url) {
        netWork(USER_INFO_TYPE, url);
    }

    @Override
    public void getMomentsList(String url) {
        netWork(MOMENTS_LIST_TYPE,url);
    }

    @Override
    public void onDestroy() {
        if (null != mUserInfoCallback){
            mUserInfoCallback = null;
        }
        if (null != mMomentsListCallback){
            mMomentsListCallback = null;
        }
        if (null != mRequest){
            mRequest = null;
        }
    }
}
