package com.example.intent_service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnPause;
    Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
            if (flag == true) {
                btnStart.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_2));
                flag = false;
            } else {
                btnStart.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_1));
                flag = true;

            }
        });
        btnPause.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            stopService(intent);
            btnStart.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_1));
            flag = true;
        });
        getSupportActionBar().setTitle("Intent_service");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}