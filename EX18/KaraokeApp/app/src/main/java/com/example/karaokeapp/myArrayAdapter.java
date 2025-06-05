import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // Assuming Toast might be used for debugging or user feedback, not directly in the provided code but common.

import com.example.karaokeapp.Item;

import java.util.ArrayList;

public class myArrayAdapter extends ArrayAdapter<Item> {

    Activity context = null;
    ArrayList<Item> myArray = null;
    int layoutId;

    public myArrayAdapter(Activity context, int layoutId, ArrayList<Item> arr) {
        super(context, layoutId, arr);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        final Item myItem = myArray.get(position);
        final TextView tieude = (TextView) convertView.findViewById(R.id.txttieude);
        tieude.setText(myItem.getTieude());

        final TextView maso = (TextView) convertView.findViewById(R.id.txtmaso);
        maso.setText(myItem.getMaso());

        final ImageView btnlike = (ImageView) convertView.findViewById(R.id.btnlike);
        final ImageView btnunlike = (ImageView) convertView.findViewById(R.id.btnunlike);

        int thich = myItem.getThich();

        // Xử lý hiển thị ảnh cho ImageButton btnlike và btnunlike
        // (Handle visibility of ImageButton btnlike and btnunlike)
        if (thich == 0) {
            btnunlike.setVisibility(View.INVISIBLE); // cho ẩn btnunlike (hide btnunlike)
            btnunlike.setClickable(false); // disable click for btnunlike
            btnlike.setVisibility(View.VISIBLE); // cho hiện btnlike (show btnlike)
            btnlike.setClickable(true); // enable click for btnlike
        } else {
            btnunlike.setVisibility(View.VISIBLE); // cho hiện btnunlike (show btnunlike)
            btnunlike.setClickable(true); // enable click for btnunlike
            btnlike.setVisibility(View.INVISIBLE); // cho ẩn btnlike (hide btnlike)
            btnlike.setClickable(false); // disable click for btnlike
        }

        // Xử lý sự kiện khi click vào ImageButton btnlike và btnunlike
        // (Handle click event for ImageButton btnlike and btnunlike)
        // Cập nhật trạng thái thích vào CSDL; Thiết lập ImageButton cho phù hợp
        // (Update like status in DB; Set ImageButton accordingly)
        btnlike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("THICH", 1);
                MainActivity.database.update("AriRangSongList", values,
                        "MABH=?", new String[]{maso.getText().toString()});
                btnlike.setVisibility(View.INVISIBLE);
                btnunlike.setVisibility(View.VISIBLE);
            }
        });

        btnunlike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("THICH", 0);
                MainAcitivity.database.update("AriRangSongList", values,
                        "MABH=?", new String[]{maso.getText().toString()});
                btnlike.setVisibility(View.VISIBLE);
                btnunlike.setVisibility(View.INVISIBLE);
            }
        });

        // Xử lý sự kiện khi click vào mỗi dòng tiêu đề để bật listview trên Listview
        // (Handle click event on each title row to enable listview on Listview)
        // Chuyển Textview tieude và maso sang màu đỏ
        // (Change Textview tieude and maso to red)
        // Khai báo Intent, Bundle, truyền qua subactivity và gọi activitysub
        // (Declare Intent, Bundle, pass to subactivity and call activitysub)
        // (xây dựng được sau)
        tieude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tieude.setTextColor(Color.RED);
                maso.setTextColor(Color.RED);
                Intent intent1 = new Intent(context, activitysub.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("maso", maso.getText().toString());
                intent1.putExtras(bundle1);
                context.startActivity(intent1);
            }
        });

        return convertView;
    }
}