package com.mobiletrain.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiletrain.my.R;
import com.mobiletrain.my.WebActivity;
import com.mobiletrain.my.bean.UserKeep;
import com.mobiletrain.my.fragment.MyFragment;
import com.mobiletrain.my.model.NewChannelDataBean;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class MFAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data;
    private Bitmap imageBitmap;

    private String userName;
    public static String ACTION_KEEP_NEWS = "keepnews";

    public void setUserName(String userName) {
        this.userName = userName;
        Log.e("test", "setUserName:" + this.userName);
    }

    public MFAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_have_picture, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((Holder) convertView.getTag());
        }
        final NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = data.get(position);
        if (!contentlistBean.isHavePic()) {
            holder.ll.setVisibility(View.GONE);
        } else {
            NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageurlsBean imageurlsBean = contentlistBean.getImageurls().get(0);
            String url = imageurlsBean.getUrl();
            holder.ivNews.setImageURI(Uri.parse(url));
            holder.ll.setVisibility(View.VISIBLE);

        }
        holder.tvTitle.setText(contentlistBean.getTitle());
        holder.tvSource.setText(contentlistBean.getSource());
        holder.tvTime.setText(contentlistBean.getPubDate());

        holder.tvKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "onClick: " + userName + "//" + MyFragment.loginCurrent);
                if (MyFragment.loginCurrent && MyFragment.un != null) {
                    userName = MyFragment.un;
                    UserKeep userKeep = new UserKeep();
                    userKeep.setUserName(userName);
                    userKeep.setPubDate(contentlistBean.getPubDate());
                    userKeep.setHavePic(contentlistBean.isHavePic());
                    userKeep.setTitle(contentlistBean.getTitle());
                    userKeep.setSource(contentlistBean.getSource());
                    if (contentlistBean.isHavePic()) {
                        userKeep.setImageurls(contentlistBean.getImageurls().get(0).getUrl());
                    }
                    userKeep.setLink(contentlistBean.getLink());

                    userKeep.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show();
//                            EventBus.getDefault().postSticky(true);
                            Intent intent1 = new Intent();
                            intent1.setAction(ACTION_KEEP_NEWS);
                            context.sendBroadcast(intent1);

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(context, "收藏失败！", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(context, "您还未登录，请登录后收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.llWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webUrl = contentlistBean.getLink();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", webUrl);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class Holder {

        private final TextView tvTitle;
        private final TextView tvSource;
        private final TextView tvTime;
        private final ImageView ivNews;
        private final View ll;
        private final TextView tvKeep;
        private final LinearLayout llWeb;
        private LinearLayout llHeader;

        public Holder(View view) {
            tvTitle = ((TextView) view.findViewById(R.id.tvTitle));
            tvSource = (TextView) view.findViewById(R.id.tvSource);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            ivNews = (ImageView) view.findViewById(R.id.ivNews);
            llHeader = ((LinearLayout) view.findViewById(R.id.llHeader));
            tvKeep = ((TextView) view.findViewById(R.id.tvKeep));
            llWeb = ((LinearLayout) view.findViewById(R.id.ll_web));
            ll = view.findViewById(R.id.ll);
        }
    }


    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return bm;
    }

    public void setOnKeepListen(OnKeepListen onKeepListen) {
        this.onKeepListen = onKeepListen;
    }

    OnKeepListen onKeepListen;

    public interface OnKeepListen {
        void getNotifyChange();
    }

}
