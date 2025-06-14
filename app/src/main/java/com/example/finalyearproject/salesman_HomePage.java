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
import java.util.Date;
import java.util.Locale;

public class salesman_HomePage extends AppCompatActivity {

    //for getting header intent data
    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView;

    //recycle view of orders to stitch
    RecyclerView rc2;
    //identifying order database
    orderDatabase odb;
    //calling the custom adapter
    customerAdapter_CardView_View ca2;
    ArrayList<String>oid,cn,tamt,dd,s,en;
    String phonenumber;


    //recycle view of resize to cut
    RecyclerView rc3;
    //identifying order database
    resizeDatabase rdb;
    //calling the custom adapter
    customerAdapter_CardView_View ca3;
    ArrayList<String>roid,rcn,rtamt,rdd,rs,ren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salesman_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting intented phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }

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

        //codes for orders to cut recycleview
        rc2=findViewById(R.id.orderrecyclerView);
        odb=new orderDatabase(this);

        oid=new ArrayList<>();
        cn=new ArrayList<>();
        tamt=new ArrayList<>();
        dd=new ArrayList<>();
        s=new ArrayList<>();
        en=new ArrayList<>();

        ViewOrderstoStitch();

        ca2=new customerAdapter_CardView_View(salesman_HomePage.this,oid,cn,tamt,dd,s,en);
        rc2.setAdapter(ca2);
        rc2.setLayoutManager(new LinearLayoutManager(salesman_HomePage.this));
        //end of recycleview for orders to cut

        //codes for resize to cut recycleview
        rc3=findViewById(R.id.resizerecyclerView);
        rdb=new resizeDatabase(this);

        roid=new ArrayList<>();
        rcn=new ArrayList<>();
        rtamt=new ArrayList<>();
        rdd=new ArrayList<>();
        rs=new ArrayList<>();
        ren=new ArrayList<>();

        //ViewResizetoStitch();

        ca3=new customerAdapter_CardView_View(salesman_HomePage.this,roid,rcn,rtamt,rdd,rs,ren);
        rc3.setAdapter(ca3);
        rc3.setLayoutManager(new LinearLayoutManager(salesman_HomePage.this));
        //end of recycleview for resize to stitch


    }

    public void ViewOrderstoStitch() {
        Cursor c = odb.vieworderstostitch(phonenumber);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            oid.add(c.getString(0));
            cn.add(c.getString(2));
            tamt.add(c.getString(14));
            dd.add(c.getString(15));
            s.add(c.getString(19));
            en.add(c.getString(17));
        }
    }

    public void ViewResizetoStitch() {
        Cursor c = rdb.viewepecificresizetostitch(phonenumber);
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