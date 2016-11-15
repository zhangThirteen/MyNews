package com.mobiletrain.my.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/13.
 */
public class UserKeep extends BmobObject {

    /**
     * pubDate : 2016-10-23 20:30:50
     * havePic : true
     * title : 太励志！丁俊晖对手曾踢足球 因病才转投斯诺克
     * channelName : 体育焦点
     * imageurls : [{"height":277,"width":220,"url":"http://k.sinaimg.cn/n/sports/transform/20161023/VTcd-fxxaeqk5501899.jpg/w570186.jpg"}]
     * desc : 　　北京时间10月23日消息，2016年斯诺克国锦赛的大幕刚刚拉开，中国选手丁俊晖首轮对手是世界排名仅为115位的英国球手米切尔-曼。不过这个看起来名不见经传的球员背后，却有着一段颇为励志和感人的“转型”故事。　　米切尔-曼幼年时期曾经和每一个热爱足球的英国少年一样，选择....
     * source : 新浪
     * channelId : 5572a108b3cdc86cf39001d4
     * link : http://sports.sina.com.cn/others/snooker/2016-10-23/doc-ifxwztrt0205821.shtml
     */
    private String userName;
    private String pubDate;
    private boolean havePic;
    private String title;
    String imageurls;
    private String source;
    private String link;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurls() {
        return imageurls;
    }

    public void setImageurls(String imageurls) {
        this.imageurls = imageurls;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
