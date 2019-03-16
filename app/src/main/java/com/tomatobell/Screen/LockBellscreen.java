package com.tomatobell.Screen;

import android.content.Intent;
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


public class LockBellscreen extends BaseActivity implements finishBell,restartTimer,View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    private int firstCopyCountDown=0;

    private TextView timerView;

    private long baseTimer;

    private int countDown=0;

    private int pauseCountDown=0;

    private Timer workTimer;

    private TimerTask timerTask;
    //isquit表示是否是退出
    private boolean isquit=true;
    private boolean isPause=false;

    private Button lockPause;
    private Button lockFinish;
    private Button lockRestart;
    private Button beginMusic;
    private Button musicList;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_bell_activity_screen);

        //保存完整最开始的倒计时
        firstCopyCountDown=concentrateTime;

        //获取倒计时组件
        timerView = (TextView) this.findViewById(R.id.timeView);
        final Handler startTimehandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if (null != timerView) {
                    timerView.setText((String) msg.obj);
                }
            }
        };

        //获取当前界面时间
        LockBellscreen.this.baseTimer = SystemClock.elapsedRealtime();
        //首次启动计时器
        LockStartTimer(startTimehandler,concentrateTime);

        lockPause = (Button)findViewById(R.id.lock_pause);
        lockPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //做到轮换效果
                if (isPause==false){
                    isPause=true;
                }else {
                    isPause=false;
                }

                if (isPause==true) {
                    //保存当前时刻，并输出toast
                    pauseCountDown = countDown;
                    Toast.makeText( LockBellscreen.this, pauseCountDown + "", Toast.LENGTH_SHORT ).show();
                    finishBell();
                }else {
                    //获取当前界面时间
                    Toast.makeText( LockBellscreen.this, "恢复倒计时", Toast.LENGTH_SHORT ).show();
                    restartTimer(startTimehandler,pauseCountDown);
                }
            }
        });

        lockFinish = (Button)findViewById(R.id.lock_finish);
        lockFinish.setOnClickListener(this);

        lockRestart = (Button)findViewById(R.id.lock_restart);
        lockRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishBell();
                restartTimer(startTimehandler,concentrateTime);
            }
        });

        //播放音乐
        //选择要播放的音乐
        mediaPlayer = MediaPlayer.create(this, R.raw.test);
        //播放音乐的按钮
        beginMusic = (Button)findViewById(R.id.lock_beginMusic);
        beginMusic.setOnClickListener(this);

        musicList = (Button)findViewById(R.id.lock_musicList);
        musicList.setOnClickListener(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lock_finish:
                finishBell();
                ActivityCollector.finishAll();
                break;
            case R.id.lock_beginMusic:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }else {
                    mediaPlayer.pause();
                }
                break;
            case R.id.lock_musicList:
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
            Toast.makeText( LockBellscreen.this, "锁死退出", Toast.LENGTH_SHORT ).show();
            //关闭计时器
            workTimer.cancel();
            timerTask.cancel();
            ActivityCollector.finishAll();
        }

        //关闭音乐播放器
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    protected void LockStartTimer(final Handler startTimehandler,final int TimerTime){
        //计时器
        workTimer = new Timer();

        //计时器任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //正常获取倒计时
                //nowTime表示从当前开始计时多少秒
                int nowTime = (int) ((SystemClock.elapsedRealtime() - LockBellscreen.this.baseTimer) / 1000);
                //换算为倒计时
                countDown = TimerTime - nowTime;

                //每个时刻判断是显示倒计时还是跳转到休息钟
                if (countDown>=0){
                    startTimehandler.sendMessage(getMessage(countDown));
                }else{
                    //不是退出，是切换到休息界面
                    isquit=false;
                    //关闭计时器
                    finishBell();
                    ActivityCollector.finishAll();
                    //跳转到休息界面
                    Intent intent = new Intent( LockBellscreen.this, RestScreenActivity.class );
                    startActivity( intent );

                }
            }
        };

        //安排计时器
        workTimer.schedule(timerTask,0l,1000l);
    }

    @Override
    public void restartTimer(Handler startTimehandler, int TimerTime) {
        LockBellscreen.this.baseTimer = SystemClock.elapsedRealtime();
        LockStartTimer(startTimehandler,TimerTime);
    }

    @Override
    public void finishBell() {
        workTimer.cancel();
        timerTask.cancel();
    }
}
