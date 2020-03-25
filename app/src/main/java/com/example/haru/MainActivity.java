package com.example.haru;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

import com.example.haru.fineweather.FineWeatherContract;
import com.example.haru.fineweather.FineWeatherFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FINE_COARSE_PERMISSION = 1000;
    private Fragment mFragment;
    private ViewPager mViewPager;


    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDbData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ImageButton button;
        button = (ImageButton)findViewById(R.id.btnUser);
        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
            }
        });
        callSayingFile();
    }


    protected void callSayingFile() {
        String buf = "이런! 말이 나오지 않아요!.";
        Path fp = Paths.get("feeling.txt");  // Here
        Random rand = new Random();
        int randinta = 4;
        TextView saying = (TextView) findViewById(R.id.feelgood); // Here
        InputStream in = getResources().openRawResource(R.raw.feeling);  // Here
        try {
            InputStreamReader stream = new InputStreamReader(in, "utf-8");
            BufferedReader bufReader = new BufferedReader(stream);
            randinta = rand.nextInt(7); // Here
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    saveBitmaptoJpeg(img,"@drawable" ,"user");
                    // 이미지 표시

                    ImageButton button;
                    button = (ImageButton)findViewById(R.id.btnUser);
                    button.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name) {
        String ex_storage  = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".png";
        String string_path = ex_storage+foler_name;
        File file_path;
        try{ file_path = new File(string_path);
            if(!file_path.isDirectory()){ file_path.mkdirs(); }
            FileOutputStream out = new FileOutputStream(string_path+file_name);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        }
        catch(FileNotFoundException exception)
        { Log.e("FileNotFoundException", exception.getMessage()); }
        catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }

    private void setUpViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        loadDbData();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragment);
        mViewPager.setAdapter(adapter);
    }

    private void loadDbData() {
        mFragment = new FineWeatherFragment();
    }

//현재위치
    public void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_FINE_COARSE_PERMISSION);
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            FineWeatherContract.View view = (FineWeatherContract.View) mFragment;
                            view.reload(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_FINE_COARSE_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            }
        }
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final Fragment mFragment;

        public MyPagerAdapter(FragmentManager fm, Fragment fragment) {
            super(fm);
            mFragment = fragment;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    //메뉴 화면 넘기기

    public void sendHaru(View view) {
        Intent intent = new Intent(this, HaruActivity.class);
        startActivity(intent);
    }

    public void sendTodo(View view) {
        Intent intent = new Intent(this, TodoActivity.class);
        startActivity(intent);
    }


    public void sendDiary(View view) {
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }
}