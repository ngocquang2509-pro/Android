package com.example.notelist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvWork;
    ArrayList<String> arrayWork;
    ArrayAdapter<String> adapterAdapter;
    EditText edtWork,edtHour,edtMinute;
    TextView txtDate;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Listview_new");
        lvWork = findViewById(R.id.lvWork);
        edtWork = findViewById(R.id.edtWork);
        edtHour = findViewById(R.id.edtHour);
        edtMinute = findViewById(R.id.edtMinute);
        txtDate = findViewById(R.id.txtDate);
        btnAdd = findViewById(R.id.btnAdd);
        arrayWork = new ArrayList<>();
        adapterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayWork);
        lvWork.setAdapter(adapterAdapter);
        txtDate.setText(String.valueOf(System.currentTimeMillis()));
        btnAdd.setOnClickListener(v -> {
            if (edtWork.getText().toString().isEmpty()||edtHour.getText().toString().isEmpty()||edtMinute.getText().toString().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Info missing");
                builder.setMessage("Please enter all infomation of the work");
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }else {
                String str = edtWork.getText().toString() + "-" + edtHour.getText().toString() + ":" + edtMinute.getText().toString();
                arrayWork.add(str);
                adapterAdapter.notifyDataSetChanged();
                    edtWork.setText("");
                    edtHour.setText("");
                    edtMinute.setText("");
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}