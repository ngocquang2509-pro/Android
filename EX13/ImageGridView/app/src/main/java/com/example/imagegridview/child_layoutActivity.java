package com.example.imagegridview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class child_layoutActivity extends AppCompatActivity {
    private Bundle extra;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_layout);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        extra = getIntent().getExtras();
        int position = Integer.parseInt(extra.getString("TITLE"));
        textView.setText(MainActivity.imageName[position]);
        imageView.setImageResource(MainActivity.image[position]);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}