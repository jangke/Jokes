package com.jike.jiangke.jokes.model;

/**
 * 描述笑话的类
 */
public class JokePost {
    private String post_date;
    private String post_content;
    private String post_title;

    public JokePost(String post_date, String post_content, String post_title) {
        this.post_date = post_date;
        this.post_content = post_content;
        this.post_title = post_title;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }
}
