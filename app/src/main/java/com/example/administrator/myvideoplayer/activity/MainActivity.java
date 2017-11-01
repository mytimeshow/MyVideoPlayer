package com.example.administrator.myvideoplayer.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myvideoplayer.R;
import com.example.administrator.myvideoplayer.Uitl.ScreenRotateUtil;
import com.example.administrator.myvideoplayer.View.MyVideoView;

import java.io.File;

public class MainActivity extends BaceActivity implements View.OnClickListener {
    private ImageView lock_img;
    private MyVideoView mvideoView;
    private LinearLayout control_barlayout;
    private SeekBar play_seeBar;
    private LinearLayout left_layout;
    private ImageView pause1, voice_img;
    private TextView time_current_tv;
    private TextView time_total_tv;
    private SeekBar voice_seeBar;
    private ImageView screen_img;
    private static final int UPDATE_UL = 0;
    private RelativeLayout total_relative;
    private int screen_width;
    private int screen_height;
    private AudioManager maudioManager;
    private boolean isFull=false;
    private boolean isAdjust=false;
    private int threshold=20;
    private float mLight;
    float lastX=0,lastY=0;
    private ImageView operation_bg,voice_num;
    private FrameLayout frameLayout;
    private long firstTouch;
    private boolean HorizontalChange=false;
    long mLastTime=0;
    long mCurTime=0;
    private boolean unLocked=true;






    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UL) {
                int currentTime = mvideoView.getCurrentPosition();

                int totalTime = mvideoView.getDuration();
              //  Toast.makeText(MainActivity.this, String.valueOf(totalTime), Toast.LENGTH_SHORT).show();
                updateTextViewWithTimeFormat(time_current_tv, currentTime);
                updateTextViewWithTimeFormat(time_total_tv, totalTime);
                play_seeBar.setMax(totalTime);
                play_seeBar.setProgress(currentTime);
                handler.sendEmptyMessageDelayed(UPDATE_UL, 500);
            }else if(msg.what==2){
                if(mvideoView.isPlaying()){
                    pause1.setImageResource(R.drawable.pause6);
                    mvideoView.pause();
                }else{
                    pause1.setImageResource(R.drawable.play6);
                    mvideoView.start();
                }

            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        ScreenRotateUtil.getInstance(this).start(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lock_img= (ImageView) findViewById(R.id.lock_img);
        lock_img.setOnClickListener(this);
        maudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mvideoView = (MyVideoView) findViewById(R.id.videoPlayer);
        voice_img = (ImageView) findViewById(R.id.voice_img);
        control_barlayout = (LinearLayout) findViewById(R.id.control_barlayout);
        play_seeBar = (SeekBar) findViewById(R.id.seebar);
        left_layout = (LinearLayout) findViewById(R.id.left_layout);
        pause1 = (ImageView) findViewById(R.id.pause);
        total_relative = (RelativeLayout) findViewById(R.id.total_relative);
        time_current_tv = (TextView) findViewById(R.id.time_current_tv);
        time_total_tv = (TextView) findViewById(R.id.time_total_tv);
        voice_seeBar = (SeekBar) findViewById(R.id.voice_seeBar);
        screen_img = (ImageView) findViewById(R.id.screen_img);
        operation_bg= (ImageView) findViewById(R.id.operation_bg);
        voice_num= (ImageView) findViewById(R.id.voice_num);
        frameLayout= (FrameLayout) findViewById(R.id.framLayout);
        control_barlayout= (LinearLayout) findViewById(R.id.control_barlayout);

        screen_img.setOnClickListener(this);
        //获取当前设备最大音量值
        int voice_max = maudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //获取当前设备音量值
        int voice_current = maudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        voice_seeBar.setMax(voice_max);
        voice_seeBar.setProgress(voice_current);


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            mvideoView.getVideoFile("ww.mp4");
            File file = new File(Environment.getExternalStorageDirectory(), "ww.mp4");
           // mvideoView.setVideoPath(file.getPath());
            mvideoView.setVideoURI(Uri.parse("http://flv3.bn.netease.com/videolib3/1710/27/tzuHF3635/HD/tzuHF3635-mobile.mp4"));
           // mvideoView.setVideoURI(Uri.parse("http://sports.qq.com/kbsweb/game.htm?mid=100000:1470224&ptag=baidu.ald.sc.nba"));

//            MediaMetadataRetriever retr = new MediaMetadataRetriever();
//            retr.setDataSource("http://flv3.bn.netease.com/videolib3/1710/27/tzuHF3635/HD/tzuHF3635-mobile.mp4");
//            String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); // 视频高度
//            String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); // 视频宽度

           mvideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {

                   mp.start();
               }
           });
            //mvideoView.start();
            Toast.makeText(this, "granted2", Toast.LENGTH_SHORT).show();
//            Log.e("BBB", height+"  "+width);
            handler.sendEmptyMessage(UPDATE_UL);
        }

    }

    public void scaleFull() {
        ScreenRotateUtil.getInstance(this).toggleRotate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                File file = new File(Environment.getExternalStorageDirectory(), "mm.mp4");
               // mvideoView.setVideoPath(file.getPath());
                mvideoView.setVideoURI(Uri.parse("http://sports.qq.com/kbsweb/game.htm?mid=100000:1470224&ptag=baidu.ald.sc.nba"));
                mvideoView.start();
                Toast.makeText(this, file.getPath(), Toast.LENGTH_SHORT).show();
                Log.e("AAA", file.getPath());
                handler.sendEmptyMessage(UPDATE_UL);
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initData() {
        screen_width = getResources().getDisplayMetrics().widthPixels;
        screen_height = getResources().getDisplayMetrics().widthPixels;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mvideoView.setVideoURI(Uri.parse("http://v.qq.com/iframe/player.html?tiny=0&auto=1&vid=p0024x5a8sw"));
//                mvideoView.start();
//                handler.sendEmptyMessage(UPDATE_UL);
//            }
//        }).start();


    }

    private void setVideoViewScale(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mvideoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mvideoView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams1 = total_relative.getLayoutParams();
        layoutParams1.width = width;
        layoutParams1.height = height;
        total_relative.setLayoutParams(layoutParams1);


    }

    @Override
    protected void initListener() {

        pause1.setOnClickListener(this);
        play_seeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextViewWithTimeFormat(time_current_tv, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeMessages(UPDATE_UL);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mvideoView.seekTo(progress);
                handler.sendEmptyMessage(UPDATE_UL);

            }
        });

        voice_seeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mvideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x=event.getX();
                float y=event.getY();
                int time=2000;

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{

                        //单击事件
                        if(control_barlayout.getVisibility()==View.GONE ){
                            control_barlayout.setVisibility(View.VISIBLE);
                            if(lock_img.getVisibility()==View.GONE){
                                lock_img.setVisibility(View.VISIBLE);
                            }
                        }
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               lock_img.setVisibility(View.GONE);
                               control_barlayout.setVisibility(View.GONE);
                           }
                       }, time);



                        //双击暂停事件
                        mLastTime=mCurTime;
                        mCurTime= System.currentTimeMillis();
                        if(mCurTime-mLastTime<300){//双击事件
                            mCurTime =0;
                            mLastTime = 0;
                            handler.removeMessages(1);
                            handler.sendEmptyMessage(2);
                        }

                        Log.e("main", "onTouch " );
                       //Toast.makeText(MainActivity.this, "ddd", Toast.LENGTH_SHORT).show();
                        lastX=x;
                        lastY=y;

                        break;}
                    case MotionEvent.ACTION_MOVE:{

                        float moveX=x-lastX;
                        float moveY=y-lastY;
                        float absX=Math.abs(moveX);
                        float absY=Math.abs(moveY);
                      //  Log.e("main", String.valueOf(absX)+" "+String.valueOf(absY) );
                        if(absX>threshold && absY>threshold){
                            Log.e("main", "onTouch1 " );

                           // Toast.makeText(MainActivity.this, "13d", Toast.LENGTH_SHORT).show();
                            if(absX < absY){
                                Log.e("main", "onTouch2 " );
                              //  Toast.makeText(MainActivity.this, "d", Toast.LENGTH_SHORT).show();
                                isAdjust=true;
                                HorizontalChange=false;
                            }else{
                                Log.e("main", "onTouch3 " );
                            //    Toast.makeText(MainActivity.this, "a", Toast.LENGTH_SHORT).show();
                                isAdjust=false;
                                HorizontalChange=true;
                            }

                        }else if(absX<threshold && absY > threshold){
                            Log.e("main", "onTouch4 " );
                           // Toast.makeText(MainActivity.this, "23d", Toast.LENGTH_SHORT).show();
                            isAdjust=true;
                            HorizontalChange=false;
                        }else if(absX>threshold && absY< threshold){
                            Log.e("main", "onTouch5 " );
                           // Toast.makeText(MainActivity.this, "33d", Toast.LENGTH_SHORT).show();
                            isAdjust=false;
                            HorizontalChange=true;
                        }

                        if(isAdjust){

                            Log.e("main", "onTouch6 " );
                           // Toast.makeText(MainActivity.this, "43d", Toast.LENGTH_SHORT).show();
                            if(x<screen_width/2){
                                //调节亮度
                                if(moveY>0){

                                }else{

                                }
                                changeLight(-moveY);
                            }else {
                                //调节音量
                                if(moveY>0){

                                }else{

                                }
                                changeVoice(-moveY);

                            }
                        }

                        if(HorizontalChange){
                            Log.e("main2", "onTouch:2 " );

                            changePlayProgress(moveX);

                        }
                        lastX=x;
                        lastY=y;

                        break;}

                    case MotionEvent.ACTION_UP: {
                        HorizontalChange=false;
                        isAdjust=false;
                         lastX=0;
                        lastY=0;
                        frameLayout.setVisibility(View.GONE);
                        break;
                    }


                }

                return true;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("main2", "on " );
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("main2", "onTouchEvent: " );
                break;



        }
        return true;
    }

    //改变播放进度
    private void changePlayProgress(float absX){

        handler.removeMessages(UPDATE_UL);
        int currentProgress=mvideoView.getCurrentPosition();
        int seekTo= (int) (currentProgress+(absX/screen_width*5000));
        mvideoView.seekTo(seekTo);
        updateTextViewWithTimeFormat(time_current_tv,seekTo);
        handler.sendEmptyMessage(UPDATE_UL);




    }


    //改变音量
    private void changeVoice(float moveY){
        int voice_max = maudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //获取当前设备音量值
        int voice_current = maudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index= (int) (moveY/screen_height*voice_max*3);
        int voice=Math.max(index+voice_current,0);

        operation_bg.setImageResource(R.drawable.voice5);
        maudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,voice,0);
            if(frameLayout.getVisibility()==View.GONE){
                frameLayout.setVisibility(View.VISIBLE);
            }


        ViewGroup.LayoutParams layoutParams=voice_num.getLayoutParams();
        layoutParams.width= (int) (dip2px(this,100)*(float)voice/voice_max);
        voice_num.setLayoutParams(layoutParams);
        voice_seeBar.setProgress(voice);
    }
    //改变亮度
    private void changeLight(float moveY){
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        mLight=lp.screenBrightness;
        float index=moveY/screen_height;
        mLight+=index;
        if(mLight>1.0f){
            mLight=1.0f;
        }
        if(mLight<0.01f){
            mLight=0.01f;
        }
        lp.screenBrightness=mLight;
        operation_bg.setImageResource(R.drawable.light3);

        if(frameLayout.getVisibility()==View.GONE){
            frameLayout.setVisibility(View.VISIBLE);
        }


        ViewGroup.LayoutParams layoutParams=voice_num.getLayoutParams();
        layoutParams.width= (int) (dip2px(this,100)*mLight);
        voice_num.setLayoutParams(layoutParams);
        getWindow().setAttributes(lp);


    }



    private void updateTextViewWithTimeFormat(TextView textView, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mvideoView.pause();
        handler.removeMessages(UPDATE_UL);
        ScreenRotateUtil.getInstance(this).stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvideoView.suspend();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //当前方向为横屏
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            voice_seeBar.setVisibility(View.VISIBLE);
            voice_img.setVisibility(View.VISIBLE);
            control_barlayout.setVisibility(View.GONE);
            isFull=true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        } else {
            control_barlayout.setVisibility(View.VISIBLE);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(MainActivity.this, 240));
            voice_seeBar.setVisibility(View.GONE);
            voice_img.setVisibility(View.GONE);
            isFull=false;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }

    }
            //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pause:
                if (mvideoView.isPlaying()) {
                    pause1.setImageResource(R.drawable.pause6);
                    mvideoView.pause();
                    handler.removeMessages(UPDATE_UL);
                } else {
                    pause1.setImageResource(R.drawable.play6);
                    mvideoView.start();
                    handler.sendEmptyMessage(UPDATE_UL);
                }
                break;
            case R.id.screen_img:
               if(isFull){
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                   screen_img.setImageResource(R.drawable.screen);

               }else {
                   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                   screen_img.setImageResource(R.drawable.screen_out);


               }
                //scaleFull();
                break;
            case R.id.lock_img:
                if(unLocked){
                    lock_img.setImageResource(R.drawable.locked);
                    unLocked=false;
                }else {
                    lock_img.setImageResource(R.drawable.unlock);
                    unLocked=true;
                }

                ScreenRotateUtil.getInstance(MainActivity.this).setLockScreen();
                break;
        }
    }
}
