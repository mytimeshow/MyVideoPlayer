package com.example.administrator.myvideoplayer.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.myvideoplayer.R;

import java.io.File;

public class PlayActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        videoView= (VideoView) findViewById(R.id.video_play);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        if(ContextCompat.checkSelfPermission(PlayActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(PlayActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }else{
            String path=Environment.getExternalStorageDirectory()+"/mm.mp4";
            File file=new File(Environment.getExternalStorageDirectory(),"mm.mp4");
            videoView.setVideoPath(path);

            //videoView.setVideoURI(Uri.parse("http://v.qq.com/iframe/player.html?tiny=0&auto=1&vid=p0024x5a8sw"));
            videoView.start();
            Toast.makeText(this, "granted2", Toast.LENGTH_SHORT).show();
            Log.e("AAA", path);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                File file=new File(Environment.getExternalStorageDirectory(),"mm.mp4");
                videoView.setVideoPath(file.getPath());
                videoView.start();
                Toast.makeText(this, file.getPath(), Toast.LENGTH_SHORT).show();
                Log.e("AAA", file.getPath());

            }else{
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
