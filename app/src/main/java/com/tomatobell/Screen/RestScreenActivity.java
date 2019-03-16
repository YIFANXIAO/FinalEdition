package com.tomatobell.Screen;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xy.plan.R;
import com.tomatobell.Function.finishBell;
import com.tomatobell.Function.restartTimer;

import java.util.Timer;
import java.util.TimerTask;

public class RestScreenActivity extends BaseActivity implements finishBell,restartTimer,View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    private int firstCopyCountDown=0;

    private TextView restTimerView;

    private long baseTimer;

    private int restCountDown=0;

    private Timer restTimer;

    private TimerTask timerTask;

    private boolean isquit=true;

    private Button rest_finish;
    private Button rest_restart;
    private Button rest_beginmusic;
    private Button rest_musiclist;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_screen);

        //保存完整倒计时
        firstCopyCountDown=restTime;

        //获取倒计时组件
        restTimerView = (TextView) this.findViewById(R.id.restTimeView);
        final Handler startTimehandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if (null != restTimerView) {
                    restTimerView.setText((String) msg.obj);
                }
            }
        };

        //笔记，心得，点击倒计时记录心得
        restTimerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RestScreenActivity.this,"写点什么吧",Toast.LENGTH_SHORT).show();
            }
        });

        //获取当前界面时间
        RestScreenActivity.this.baseTimer = SystemClock.elapsedRealtime();
        //初次启动计时器
        ResartStartTimer(startTimehandler,restTime);

        rest_finish = (Button)findViewById(R.id.rest_finish);
        rest_restart = (Button)findViewById(R.id.rest_restart);
        rest_beginmusic = (Button)findViewById(R.id.rest_beginmusic);
        rest_musiclist = (Button)findViewById(R.id.rest_musiclist);

        rest_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishBell();
                restartTimer(startTimehandler,restTime);
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.test);

        rest_finish.setOnClickListener(this);
        rest_musiclist.setOnClickListener(this);
        rest_beginmusic.setOnClickListener(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rest_finish:
                finishBell();
                ActivityCollector.finishAll();
                break;
            case R.id.rest_beginmusic:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }else {
                    mediaPlayer.pause();
                }
                break;
            case R.id.rest_musiclist:
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.main, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(this);
                //显示
                popup.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.music1:
                mediaPlayer.reset();
                mediaPlayer =MediaPlayer.create(this, R.raw.test);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                break;
            case R.id.music2:
                mediaPlayer.reset();
                mediaPlayer =MediaPlayer.create(this, R.raw.test1);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                break;
            case R.id.music3:
                mediaPlayer.reset();
                mediaPlayer =MediaPlayer.create(this, R.raw.test2);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isquit==true) {
            Toast.makeText( RestScreenActivity.this, "退出休息", Toast.LENGTH_SHORT ).show();
            //关闭计时器
            restTimer.cancel();
            timerTask.cancel();
            ActivityCollector.finishAll();
        }
        //关闭音乐播放器
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    protected void ResartStartTimer(final Handler startTimehandler, final int TimerTime){
        //计时器
        restTimer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                //nowTime表示从当前开始计时多少秒
                int nowTime = (int)((SystemClock.elapsedRealtime() - RestScreenActivity.this.baseTimer) / 1000);
                //10是10秒
                restCountDown=TimerTime-nowTime;

                if (restCountDown>=0){
                    startTimehandler.sendMessage(getMessage(restCountDown));
                }else{
                    isquit=false;
                    finishBell();
                    ActivityCollector.finishAll();
                }
            }
        };

        restTimer.schedule(timerTask,0l,1000l);
    }

    @Override
    public void restartTimer(Handler startTimehandler, int TimerTime) {
        RestScreenActivity.this.baseTimer = SystemClock.elapsedRealtime();
        ResartStartTimer(startTimehandler,TimerTime);
    }

    @Override
    public void finishBell() {
        restTimer.cancel();
        timerTask.cancel();
    }
}
