package com.vidge.wechatmoments.beans;

import java.util.List;

/**
 * ClassName MommentBeans
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
 * Platform 09
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
/*
{
	"content": "沙发！",
	"images": [{
			"url": "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg"
		},
		{
			"url": "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7"
		},
		{
			"url": "http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg"
		}
	],
	"sender": {
		"username": "jport",
		"nick": "Joe Portman",
		"avatar": "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w"
	},
	"comments": [{
			"content": "Good.",
			"sender": {
				"username": "outman",
				"nick": "Super hero",
				"avatar": "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w"
			}
		},
		{
			"content": "Like it too",
			"sender": {
				"username": "inman",
				"nick": "Doggy Over",
				"avatar": "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w"
			}
		}
	]
}
 */

public class MomentBeans {
    private String content;
    private List<Image> images;
    private UserInfoBean sender;
    private List<Comments> comments;

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setSender(UserInfoBean sender) {
        this.sender = sender;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public List<Image> getImages() {
        return images;
    }

    public UserInfoBean getSender() {
        return sender;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public static class Image{
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
    public static class Comments{
        private String content;
        private UserInfoBean sender;

        public void setContent(String content) {
            this.content = content;
        }

        public void setSender(UserInfoBean sender) {
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public UserInfoBean getSender() {
            return sender;
        }
    }
}
