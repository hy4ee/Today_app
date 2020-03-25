package com.example.haru;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;


import java.io.InputStream;

public class MemoActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentsEditText;
    ImageView imageView;
    Button button;

    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        imageView = (ImageView)findViewById(R.id.image);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        mTitleEditText = (EditText) findViewById(R.id.title_edit);
        mContentsEditText = (EditText) findViewById(R.id.contents_edit);

        Intent intent = getIntent();
        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String contents = intent.getStringExtra("contents");
            mTitleEditText.setText(title);
            mContentsEditText.setText(contents);
        }
    }

    @Override
    public void onBackPressed() {
        // DB 에 저장하는 처리
        String title = mTitleEditText.getText().toString();
        String contents = mContentsEditText.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);

//        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();
        if (mMemoId == -1) {
//            // DB 에 저장하는 처리
//            long newRowId = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, contentValues);
//
//            if (newRowId == -1) {
            Uri uri = getContentResolver().insert(MemoProvider.CONTENT_URI, contentValues);
            if (uri == null) {

                Toast.makeText(this, "저장에 문제가 발생하였습니다",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // 기존 메모 내용을 업데이트 처리
//            int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues,
//                    MemoContract.MemoEntry._ID + " = " + mMemoId, null);
            int count = getContentResolver().update(MemoProvider.CONTENT_URI,
                    contentValues, MemoContract.MemoEntry._ID + " = " + mMemoId, null);


            if (count == 0) {
                Toast.makeText(this, "수정에 문제가 발생하였습니다",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "메모가 수정되었습니다", Toast.LENGTH_SHORT).show();
            }
        }


        // 뒤로 가기의 원래의 동작이 실행 됨
        super.onBackPressed();
    }


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
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
