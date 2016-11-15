package com.mobiletrain.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiletrain.my.bean.UserData;
import com.mobiletrain.my.util.BmobSMSUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordAgain)
    EditText etPasswordAgain;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.btnGet)
    Button btnGet;
    @BindView(R.id.etMsg)
    EditText etMsg;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    private String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnRegister)
    public void onBtnRegister() {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();
        String msg = etMsg.getText().toString();

        if (username.trim().length() == 0 || password.trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (password.trim().length() < 6) {
            Toast.makeText(RegisterActivity.this, "密码长度不能低于6", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passwordAgain)) {
            Toast.makeText(RegisterActivity.this, "两次输入的密码不同,请重新输入", Toast.LENGTH_SHORT).show();
        } else if (username.trim().length() < 2) {
            Toast.makeText(RegisterActivity.this, "用户名长度不能低于2", Toast.LENGTH_SHORT).show();
        } else if (msg.trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            BmobSMS.verifySmsCode(this, number, msg, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {

                        final UserData userData = new UserData();
                        userData.setUserName(username);
                        userData.setPassword(password);
                        userData.setPhoneNumber(number);

                        userData.save(RegisterActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterActivity.this, "注册成功!返回登录界面", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(RegisterActivity.this, "注册失败,请稍后再试!---" + s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

    }

    @OnClick(R.id.btnGet)
    public void onBtnGetClick() {
        number = etPhoneNumber.getText().toString();
        if (number.length() == 0) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (number.length() != 11) {
            Toast.makeText(RegisterActivity.this, "请输入11位有效号码", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobSMSUtil.sendSMS(RegisterActivity.this, number, btnGet);
    }
}
