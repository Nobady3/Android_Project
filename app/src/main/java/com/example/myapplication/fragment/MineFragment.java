package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.RegisterActivity;
import com.example.myapplication.db.UserDbHelper;
import com.example.myapplication.entity.UserInfo;

public class MineFragment extends Fragment {
    private View rootView;
    private TextView tv_username;
    private TextView tv_nickname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_mine, container, false);

        //初始化控件
        tv_username=rootView.findViewById(R.id.tv_username);
        tv_nickname=rootView.findViewById(R.id.tv_nickname);

        //退出登录
        rootView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取消

                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //退出登录
                                getActivity().finish();
                                //打开登录页面
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        //注销账号
        rootView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage("确定要注销账号吗?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //取消

                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int row = UserDbHelper.getInstance(getActivity() ).logout(UserInfo.sUserInfo.getUsername());
                                //退出登录
                                getActivity().finish();
                                //打开登录页面
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });

        //修改密码
        rootView.findViewById(R.id.updatePwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UpdatePwdActivity.class);
                startActivityForResult(intent,1000);
            }
        });
        rootView.findViewById(R.id.updateInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UpdateInfoActivity.class);
                startActivityForResult(intent,2000);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置用户数据
        UserInfo userInfo=UserInfo.getUserInfo();
        if(null!=userInfo){
            tv_username.setText("用户名:"+userInfo.getUsername());
            tv_nickname.setText("用户ID:" + String.format("%06d", userInfo.getUser_id()));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
            getActivity().finish();
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else if(resultCode==2000){
            UserInfo userInfo=UserInfo.getUserInfo();
            tv_username.setText("用户名:"+userInfo.getUsername());
        }
    }
}