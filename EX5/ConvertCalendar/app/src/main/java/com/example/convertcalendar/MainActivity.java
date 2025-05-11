package com.example.convertcalendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText edtCalendarYear = findViewById(R.id.edtCalendarYear);
        TextView tvLunarYear = findViewById(R.id.tvLunarYear);
        Button btnConvert = findViewById(R.id.btnConvert);

        btnConvert.setOnClickListener(v -> {
            try {
                int namDuong = Integer.parseInt(edtCalendarYear.getText().toString().trim());

                String[] canList = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
                String[] chiList = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mẹo", "Thìn", "Tỵ", "Ngọ", "Mùi"};

                String can = canList[namDuong % 10];
                String chi = chiList[namDuong % 12];

                String namAmLich = can + " " + chi;
                tvLunarYear.setText(namAmLich);
            } catch (NumberFormatException e) {
                tvLunarYear.setText("Năm không hợp lệ!");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
