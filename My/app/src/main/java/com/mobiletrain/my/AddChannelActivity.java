package com.mobiletrain.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobiletrain.my.adapter.AddChannelAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class AddChannelActivity extends AppCompatActivity {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.news_channel_mine_rv)
    RecyclerView newsChannelMineRv;
    @BindView(R.id.news_channel_more_rv)
    RecyclerView newsChannelMoreRv;
    @BindView(R.id.add_channel_back)
    LinearLayout addChannelBack;
    private AddChannelAdapter addChannelAdapter;
    private AddChannelAdapter moreChannelAdapter;
    private ArrayList<String> mine;
    private ArrayList<String> more;

    private boolean isChange = false;

    public static final String ACTION_CHANNEL_MINE = "newChannelChange";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mine = bundle.getStringArrayList("mine");
        more = bundle.getStringArrayList("more");

        mineChannelInit();

        moreChannelInit();

        listenChannelChange();

    }

    @OnClick(R.id.add_channel_back)
    public void onClick(View v) {
        finish();
    }

    private void listenChannelChange() {
        addChannelAdapter.setOnItemClickListener(new AddChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = mine.get(position);
                mine.remove(position);
                more.add(0, s);
                addChannelAdapter.notifyDataSetChanged();
                moreChannelAdapter.notifyDataSetChanged();
                isChange = true;
            }

        });

        moreChannelAdapter.setOnItemClickListener(new AddChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = more.get(position);
                mine.add(0, s);
                more.remove(position);
                addChannelAdapter.notifyDataSetChanged();
                moreChannelAdapter.notifyDataSetChanged();
                isChange = true;
            }
        });
    }

    private void moreChannelInit() {
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        moreChannelAdapter = new AddChannelAdapter(this, more);
        newsChannelMoreRv.setAdapter(moreChannelAdapter);
        newsChannelMoreRv.setLayoutManager(gridLayoutManager2);
    }

    private void mineChannelInit() {
        addChannelAdapter = new AddChannelAdapter(this, mine);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        newsChannelMineRv.setAdapter(addChannelAdapter);
        newsChannelMineRv.setLayoutManager(gridLayoutManager);
    }

    //    ChannelChange channelChange;
//
//    public void setChannelChange(ChannelChange channelChange) {
//        this.channelChange = channelChange;
//
//    }
//
//    @Override
    protected void onStop() {
        super.onStop();
        if (isChange) {
            Intent intent1 = new Intent();
            intent1.setAction(ACTION_CHANNEL_MINE);
            Bundle extras = new Bundle();
            extras.putStringArrayList("mine", mine);
            extras.putStringArrayList("more", more);
            intent1.putExtras(extras);
            Log.e("test", "onStop: " + mine.get(0));
            sendBroadcast(intent1);
        }
    }

//    public interface ChannelChange{
//        void changeNewChannel(ArrayList<String> mineList,ArrayList<String> moreList);
//    }
}
