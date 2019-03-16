package com.plan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.xy.plan.R;

import java.util.ArrayList;
import java.util.List;


public class set extends AppCompatActivity implements View.OnClickListener{
    private Button btnClose;
    private EditText editText;
    private TextView mTvSelectedDate1,mTvSelectedDate2;
    private CustomDatePicker mDatePicker1,mDatePicker2;
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private ProgressBar progesss;
    private TextView progesssValue;
    private LinearLayout full;

    private int x0,x1, x2, dx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

        progesss = (ProgressBar) findViewById(R.id.progesss1);
        progesssValue = (TextView) findViewById(R.id.progesss_value1);
        full = (LinearLayout) findViewById(R.id.full);
        initview();

        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        ImageButton titleBack = (ImageButton) findViewById(R.id.title_back);
        titleBack.setOnClickListener(this);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        editText = (EditText) findViewById(R.id.InputPlanType);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    showListPopulWindow();
                }
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (editText.getWidth() - editText
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.pull_up1), null);
                        showListPopulWindow();
                        return true;
                    }
                }
                return false;
            }
        });

        /*----------------------------------------*/
        findViewById(R.id.begin_date).setOnClickListener(this);
        mTvSelectedDate1 = findViewById(R.id.tv_selected1_date);
        initDatePicker_begin();
        findViewById(R.id.end_date).setOnClickListener(this);
        mTvSelectedDate2 = findViewById(R.id.tv_selected2_date);
        initDatePicker_end();

        /*----------------------------------------*/
        spinner = (Spinner) findViewById(R.id.spinner);
        //数据
        data_list = new ArrayList<String>();
        data_list.add("T1");
        data_list.add("T2");
        data_list.add("T3");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
    }
    @Override
    public void onClick(View whichbtn) {
        // TODO Auto-generated method stub
        switch (whichbtn.getId()) {
            case R.id.begin_date:
                mDatePicker1.show(mTvSelectedDate1.getText().toString());
                break;
            case R.id.end_date:
                mDatePicker2.show(mTvSelectedDate2.getText().toString());
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker1.onDestroy();
        mDatePicker2.onDestroy();
    }
    private void initDatePicker_begin() {
        long beginTimestamp = DateFormatUtils.str2Long("2019-01-01");
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate1.setText(DateFormatUtils.long2Str(endTimestamp));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate1.setText(DateFormatUtils.long2Str(timestamp));
            }
        }, beginTimestamp, endTimestamp);
        // 允许点击屏幕或物理返回键关闭
        mDatePicker1.setCancelable(true);
        // 允许循环滚动
        mDatePicker1.setScrollLoop(true);
        // 允许滚动动画
        mDatePicker1.setCanShowAnim(true);
    }
    private void initDatePicker_end() {
        long beginTimestamp = DateFormatUtils.str2Long("2019-01-01");
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate2.setText(DateFormatUtils.long2Str(endTimestamp));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate2.setText(DateFormatUtils.long2Str(timestamp));
            }
        }, beginTimestamp, endTimestamp);
        // 允许点击屏幕或物理返回键关闭
        mDatePicker2.setCancelable(true);
        // 允许循环滚动
        mDatePicker2.setScrollLoop(true);
        // 允许滚动动画
        mDatePicker2.setCanShowAnim(true);
    }
    private void showListPopulWindow() {
        final String[] list = {"crab0314", "lsmhfz", "daoyuan3"};//要填充的数据
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(set.this);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(editText);//以哪个控件为基准，在该处以logId为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(list[i]);//把选择的选项内容展示在EditText上
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.pull_down1), null);
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
    }
    private void initview() {

        //外面的父view设置触摸监听事件
        full.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int w = getWindowManager().getDefaultDisplay().getWidth();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        progesss.setProgress(100 * x1 / w);
                        setPos();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = (int) event.getRawX();
                        dx = x2 - x1;
                        if (Math.abs(dx) > w / 100) { //改变条件 调整进度改变速度
                            x1 = x2; // 去掉已经用掉的距离， 去掉这句 运行看看会出现效果
                            progesss.setProgress(progesss.getProgress() + dx * 100 / w);
                            setPos();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });


    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPos();
        }
    }
    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int w = getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) progesssValue.getLayoutParams();
        int pro = progesss.getProgress();
        int tW = progesssValue.getWidth();
        if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = (int) (w - tW * 1.1);
        } else if (w * pro / 100 < tW * 0.7) {
            params.leftMargin = 0;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
        }
        progesssValue.setLayoutParams(params);
        progesssValue.setText(new StringBuffer().append(progesss.getProgress()).append("%"));
    }
}
