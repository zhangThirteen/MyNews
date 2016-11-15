package com.mobiletrain.my.util;

import com.mobiletrain.my.model.NewChannelDataBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class DataUtil {

    public static List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>  newsChannelData(String name){
        String json = HttpUtil.newsByName(name);
        NewChannelDataBean newChannelDataBean = JsonUtil.parseNewChannelData(json);
        List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = newChannelDataBean.getShowapi_res_body().getPagebean().getContentlist();
        return contentlist;
    }
}
