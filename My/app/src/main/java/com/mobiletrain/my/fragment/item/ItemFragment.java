package com.mobiletrain.my.fragment.item;

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

import com.mobiletrain.my.R;
import com.mobiletrain.my.adapter.MFAdapter;
import com.mobiletrain.my.model.NewChannelDataBean;
import com.mobiletrain.my.util.DataUtil;
import com.mobiletrain.my.util.ThreadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class ItemFragment extends Fragment {

    @BindView(R.id.lv)
    ListView lv;
    private View view;
    private static final int NEWS_CHANNEL_DATA = 30;
    private MFAdapter mfAdapter;
    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEWS_CHANNEL_DATA:
                    List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data = (List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) msg.obj;
                    if (data != null) {
                        mfAdapter = new MFAdapter(getContext(), data);
                        lv.setAdapter(mfAdapter);
                        mfAdapter.notifyDataSetChanged();
                        mfAdapter.notifyDataSetInvalidated();
                 //       mfAdapter.setOnImageListen(ItemFragment.this);
                    }

            }

        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_fragment, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
        }
        initData();


        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 90, sticky = false)
    public void userNameEvent(String userName) {

        if (mfAdapter!=null){
            mfAdapter.setUserName(userName);
            Log.e("test", "userNameEventsadasd: "+userName );
        }

    }

    private void initData() {
        Bundle bundle = getArguments();
        final String name = bundle.getString("name");
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<NewChannelDataBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlistBeen = DataUtil.newsChannelData(name);
                Message message = handler.obtainMessage();
                message.obj = contentlistBeen;
                message.what = NEWS_CHANNEL_DATA;
                handler.sendMessage(message);
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
