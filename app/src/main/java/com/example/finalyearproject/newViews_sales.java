package com.example.finalyearproject;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class newViews_sales extends AppCompatActivity {
    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView,selectDate;
    final Calendar mycalender =Calendar.getInstance();


    RecyclerView rc3;
    //identifying resize database
    resizeDatabase rdb;
    //calling the custom adapter
    resize_to_cut_customAdapter ca3;
    ArrayList<String> roid,rcn,rtamt,rdd,rs,ren;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_views_sales);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //header intent data
        employeename=findViewById(R.id.fullnametxt);
        employeetype=findViewById(R.id.employeetypetxt);
        employeeid=findViewById(R.id.employeeidtxt);
        //code for getting intended data
        if(getIntent().hasExtra("ename") && getIntent().hasExtra("etype") && getIntent().hasExtra("eid")) {
            //getting the data from intent
            String n = getIntent().getStringExtra("ename");
            String et = getIntent().getStringExtra("etype");
            String id = getIntent().getStringExtra("eid");

            //setting intended data
            employeename.setText(n);
            employeetype.setText(et);
            employeeid.setText(id);

            Log.d("sample",n+" "+et+" "+id);
        }else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }

        //getting current date
        dateTextView = findViewById(R.id.currentdate);

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Set date to text field
        dateTextView.setText(currentDate);
        //end of getting current date

        //codes for resize to cut recycleview
        rc3=findViewById(R.id.viewResize);
        rdb=new resizeDatabase(this);

        roid=new ArrayList<>();
        rcn=new ArrayList<>();
        rtamt=new ArrayList<>();
        rdd=new ArrayList<>();
        rs=new ArrayList<>();
        ren=new ArrayList<>();

        ViewResizetoCut();

        ca3=new resize_to_cut_customAdapter(newViews_sales.this,roid,rcn,rtamt,rdd,rs,ren);
        rc3.setAdapter(ca3);
        rc3.setLayoutManager(new LinearLayoutManager(newViews_sales.this));
        //end of recycleview for resize to cut


    }

    public void ViewResizetoCut() {
        String phonenumber=employeeid.getText().toString();
        Cursor c = rdb.viewresizebyEMPID(phonenumber);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            roid.add(c.getString(0));
            rcn.add(c.getString(2));
            rtamt.add(c.getString(11));
            rdd.add(c.getString(13));
            rs.add(c.getString(16));
            ren.add(c.getString(14));
        }
    }
}