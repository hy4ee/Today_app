package com.example.haru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class HumorActivity extends AppCompatActivity {

    Button bnt;
    TextView mind;
    ArrayList<String> list = new ArrayList<>();
    String buf = null;
    String next = "다음";
    int line=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humor);

        bnt = findViewById(R.id.button);
        mind = (TextView) findViewById(R.id.text); // Here

        mind.append("\n");


        Path fp = Paths.get("humor.txt");
        InputStream in = getResources().openRawResource(R.raw.humor);
        try{
            InputStreamReader stream = new InputStreamReader(in,"utf-8");
            BufferedReader bufReader = new BufferedReader(stream);

            buf = bufReader.readLine();

            for(int i=0;i<=40;i++)
            {
                buf = bufReader.readLine();
                list.add(buf);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<=line;i++) {
                    next = list.get(i);

                }
                mind.append(next);
                mind.append("\n");
                line++;
        }
    });
    }
}