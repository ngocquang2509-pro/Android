package com.example.karaokeapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity; // Assuming AppCompatActivity is used
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "arirang.sqlite";

    EditText edttim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myArrayAdapter myarray1, myarray2, myarray3;
    TabHost tab;
    ImageButton btnxoa; // Added based on btnxoa.setOnClickListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assuming your main layout is activity_main.xml

        // preprocess() // This line is commented out in the image, but the method is defined.
        copyProcess(); // Calls the method to copy the database
        // Mô số dữ liệu để copy. Lấy vào biến database
        // (Some data to copy. Get into database variable)
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        addControls(); // Hàm thêm các controls (Method to add controls)
        addTim(); // Xử lý công việc tìm kiếm (Handle search task)
        addEvents(); // Xử lý sự kiện khi chuyển Tab và các sự kiện khác (Handle tab change and other events)
    }

    // Hàm khai báo và Add các Controls vào giao diện
    // (Method to declare and Add Controls to UI)
    // Trên 3 Tab chúng ta có 3 Listview ứng với 3 Danh sách dữ liệu (Dữ liệu tìm kiếm, Danh sách bài hát gốc, Danh sách bài hát yêu thích) và Adapter riêng
    // (On 3 Tabs we have 3 Listviews corresponding to 3 Data Lists (Search data, Original song list, Favorite song list) and separate Adapters)
    private void addControls() {
        // TODO Auto-generated method stub
        btnxoa = (ImageButton) findViewById(R.id.btnXoa);
        tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        // Cung cấp một chuỗi không rỗng, ví dụ: "Tìm kiếm"
        tab1.setIndicator("Tìm kiếm", getResources().getDrawable(R.drawable.ic_launcher_foreground));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        // Cung cấp một chuỗi không rỗng, ví dụ: "Danh sách"
        tab2.setIndicator("Danh sách", getResources().getDrawable(R.drawable.ic_launcher_foreground));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        // Cung cấp một chuỗi không rỗng, ví dụ: "Yêu thích"
        tab3.setIndicator("Yêu thích", getResources().getDrawable(R.drawable.ic_launcher_foreground));
        tab.addTab(tab3);

        edttim = (EditText) findViewById(R.id.edtTim);

        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        list1 = new ArrayList<Item>();
        list2 = new ArrayList<Item>();
        list3 = new ArrayList<Item>();

        myarray1 = new myArrayAdapter(MainActivity.this, R.layout.listitem, list1);
        lv1.setAdapter(myarray1);

        myarray2 = new myArrayAdapter(MainActivity.this, R.layout.listitem, list2);
        lv2.setAdapter(myarray2);

        myarray3 = new myArrayAdapter(MainActivity.this, R.layout.listitem, list3);
        lv3.setAdapter(myarray3);

        addDanhsach();

    }

    // Xử lý sự kiện khi chuyển qua lại giữa các Tab Danh sách và Yêu Thích
    // (Handle event when switching between "List" and "Favorite" Tabs)
    private void addEvents() {
        // TODO Auto-generated method stub
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                if (tabId.equalsIgnoreCase("t2")) {
                    addDanhsach();
                }
                if (tabId.equalsIgnoreCase("t3")) {
                    addYeuThich();
                }
            }
        });

        // Xử lý sự kiện khi Click vào Button xóa trên Tab Tìm kiếm
        // (Handle event when clicking "Clear" Button on "Search" Tab)
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                edttim.setText("");
            }
        });
    }

    // Hàm thêm bài hát vào Listview trên Tab Yêu thích
    // (Method to add songs to Listview on "Favorite" Tab)
    private void addYeuThich() {
        myarray3.clear(); // Clear the adapter before adding new data
        list3.clear();    // Clear the list backing the adapter
        Cursor c = database.rawQuery("SELECT * FROM AriRangSongList WHERE YEUTHICH = 1", null);
        if (c != null && c.moveToFirst()) {
            do {
                list3.add(new Item(
                        c.getString(1), // TENBH
                        c.getString(2), // LOIBAIHAT
                        c.getInt(4)     // YEUTHICH
                ));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        myarray3.notifyDataSetChanged();
    }

    // Hàm thêm bài hát vào Listview trên Tab Danh sách bài hát
    // (Method to add songs to Listview on "Song List" Tab)
    private void addDanhsach() {
        myarray2.clear(); // Clear the adapter before adding new data
        list2.clear();    // Clear the list backing the adapter
        Cursor c = database.rawQuery("SELECT * FROM AriRangSongList", null);
        if (c != null && c.moveToFirst()) {
            do {
                list2.add(new Item(
                        c.getString(1), // TENBH
                        c.getString(2), // LOIBAIHAT
                        c.getInt(4)     // YEUTHICH
                ));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        myarray2.notifyDataSetChanged();
    }

    // Hàm xử lý tìm kiếm bài hát theo Tiêu đề và Mã số
    // (Method to handle searching songs by Title and ID)
    private void addTim() {
        // TODO Auto-generated method stub
        // Sự kiện khi thay đổi text trong Edittext edttim
        // (Event when text changes in EditText edttim)
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getdata();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            private void
            getdata() {
                // TODO Auto-generated method stub
                String dulieunhap = edttim.getText().toString();
                myarray1.clear(); // Clear the adapter for search results
                list1.clear(); // Clear the list backing the adapter for search results

                if (dulieunhap.equals("")) {
                    // If search box is empty, don't show any results (or could display all)
                    // In this case, it clears the list and notifies.
                    myarray1.notifyDataSetChanged();
                    return;
                }

                Cursor c = database.rawQuery("SELECT * FROM AriRangSongList WHERE TENBH LIKE '%" + dulieunhap + "%' OR MABH LIKE '%" + dulieunhap + "%'", null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    list1.add(new Item(c.getString(1), c.getString(2), c.getInt(6))); // Assuming column indices for maso, tieude, thich
                    c.moveToNext();
                }
                c.close();
                myarray1.notifyDataSetChanged();
            }
        });
    }

    // Hàm xử lý Copy CS dữ liệu từ thư mục assets vào hệ thống thư mục cài đặt
    // (Method to handle copying DB from assets folder to installation directory)
    private void copyProcess() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying success from assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getMyDatabasePath(String databaseName) {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + databaseName;
    }

    public void CopyDataBaseFromAsset() throws IOException {
        // TODO Auto-generated method stub
        InputStream myInput = getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = getMyDatabasePath(DATABASE_NAME);
        // If the path doesn't exist first, create it
        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) {
            f.mkdir();
        }
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}