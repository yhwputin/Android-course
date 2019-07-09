package com.example.douyin;

import com.example.douyin.bean.Feed;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    public static ArrayList<Bean> getDatas(List<Feed> mFeeds) {
        ArrayList<Bean> videoList = new ArrayList<>();
        int i;
        for(i = 0;i < 30;i ++){
            videoList.add(new Bean(mFeeds.get((int)(Math.random()*mFeeds.size())).getVideo_url()));
        }
        return videoList;
    }
}
