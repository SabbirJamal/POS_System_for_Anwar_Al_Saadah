package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class onlyViewResize extends AppCompatActivity {

    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView,selectDate;
    final Calendar mycalender =Calendar.getInstance();


    TextView oid,cn,amt,dd,s2,empid2;

    //search by date
    ImageView menu,home,search;

    resizeDatabase rdb;
    fullview_Sales ca;
    ArrayList<String>h,wc,wh,s,b,hand,a,w,phn,od,empid,il;
    RecyclerView rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_only_view_resize);
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

        menu=findViewById(R.id.imgmenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(onlyViewResize.this, search_CustomerItem.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        search=findViewById(R.id.imgsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(onlyViewResize.this, search_CustomerItem.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        home=findViewById(R.id.imghome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(onlyViewResize.this, employeeHomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        //code for getting intended data from prvs cardview
        oid=findViewById(R.id.orderID);
        cn=findViewById(R.id.custName);
        amt=findViewById(R.id.totalamt);
        dd=findViewById(R.id.deliveryDate);
        s2=findViewById(R.id.clothStatus);
        empid2=findViewById(R.id.employeeID);

        if(getIntent().hasExtra("orid") && getIntent().hasExtra("cn") && getIntent().hasExtra("tamt") && getIntent().hasExtra("dd") &&
                getIntent().hasExtra("s") && getIntent().hasExtra("en")) {
            String a1 = getIntent().getStringExtra("orid");
            String a2 = getIntent().getStringExtra("cn");
            String a3 = getIntent().getStringExtra("tamt");
            String a5 = getIntent().getStringExtra("dd");
            String a6 = getIntent().getStringExtra("s");
            String a8 = getIntent().getStringExtra("en");

            String eid=a8;

            oid.setText(a1);
            cn.setText(a2);
            amt.setText(a3);
            dd.setText(a5);
            s2.setText(a6);
            empid2.setText(a8);

            String phonenumber=empid2.getText().toString();
        }

        //code for remaining information in recycleview
        rc=findViewById(R.id.recyclerView);
        rdb=new resizeDatabase(this);
        h=new ArrayList<>();
        wc=new ArrayList<>();
        wh=new ArrayList<>();
        s=new ArrayList<>();
        b=new ArrayList<>();
        hand=new ArrayList<>();
        a=new ArrayList<>();
        w=new ArrayList<>();
        phn=new ArrayList<>();
        od=new ArrayList<>();
        empid=new ArrayList<>();
        il=new ArrayList<>();

        viewalldata();

        ca=new fullview_Sales(onlyViewResize.this,h,wc,wh,s,b,hand,a,w,phn,od,empid,il);
        rc.setAdapter(ca);
        rc.setLayoutManager(new LinearLayoutManager(onlyViewResize.this));
        //end of recycle view



    }
    public void viewalldata(){
        String phonenumber=empid2.getText().toString();
        String orderid=oid.getText().toString();
        Cursor c = rdb.viewepecificresize3(phonenumber,orderid);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            phn.add(c.getString(1));
            h.add(c.getString(3));
            wc.add(c.getString(4));
            wh.add(c.getString(5));
            s.add(c.getString(6));
            b.add(c.getString(7));
            hand.add(c.getString(8));
            a.add(c.getString(9));
            w.add(c.getString(10));
            od.add(c.getString(12));
            empid.add(c.getString(15));
            il.add(c.getString(18));
        }
    }

}