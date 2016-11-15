package com.mobiletrain.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiletrain.my.LoginActivity;
import com.mobiletrain.my.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class MyFragment extends Fragment {

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.login_ll)
    LinearLayout loginLl;
    @BindView(R.id.login_exit)
    TextView loginExit;
    private View view;
    public static String un = null;
    public static  boolean loginCurrent = false;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.my_fragment, container, false);
            ButterKnife.bind(this, view);

            EventBus.getDefault().register(this);
        }
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });


        loginExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLl.setVisibility(View.GONE);
                tvLogin.setVisibility(View.VISIBLE);
                loginCurrent = false;
                un = null;
            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    public void userNameEvent(String userName) {
        loginLl.setVisibility(View.VISIBLE);
        tvLogin.setVisibility(View.GONE);
        Log.e("test", "userNameEvent: "+userName );
        loginCurrent = true;
        un = userName;
        tvUser.setText(userName);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
