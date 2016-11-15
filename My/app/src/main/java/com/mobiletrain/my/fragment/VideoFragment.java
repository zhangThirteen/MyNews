package com.mobiletrain.my.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mobiletrain.my.R;
import com.mobiletrain.my.adapter.VideoAdapter;
import com.mobiletrain.my.model.NewsVideoBean;
import com.mobiletrain.my.util.HttpUtil;
import com.mobiletrain.my.util.JsonUtil;
import com.mobiletrain.my.util.ThreadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class VideoFragment extends Fragment {
    @BindView(R.id.lvVideo)
    ListView lvVideo;
    private View view;
    private VideoAdapter videoAdapter;

    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            String json = (String) msg.obj;
            if (json!=null){
                try {
                    NewsVideoBean newsVideoBean = JsonUtil.parseNewVideoData(json);
                    List<NewsVideoBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = newsVideoBean.getShowapi_res_body().getPagebean().getContentlist();
                    videoAdapter = new VideoAdapter(contentlist,getActivity(),VideoFragment.this);
                    lvVideo.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.video_fragment, container, false);
            ButterKnife.bind(this, view);
        }

        initData();
        return view;
    }

    private void initData() {

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.newsVideo();
                Message message = handler.obtainMessage();
                message.obj = json;
                handler.sendMessage(message);
            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            try {
                if (videoAdapter.mediaPlayer.isPlaying()){
                    videoAdapter.mediaPlayer.pause();
                    Log.e("test", "onHiddenChanged: "+111);
                    fragmentStopListen.fragmentStop(22);
                    videoAdapter.notifyDataSetChanged();

                }else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    FragmentStopListen fragmentStopListen;

    public void setFragmentStopListen(FragmentStopListen fragmentStopListen) {
        this.fragmentStopListen = fragmentStopListen;
    }

   public interface FragmentStopListen{
        void fragmentStop(int i);
    }
}
