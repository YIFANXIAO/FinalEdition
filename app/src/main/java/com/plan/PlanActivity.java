package com.plan;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xy.plan.R;

import java.util.ArrayList;



public class PlanActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private CustomViewPager myviewpager;
    //fragment的集合，对应每个子页面
    private ArrayList<Fragment> fragments;
    //选项卡中的按钮
    private Button btn_first;
    private Button btn_second;
    private Button btn_third;
    //作为指示标签的按钮
    private ImageView cursor;
    //标志指示标签的横坐标
    float cursorX = 0;
    //所有按钮的宽度的集合
    private int[] widthArgs;
    //所有按钮的集合
    private Button[] btnArgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        initView();
        ActionBar actionbar = getSupportActionBar();
        ImageButton titleEdit = (ImageButton) findViewById(R.id.title_edit);
        ImageButton titleBack = (ImageButton) findViewById(R.id.title_back);
        titleEdit.setOnClickListener(this);
        titleBack.setOnClickListener(this);
        if(actionbar != null){
            actionbar.hide();
        }
    }
    public void initView(){
        myviewpager = (CustomViewPager)this.findViewById(R.id.myviewpager);
        btn_first = (Button)this.findViewById(R.id.btn_first);
        btn_second = (Button)this.findViewById(R.id.btn_second);
        btn_third = (Button)this.findViewById(R.id.btn_third);
        btnArgs = new Button[]{btn_first,btn_second,btn_third};
        cursor = (ImageView)this.findViewById(R.id.cursor_btn);
        cursor.setBackgroundColor(Color.RED);
        myviewpager.setOnPageChangeListener(this);
        btn_first.setOnClickListener(this);
        btn_second.setOnClickListener(this);
        btn_third.setOnClickListener(this);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThridFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        myviewpager.setAdapter(adapter);
        resetButtonColor();
        btn_first.setTextColor(Color.RED);
    }
    //重置所有按钮的颜色
    public void resetButtonColor(){
        btn_first.setBackgroundColor(Color.parseColor("#DCDCDC"));
        btn_second.setBackgroundColor(Color.parseColor("#DCDCDC"));
        btn_third.setBackgroundColor(Color.parseColor("#DCDCDC"));
        btn_first.setTextColor(Color.BLACK);
        btn_second.setTextColor(Color.BLACK);
        btn_third.setTextColor(Color.BLACK);
    }
    @Override
    public void onClick(View whichbtn) {
        // TODO Auto-generated method stub
        switch (whichbtn.getId()) {
            case R.id.title_edit:
                Intent intent = new Intent(PlanActivity.this,set.class);
                startActivity(intent);
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_first:
                myviewpager.setCurrentItem(0);
                break;
            case R.id.btn_second:
                myviewpager.setCurrentItem(1);
                break;
            case R.id.btn_third:
                myviewpager.setCurrentItem(2);
                break;
        }
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        if(widthArgs==null){
            widthArgs = new int[]{btn_first.getWidth(),
                    btn_second.getWidth(),
                    btn_third.getWidth()};
        }
        //每次滑动首先重置所有按钮的颜色
        resetButtonColor();
        //将滑动到的当前按钮颜色设置为红色
        btnArgs[arg0].setTextColor(Color.RED);
        cursorAnim(arg0);
    }
    //指示器的跳转，传入当前所处的页面的下标
    public void cursorAnim(int curItem){
        //每次调用，就将指示器的横坐标设置为0，即开始的位置
        cursorX = 0;
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
        //减去边距*2，以对齐标题栏文字
        lp.width = widthArgs[curItem]-btnArgs[0].getPaddingLeft()*2;
        cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for(int i=0; i<curItem; i++){
            cursorX = cursorX + btnArgs[i].getWidth();
        }
        //再加上当前页面的左边距，即为指示器当前应处的位置
        cursor.setX(cursorX+btnArgs[curItem].getPaddingLeft());
    }

}
