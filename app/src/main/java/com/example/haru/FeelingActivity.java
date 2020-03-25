package com.example.haru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class FeelingActivity extends AppCompatActivity {

    Button bnt;
    TextView mind;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeling);

        bnt = findViewById(R.id.button);
        mind  = (TextView) findViewById(R.id.text); // Here

        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputStream inputStream1 = getResources().openRawResource(R.raw.mind1); // Here
                InputStream inputStream2 = getResources().openRawResource(R.raw.mind2);
                InputStream inputStream3 = getResources().openRawResource(R.raw.mind3);
                InputStream inputStream4 = getResources().openRawResource(R.raw.mind4);
                ArrayList<InputStream> list11;
                list11 = new ArrayList<InputStream>();
                list11.add(inputStream1);
                list11.add(inputStream2);
                list11.add(inputStream3);
                list11.add(inputStream4);
                Random rand = new Random();
                int randint = 4;
                randint = rand.nextInt(4);
                InputStream inputStream = list11.get(randint);
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                StringBuilder textext = new StringBuilder();

                try {
                    while ((line = buffreader.readLine()) != null) {
                        mind.append(line);
                        Log.d("log", line);
                        mind.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("log", "읽기 실패");
                }
            }
            });



}
    }
