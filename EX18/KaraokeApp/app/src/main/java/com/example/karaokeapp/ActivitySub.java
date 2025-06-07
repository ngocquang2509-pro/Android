package com.example.karaokeapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ActivitySub extends AppCompatActivity {

    TextView txtmaso, txtbaihat, txtloibaihat, txttacgia;
    ImageButton btnthich, btnkhongthich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtmaso = (TextView) findViewById(R.id.txtmaso);
        txtbaihat = (TextView) findViewById(R.id.txtbaihat);
        txtloibaihat = (TextView) findViewById(R.id.txtloibaihat);
        txttacgia = (TextView) findViewById(R.id.txttacgia);
        btnthich = (ImageButton) findViewById(R.id.btnthich);
        btnkhongthich = (ImageButton) findViewById(R.id.btnkhongthich);

        // Nhận Intent từ myArrayAdapter, lấy dữ liệu khỏi Bundle
        // (Receive Intent from myArrayAdapter, get data from Bundle)
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("package");
        String maso = packageFromCaller.getString("maso");

        // Truy vấn dữ liệu từ maso nhận được; Hiển thị dữ liệu Mã bài hát, Tên bài hát, Lời bài hát, Tác giả, Trạng thái Thích lên Activitysub
        // (Query data using received maso; Display Song ID, Song Name, Lyrics, Author, Like Status on Activitysub)
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM AriRangSongList WHERE MABH = '" + maso + "'", null);
        c.moveToFirst();

        txtmaso.setText(maso); // Setting maso from the bundle, not directly from cursor for this field
        txtbaihat.setText(c.getString(2)); // Assuming column index 2 is for baihat
        txtloibaihat.setText(c.getString(3)); // Assuming column index 3 is for loibaihat
        txttacgia.setText(c.getString(4)); // Assuming column index 4 is for tacgia

        if (c.getInt(6) == 0) { // Assuming column index 6 is for the 'thich' status
            btnthich.setVisibility(View.INVISIBLE);
            btnkhongthich.setVisibility(View.VISIBLE);
        } else {
            btnthich.setVisibility(View.VISIBLE); // Should be btnthich? Assuming typo in image: btnkhich should be btnthich
            btnkhongthich.setVisibility(View.INVISIBLE);
        }
        c.close();

        // Xử lý sự kiện khi click vào Button btnthich và btnkhongthich
        // (Handle click events for Button btnthich and btnkhongthich)
        // Cập nhật dữ liệu vào CSDL, thay đổi trạng thái hiển thị cho Button btnthich và btnkhongthich
        // (Update data in DB, change display status for Button btnthich and btnkhongthich)
        btnthich.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0); // Assuming "YEUTHICH" is the column name for the like status
                MainActivity.database.update("AriRangSongList", values,
                        "MABH=?", new String[]{txtmaso.getText().toString()});
                btnthich.setVisibility(View.INVISIBLE);
                btnkhongthich.setVisibility(View.VISIBLE);
            }
        });

        btnkhongthich.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1); // Assuming "YEUTHICH" is the column name for the like status
                MainActivity.database.update("AriRangSongList", values,
                        "MABH=?", new String[]{txtmaso.getText().toString()});
                btnthich.setVisibility(View.VISIBLE);
                btnkhongthich.setVisibility(View.INVISIBLE);
            }
        });
    }
    }
