package com.example.douyin.bean;

import java.util.List;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:17
 */
public class FeedResponse {

    private List<Feed> feeds;
    private boolean success;

    public List<Feed> getFeeds(){
        return feeds;
    }
    public boolean getSuccess(){
        return success;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    // TODO-C2 (2) Implement your FeedResponse Bean here according to the response json
}
