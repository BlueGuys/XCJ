package com.hongyan.xcj.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.modules.main.MainTabActivity;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        MyHeaderView myHeaderView=findViewById(R.id.myheaderview);
        myHeaderView.initData(initData(),new MyHeaderView.OnPageClickListener(){


            @Override
            public void setOnPage(int position) {
                Toast.makeText(TestActivity.this,"position---"+position,100).show();
            }
        });



    }


    private ArrayList<MyHeaderView.Entity> initData(){
        ArrayList<MyHeaderView.Entity> list=new ArrayList<MyHeaderView.Entity>();
        MyHeaderView.Entity Entity1=new MyHeaderView.Entity();
        Entity1.setImageurl("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
        Entity1.setTitle("AAA");
        list.add(Entity1);
        MyHeaderView.Entity Entity2=new MyHeaderView.Entity();
        Entity2.setImageurl("http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg");
        Entity2.setTitle("BBB");
        list.add(Entity2);
        return list;
    }



}
