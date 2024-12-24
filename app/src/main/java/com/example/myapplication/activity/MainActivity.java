package com.example.myapplication.activity;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragment.AccountFragment;
import com.example.myapplication.fragment.DepositFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private HomeFragment mHomeFragment;
    private DepositFragment mDepositFragment;
    private AccountFragment mAccountFragment;
    private MineFragment mMineFragment;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        mBottomNavigationView=findViewById(R.id.bottomNavigationView);
        //点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    selectedFragment(0);
                }else if(item.getItemId()==R.id.deposit){
                    selectedFragment(1);
                }else if(item.getItemId()==R.id.account){
                    selectedFragment(2);
                }else{
                    selectedFragment(3);
                }
                return true;
            }
        });
        //默认进首页
        selectedFragment(0);
    }
    private  void selectedFragment(int position){

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(position==0){
            if(mHomeFragment==null){
                mHomeFragment=new HomeFragment();
                fragmentTransaction.add(R.id.content,mHomeFragment);
            }else{
                fragmentTransaction.show(mHomeFragment);
            }
        }else if(position==1){
            if(mDepositFragment==null){
                mDepositFragment=new DepositFragment();
                fragmentTransaction.add(R.id.content,mDepositFragment);
            }else{
                fragmentTransaction.show(mDepositFragment);
            }
        }else if(position==2){
            if(mAccountFragment==null){
                mAccountFragment=new AccountFragment();
                fragmentTransaction.add(R.id.content,mAccountFragment);
            }else{
                fragmentTransaction.show(mAccountFragment);
            }
        }else{
            if(mMineFragment==null){
                mMineFragment=new MineFragment();
                fragmentTransaction.add(R.id.content,mMineFragment);
            }else{
                fragmentTransaction.show(mMineFragment);
            }
        }

        //提交
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(mHomeFragment!=null){
            fragmentTransaction.hide(mHomeFragment);
        }

        if(mDepositFragment!=null){
            fragmentTransaction.hide(mDepositFragment);
        }

        if(mAccountFragment!=null){
            fragmentTransaction.hide(mAccountFragment);
        }

        if(mMineFragment!=null){
            fragmentTransaction.hide(mMineFragment);
        }
    }
}