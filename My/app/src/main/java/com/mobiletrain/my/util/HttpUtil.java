package com.mobiletrain.my.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class HttpUtil {


    public static String newsChannel() {
        Log.e("test", "newsChannel: " + 333);
        String json = getStringByOkHttp("https://route.showapi.com/109-34?showapi_appid=25268&showapi_timestamp=20161020130052&showapi_sign=1c6e63a096b9f8c9d005084e2a14b356");
        Log.e("test", "newsChannel: " + json);
        return json;
    }

    public static String newsInNow() {
        String json = getStringByOkHttp("https://route.showapi.com/109-35?channelId=&channelName=&maxResult=20&needAllList=0&needContent=0&needHtml=0&page=1&showapi_appid=25268&showapi_timestamp=20161020131226&title=&showapi_sign=ccfaa430efc6789d71d05c5a5e042226");
        return json;
    }

    public static String newsByName(String name) {
        String json = getStringByOkHttp(" https://route.showapi.com/109-35?channelId=&channelName=" + name + "&maxResult=20&needAllList=0&needContent=0&needHtml=0&page=1&showapi_appid=25268&showapi_timestamp=20161024005908&title=&showapi_sign=372f0be4997999aeba16aa96be8928fa");
        return json;

    }

    public static String newsVideo() {
        String json = getStringByOkHttp("https://route.showapi.com/255-1?page=&showapi_appid=25268&showapi_timestamp=20161025002617&title=&type=41&showapi_sign=7127d25d2377f7bbe641ad2630a7e5c1");
        return json;

    }


    public static String getStringByOkHttp(String url) {
        String json = "";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag("tag")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
            json = "IOException";
        }
        return json;
    }
}
