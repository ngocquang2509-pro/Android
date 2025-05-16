package com.example.phonelist;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        final String arr[] = {"Iphone 12 pro max", "Redmi note 10", "Samsung galaxy S23 ultra", "Vivo 5", "Oppo Reno 10 pro"};
        ArrayAdapter<String> adapterPhone = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        ListView lvPhones = findViewById(R.id.lvPhones);
        lvPhones.setAdapter(adapterPhone);
        lvPhones.setOnItemClickListener((parent, view, position, id) -> {
            textView.setText("Vị trí: " + position + "\n" + "Tên điện thoại: " + arr[position]);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}