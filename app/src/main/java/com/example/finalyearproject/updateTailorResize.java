package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class updateTailorResize extends AppCompatActivity {


    //intended data
    TextView employeename,employeetype,employeeid;

    //fields for getting prvs intend data
    TextView oid,cn,amt,bal,dd,s,advanceamt,empid;

    //DROP DOWN LIST
    Spinner categorySpinner;
    TextView tailor;
    empdatabase empdb;

    //recycleview for remaing data
    //recycleview for remaing data
    resizeDatabase rdb;
    viewResizeDetails ca1;
    ArrayList<String>h,wc,wh,sh,b,hand,a,w;
    RecyclerView rc1;
    Button f;


    ImageView search,home,menu;


    TextView dateTextView;
    final Calendar mycalender =Calendar.getInstance();
    EditText isle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_tailor_resize);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting current date
        dateTextView = findViewById(R.id.currentdate);

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Set date to text field
        dateTextView.setText(currentDate);

        //code for getting intended data for header
        employeename=findViewById(R.id.fullnametxt);
        employeetype=findViewById(R.id.employeetypetxt);
        employeeid=findViewById(R.id.employeeidtxt);

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

        //botton cardview intent codes

        home=findViewById(R.id.imghome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updateTailorResize.this, salesman_HomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                //send phone number data
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        menu=findViewById(R.id.imgmenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updateTailorResize.this, tailorMENU.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                //send phone number data
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        //code for getting intended data from prvs cardview
        oid=findViewById(R.id.orderID);
        cn=findViewById(R.id.custName);
        amt=findViewById(R.id.totalamt);
        dd=findViewById(R.id.deliveryDate);
        s=findViewById(R.id.clothStatus);
        empid=findViewById(R.id.employeeID);

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
            s.setText(a6);
            empid.setText(a8);

            String phonenumber=empid.getText().toString();
        }



        isle=findViewById(R.id.isleNumber);

        //updating status
        f=findViewById(R.id.finish);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderID = oid.getText().toString();  // Get Order ID from input
                String il = isle.getText().toString();  // Get tailor from dropdown
                String s = "Completed";  // Fixed status

                boolean update = rdb.updatestatusRESIZEStITCH(orderID, s, il);  // Only pass Order_ID, Status, Tailor

                if (update) {
                    Toast.makeText(updateTailorResize.this, "Data updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(updateTailorResize.this, "Data not updated", Toast.LENGTH_LONG).show();
                }

            }
        });

        //code for remaining information in recycleview
        rc1=findViewById(R.id.recyclerView);
        rdb=new resizeDatabase(this);
        h=new ArrayList<>();
        wc=new ArrayList<>();
        wh=new ArrayList<>();
        sh=new ArrayList<>();
        b=new ArrayList<>();
        hand=new ArrayList<>();
        a=new ArrayList<>();
        w=new ArrayList<>();

        viewordertoresizedetails();
        ca1=new viewResizeDetails(updateTailorResize.this,h,wc,wh,sh,b,hand,a,w);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(updateTailorResize.this));
        //end of recycle view


    }

    public void viewordertoresizedetails(){
        String phonenumber=empid.getText().toString();
        String orderid=oid.getText().toString();
        Cursor c = rdb.viewepecificresize4(orderid);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            h.add(c.getString(3));
            wc.add(c.getString(4));
            wh.add(c.getString(5));
            sh.add(c.getString(6));
            b.add(c.getString(7));
            hand.add(c.getString(8));
            a.add(c.getString(9));
            w.add(c.getString(10));
        }
    }



}