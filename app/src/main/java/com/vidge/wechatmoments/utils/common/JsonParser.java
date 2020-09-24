package com.vidge.wechatmoments.utils.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName JsonParser
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 2020/9/22
 * <p>
 * Version 1.0
 * <p>
 * ProjectName WeChatMoments
 * <p>
 * Platform 07
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class JsonParser {
    private static final String TAG = JsonParser.class.getSimpleName();
    private static final Gson G_SON_INSTANCE = new Gson();

    public static <T> T JsonToObject(Class<T> cls, String json) {
        T obj;
        try {
            obj = G_SON_INSTANCE.fromJson(json, cls);
        }catch (JsonSyntaxException e){
            Logger.getLogger(TAG).log(Level.SEVERE,
                    StringUtils.getLogInfo("JsonToObject() ", "Parse json ",
                            json,e.toString()));
            return null;
        }
        return obj;
    }
}
