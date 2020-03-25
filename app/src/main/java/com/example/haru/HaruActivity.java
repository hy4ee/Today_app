package com.example.haru;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.haru.FeelingActivity;
import com.example.haru.HumorActivity;
import com.example.haru.VideoActivity;
import com.google.android.gms.location.LocationServices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HaruActivity extends AppCompatActivity {

    MediaPlayer mp1,mp2,mp3,mp4,mp5,mediaPlayer;

    ArrayList<MediaPlayer> list;

    Button startButton;
    //종료버튼
    Button stopButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haru);

        Intent intent = getIntent();
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
        callSayingFile();

        mp1 = MediaPlayer.create(HaruActivity.this, R.raw.rose);
        mp2 = MediaPlayer.create(HaruActivity.this, R.raw.umbrella);
        mp3 = MediaPlayer.create(HaruActivity.this, R.raw.young);
        mp4 = MediaPlayer.create(HaruActivity.this, R.raw.telephone);
        mp5 = MediaPlayer.create(HaruActivity.this, R.raw.soledad);
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
    //메뉴 화면 넘기기

    public void sendVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void sendHumor(View view) {
        Intent intent = new Intent(this, HumorActivity.class);
        startActivity(intent);
    }

    public void sendFeeling(View view) {
        Intent intent = new Intent(this, FeelingActivity.class);
        startActivity(intent);
    }

    protected void callSayingFile() {
        String buf = "이런! 격언이 로딩되지 않았어요 ㅜㅜ.";
        Path fp = Paths.get("saying.txt");  // Here
        Random rand = new Random();
        int randinta = 4;
        TextView saying = (TextView) findViewById(R.id.saying); // Here
        InputStream in = getResources().openRawResource(R.raw.saying);  // Here
        try {
            InputStreamReader stream = new InputStreamReader(in, "utf-8");
            BufferedReader bufReader = new BufferedReader(stream);
            randinta = rand.nextInt(14); // Here
            for(int i=0;i<randinta+1;i++)
            {
                buf = bufReader.readLine();
            }
        }
        catch(IOException e) {
            System.out.println("Exception occered");
            e.printStackTrace();
        }

        saying.setText(buf);
    }
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


