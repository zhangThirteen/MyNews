package com.mobiletrain.my.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiletrain.my.R;
import com.mobiletrain.my.adapter.KeepAdapter;
import com.mobiletrain.my.adapter.MFAdapter;
import com.mobiletrain.my.bean.UserKeep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/22 0022.
 */
public class TopicFragment extends Fragment {
    @BindView(R.id.tv_go_login)
    TextView tvGoLogin;
    @BindView(R.id.lvKeep)
    ListView lvKeep;
    private View view;
    private KeepAdapter keepAdapter;
    private MyNewReceive myNewReceive;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.topic_fragment, container, false);
            ButterKnife.bind(this, view);
//           EventBus.getDefault().register(this);
        }

        if (MyFragment.loginCurrent&&MyFragment.un!=null){
            bmodQueryInit();

        }else {
            tvGoLogin.setVisibility(View.VISIBLE);
            lvKeep.setVisibility(View.GONE);
        }

        myNewReceive = new MyNewReceive();
        intentFilter = new IntentFilter();
        intentFilter.addAction(MFAdapter.ACTION_KEEP_NEWS);
        getActivity().registerReceiver(myNewReceive,intentFilter);

        return view;
    }

    private void bmodQueryInit() {
        BmobQuery<UserKeep> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userName", MyFragment.un);
        bmobQuery.setLimit(100);

        Log.e("test", "bmodQueryInit: "+123456);
        bmobQuery.findObjects(getActivity(), new FindListener<UserKeep>() {
            @Override
            public void onSuccess(List<UserKeep> list) {
                if (list.size()==0){
                    tvGoLogin.setVisibility(View.VISIBLE);
                    tvGoLogin.setText("暂无收藏！");
                    lvKeep.setVisibility(View.GONE);
                }else {
                    tvGoLogin.setVisibility(View.GONE);
                    lvKeep.setVisibility(View.VISIBLE);
                     keepAdapter = new KeepAdapter(getActivity(), list);
                    lvKeep.setAdapter(keepAdapter);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN,priority = 99,sticky = true)
//    public void reflashKeep(boolean b){
//        if (b){
//            bmodQueryInit();
//            keepAdapter.notifyDataSetChanged();
//        }
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//       EventBus.getDefault().unregister(this);
    }

    class MyNewReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(MFAdapter.ACTION_KEEP_NEWS)) {
                try {

                    bmodQueryInit();
                    keepAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
