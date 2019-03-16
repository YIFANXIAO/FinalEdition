package com.tomatobell.Screen;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.tomatobell.Function.message;

import java.text.DecimalFormat;

public class BaseActivity extends Activity implements message {

    protected static int concentrateTime=0;

    protected static int restTime=0;

    public void setTime(int concentrateTime,int restTime){
        this.concentrateTime=concentrateTime;
        this.restTime=restTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //Toast.makeText(BaseActivity.this,"加入队列",Toast.LENGTH_SHORT).show();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        //Toast.makeText(BaseActivity.this,"移出队列",Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public Message getMessage(int countDown) {
        String hh = new DecimalFormat("00").format(countDown / 3600);
        String mm = new DecimalFormat("00").format(countDown % 3600 / 60);
        String ss = new DecimalFormat("00").format(countDown % 60);
        String timeFormat = new String(hh + ":" + mm + ":" + ss);
        Message msg = new Message();
        msg.obj = timeFormat;
        return msg;
    }

    @Override
    public int toInt(String input) {
        int sum=0;
        char[] c=input.toCharArray();
        for (char ch:c) {
            int chr=0;
            switch (ch){
                case '1':
                    chr=1;
                    break;
                case '2':
                    chr=2;
                    break;
                case '3':
                    chr=3;
                    break;
                case '4':
                    chr=4;
                    break;
                case '5':
                    chr=5;
                    break;
                case '6':
                    chr=6;
                    break;
                case '7':
                    chr=7;
                    break;
                case '8':
                    chr=8;
                    break;
                case '9':
                    chr=9;
                    break;
                case '0':
                    chr=0;
                    break;
            }
            sum=sum*10+chr;
        }
        return sum;
    }
}
