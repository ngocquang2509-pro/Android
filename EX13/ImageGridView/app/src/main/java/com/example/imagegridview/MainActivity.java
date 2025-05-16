package com.example.imagegridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String[] imageName = {"Ảnh 1", "Ảnh 2", "Ảnh 3", "Ảnh 4", "Ảnh 5", "Ảnh 6", "Ảnh 7", "Ảnh 8", "Ảnh 9", "Ảnh 10", "Ảnh 11", "Ảnh 12"};
    public static int[] image = {R.drawable.img, R.drawable.img_1,R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7,
            R.drawable.img_8};

    GridView gridViewDemo;
    //Sử dụng MyArrayAdapter thay vì ArrayAdapter
    MyArrayAdapter adapterDanhSach;
    ArrayList<Image> arrimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridViewDemo = (GridView) findViewById(R.id.grid);
        arrimage = new ArrayList<>();
        //Khởi tạo đối tượng adapter và gán Data Source
        adapterDanhSach = new MyArrayAdapter(MainActivity.this,
                R.layout.listitem, //lấy custom layout
                arrimage);        //thiết lập data source

        gridViewDemo.setAdapter(adapterDanhSach);
        //gán adapter vào GridView

        for (int i = 0; i < imageName.length; i++) {
            Image myImage = new Image();
            myImage.setName(imageName[i]);
            myImage.setImg(image[i]);
            arrimage.add(myImage);
        }
        adapterDanhSach.notifyDataSetChanged();
        //cập nhật giao diện

        //Viết sự kiện khi click vào đối tượng trong GridView
        gridViewDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                //TODO Auto-generated method stub

                //Khai báo Intent
                Intent intent1 = new Intent(MainActivity.this, child_layoutActivity.class);
                //Khai báo bundle và đưa dữ liệu vào bundle, tham số arg2 chứa vị trí của phần tử
                //mà chúng ta click trong GridView
                Bundle bundle = new Bundle();
                bundle.putString("TITLE", position+"");
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
    }
}