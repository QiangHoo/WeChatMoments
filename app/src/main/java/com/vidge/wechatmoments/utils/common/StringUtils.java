package com.vidge.wechatmoments.utils.common;

/**
 * ClassName StringUtils
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
 * Platform 24
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class StringUtils {
    public static String getLogInfo(String functionName,String work,String json,String errorInfo){
        return functionName + work + ":" +
                json +
                " | " +
                "error:" +
                errorInfo;
    }
}
