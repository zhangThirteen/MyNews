package com.mobiletrain.my.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobiletrain.my.AddChannelActivity;
import com.mobiletrain.my.R;
import com.mobiletrain.my.fragment.item.ItemFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class MainFragment extends Fragment{

    @BindView(R.id.tl)
    TabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.mainFL_add_channel)
    ImageView mainFLAddChannel;
    private View view;
    List<Map<String, String>> list = new ArrayList<>();

    List<String> channelList = new ArrayList<>();

   public static int channelNum = 12;

    private String TAG = "test";
    public static List<String> strings;
    private static List<String> stringsOther;
    private MyNewReceive myNewReceive;
    private IntentFilter intentFilter;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.main_fragment, container, false);
            ButterKnife.bind(this, view);
        }

    //    initData();
        initData();

        tl.setupWithViewPager(vp);

        mainFLAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddChannelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("mine", (ArrayList<String>) strings);
                bundle.putStringArrayList("more", (ArrayList<String>) stringsOther);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        myNewReceive = new MyNewReceive();
        intentFilter = new IntentFilter();
        intentFilter.addAction(AddChannelActivity.ACTION_CHANNEL_MINE);
        getActivity().registerReceiver(myNewReceive,intentFilter);

        return view;
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.news_channel);

        for (int i = 0; i < stringArray.length; i++) {
            channelList.add(stringArray[i]);
        }
//        Map<Integer,String> channelMap =

        if (strings==null) {
            strings = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                strings.add(channelList.get(i));
            }
        }
        if (stringsOther==null) {
            stringsOther = new ArrayList<>();
            for (int i = 0; i < (stringArray.length-12); i++) {
                stringsOther.add(channelList.get(12+i));
            }
        }

        initNewsChannel(strings);
    }


    private void initNewsChannel(final List<String> ss) {
        final List<Fragment> fragments = new ArrayList<>();
        if (ss != null) {
            for (int i = 0; i <strings.size(); i++) {
                ItemFragment itemFragment = new ItemFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", ss.get(i));
                itemFragment.setArguments(bundle);
                fragments.add(itemFragment);

            }


            vp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

                @Override
                public Fragment getItem(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getCount() {
                    return fragments.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return ss.get(position);
                }

            });
        }
    }




    class MyNewReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(AddChannelActivity.ACTION_CHANNEL_MINE)) {
                try {
                    Bundle bundle = intent.getExtras();
                    ArrayList<String> mine = bundle.getStringArrayList("mine");
                    ArrayList<String> more = bundle.getStringArrayList("more");
                    strings = mine;
                    stringsOther = more;
                    initNewsChannel(strings);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //    private void initData() {
//
//        ThreadUtil.execute(new Runnable() {
//            @Override
//            public void run() {
//                String json = HttpUtil.newsChannel();
//                Message message = handler.obtainMessage();
//                message.obj = json;
//                Log.e(TAG, "run: " + json);
//                handler.sendMessage(message);
//            }
//        });


//    }
}
