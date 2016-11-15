package com.mobiletrain.my.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mobiletrain.my.model.NewChannelDataBean;
import com.mobiletrain.my.model.NewsChannelBean;
import com.mobiletrain.my.model.NewsVideoBean;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class JsonUtil {

    public static NewsChannelBean parseNewChannel(String json) {
        NewsChannelBean newsChannelBean = null;
        try {
            newsChannelBean = new Gson().fromJson(json, NewsChannelBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return newsChannelBean;

    }

    public static NewChannelDataBean parseNewChannelData(String json) {
        NewChannelDataBean newChannelDataBean = null;
        try {
            newChannelDataBean = new Gson().fromJson(json, NewChannelDataBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return newChannelDataBean;

    }

    public static NewsVideoBean parseNewVideoData(String json) {
        NewsVideoBean newsVideoBean = null;
        try {
             newsVideoBean = new Gson().fromJson(json, NewsVideoBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return newsVideoBean;

    }
}
