package com.vidge.wechatmoments.beans;

/**
 * ClassName UserInfoBean
 * <p>
 * Description TODO
 * <p>
 * create by vidge
 * <p>
 * on 2020/9/23
 * <p>
 * Version 1.0
 * <p>
 * ProjectName WeChatMoments
 * <p>
 * Platform 08
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

/*
    {
		"username": "jport",
		"nick": "Joe Portman",
		"avatar": "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w"
	}
*/

public class UserInfoBean {
    private String username;
    private String nick;
    private String avatar;
    private String profileImage;

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getNick() {
        return nick;
    }

    public String getAvatar() {
        return avatar;
    }
}
