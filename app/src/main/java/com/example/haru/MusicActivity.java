package com.example.haru;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MusicActivity extends AppCompatActivity {

    // MediaPlayer 객체생성
    MediaPlayer mp1,mp2,mp3,mp4,mp5,mediaPlayer;

    ArrayList<MediaPlayer> list;

    // 시작버
    Button startButton;
    //종료버튼
    Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mp1 = MediaPlayer.create(MusicActivity.this, R.raw.rose);
        mp2 = MediaPlayer.create(MusicActivity.this, R.raw.umbrella);
        mp3 = MediaPlayer.create(MusicActivity.this, R.raw.young);
        mp4 = MediaPlayer.create(MusicActivity.this, R.raw.telephone);
        mp5 = MediaPlayer.create(MusicActivity.this, R.raw.soledad);
        list = new ArrayList<>();
        list.add(mp1);
        list.add(mp2);
        list.add(mp3);
        list.add(mp4);
        list.add(mp5);

        startButton = findViewById(R.id.playBtn);

        stopButton = findViewById(R.id.pauseBtn);



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                Random rand = new Random();
                int randint = 4;
                randint = rand.nextInt(4);
                mediaPlayer = list.get(randint);
                mediaPlayer.start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                mediaPlayer.stop();
                // 초기화
            }
        });
    }


    // MediaPlayer는 시스템 리소스를 잡아먹는다.
    // MediaPlayer는 필요이상으로 사용하지 않도록 주의해야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}