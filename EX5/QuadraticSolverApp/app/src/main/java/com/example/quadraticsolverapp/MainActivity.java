package com.example.quadraticsolverapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnContinue,btnExit,btnSolve;
    EditText edtA,edtB,edtC;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Vidu_Ptb2");
        btnContinue = findViewById(R.id.btnContinue);
        btnExit = findViewById(R.id.btnExit);
        btnSolve = findViewById(R.id.btnSolve);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtC = findViewById(R.id.edtC);
        tvResult = findViewById(R.id.tvResult);
        btnSolve.setOnClickListener(v -> {
            String kq = "";
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            int a = Integer.parseInt(edtA.getText().toString());
            int b = Integer.parseInt(edtB.getText().toString());
            int c = Integer.parseInt(edtC.getText().toString());
            if(a==0){
                if(b==0){
                    if(c==0){
                        kq = "Phương trình vô số nghiệm";
                    }else{
                        kq = "Phương trình vô nghiệm";
                    }
                }else{
                    kq = "Phương trình có 1 nghiệm x = " + decimalFormat.format((double)-c/b);
                }
            }else{
                double delta = b*b - 4*a*c;
                if(delta < 0){
                    kq = "Phương trình vô nghiệm";
                }else if(delta == 0){
                    kq = "Phương trình có nghiệm kép x1 = x2 = " + decimalFormat.format((double)-b/(2*a));
                }else{
                    kq = "Phương trình có 2 nghiệm phân biệt:\n" +
                            "x1 = " + decimalFormat.format((-b + Math.sqrt(delta))/(2*a)) + "\n" +
                            "x2 = " + decimalFormat.format((-b - Math.sqrt(delta))/(2*a));
                }
            }
            tvResult.setText(kq);

        });
btnContinue.setOnClickListener(v -> {
            edtA.setText("");
            edtB.setText("");
            edtC.setText("");
            edtA.requestFocus();
        });
        btnExit.setOnClickListener(v -> finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}