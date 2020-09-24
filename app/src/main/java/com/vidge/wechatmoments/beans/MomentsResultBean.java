package com.vidge.wechatmoments.beans;

import java.util.List;

/**
 * ClassName MomentsResultBean
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
 * Platform 34
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class MomentsResultBean {
    private List<MomentBeans> result;

    public void setResult(List<MomentBeans> result) {
        this.result = result;
    }

    public List<MomentBeans> getResult() {
        return result;
    }
}
