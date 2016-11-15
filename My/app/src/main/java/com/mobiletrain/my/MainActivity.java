package com.mobiletrain.my;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mobiletrain.my.fragment.MainFragment;
import com.mobiletrain.my.fragment.MyFragment;
import com.mobiletrain.my.fragment.TopicFragment;
import com.mobiletrain.my.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.mainFL)
    FrameLayout mainFL;
    @BindView(R.id.rbHomePage)
    RadioButton rbHomePage;
    @BindView(R.id.rbVideo)
    RadioButton rbVideo;
    @BindView(R.id.rbTopic)
    RadioButton rbTopic;
    @BindView(R.id.rbMy)
    RadioButton rbMy;
    @BindView(R.id.rg)
    RadioGroup rg;
    private MainFragment mainFragment;
    private MyFragment myFragment;
    private TopicFragment topicFragment;
    private VideoFragment videoFragment;
    public List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Bmob.initialize(this, "1411f38d1768416097d5e5c782540e8d");

        mainFragment = new MainFragment();
        myFragment = new MyFragment();
        topicFragment = new TopicFragment();
        videoFragment = new VideoFragment();
        fragments.add(mainFragment);
        fragments.add(myFragment);
        fragments.add(topicFragment);
        fragments.add(videoFragment);
        addFragment(mainFragment);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbHomePage:
                        addFragment(mainFragment);
                        break;
                    case R.id.rbVideo:
                        addFragment(videoFragment);
                        break;
                    case R.id.rbTopic:
                        addFragment(topicFragment);
                        break;
                    case R.id.rbMy:
                        addFragment(myFragment);
                        break;
                }
            }
        });


    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.mainFL, fragment);
        }
        for (Fragment f : fragments) {
            if (fragment == f) {
                ft.show(f);
            } else {
                ft.hide(f);
            }
        }

//        ft.addToBackStack(null);
        ft.commit();
    }


}
