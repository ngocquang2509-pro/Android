package com.example.registerpersonalinformation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtName,edtCMND,edtAddInfo;
    CheckBox ckbNews,ckbBook,ckbCode;
    Button btnSubmit;
    RadioGroup rdgLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtCMND = findViewById(R.id.edtCMND);
        edtAddInfo = findViewById(R.id.edtAddInfo);
        ckbNews = findViewById(R.id.ckbNews);
        ckbBook = findViewById(R.id.ckbBook);
        ckbCode = findViewById(R.id.ckbCode);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {
                doShowInfo();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void doShowInfo() {
        String name = edtName.getText().toString();
        name= name.trim();
        if(name.length()<3){
            edtName.requestFocus() ;
            edtName.selectAll();
            Toast.makeText(this,"Tên phải có trên 3 ký tự",Toast.LENGTH_SHORT).show();
            return;
        }
        String cmnd = edtCMND.getText().toString();
        cmnd= cmnd.trim();
        if(cmnd.length()!=9){
            edtCMND.requestFocus() ;
            edtCMND.selectAll();
            Toast.makeText(this,"CMND phải có đúng 9 ký tự",Toast.LENGTH_SHORT).show();
            return;
        }
        String degree = "";
        rdgLevel = findViewById(R.id.rdgLevel);
        int id = rdgLevel.getCheckedRadioButtonId();
        if(id==-1){
            Toast.makeText(this,"Phải chọn bằng cấp",Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rad = findViewById(id);
        degree = rad.getText().toString();
        String hobbies = "";
        if(ckbNews.isChecked()){
            hobbies += ckbNews.getText().toString() + "\n";
        }
        if(ckbBook.isChecked()){
            hobbies += ckbBook.getText().toString() + "\n";
        }
        if(ckbCode.isChecked()){
            hobbies += ckbCode.getText().toString() + "\n";
        }
        String addInfo = edtAddInfo.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setPositiveButton("Đóng",new DialogInterface.OnClickListener() {;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtName.setText("");
                edtCMND.setText("");
                edtAddInfo.setText("");
                ckbNews.setChecked(false);
                ckbBook.setChecked(false);
                ckbCode.setChecked(false);
                rdgLevel.clearCheck();
            }
        });
        String msg = name + "\n" +
                "CMND: " + cmnd + "\n" +
                "Bằng cấp: " + degree + "\n" +
                "Sở thích: \n" + hobbies +
                "______________________\n"+
                "Thông tin bổ sung:\n" + addInfo+"\n"+
                "______________________";
        builder.setMessage(msg);
        builder.create().show();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
       b.setTitle("Question");
         b.setMessage("Do you want to exit?");
         b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                finish();
             }
         });
            b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();
    }
}