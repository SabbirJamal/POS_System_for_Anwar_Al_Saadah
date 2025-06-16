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

public class viewTailorAllOrderStitched extends AppCompatActivity {

    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView,selectDate;
    final Calendar mycalender =Calendar.getInstance();
    ImageView menu,home,search;

    //for viewing all orders
    //identifying order database
    orderDatabase odb;
    //calling the custom adapter
    customAdapter_orderANDresizeCardview ca2;
    ArrayList<String> oid,cn,tamt,dd,s,en;
    RecyclerView rc2;


    //search by date


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_tailor_all_order_stitched);
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



        home=findViewById(R.id.imghome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(viewTailorAllOrderStitched.this, employeeHomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        menu=findViewById(R.id.imgmenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(viewTailorAllOrderStitched.this, tailorMENU.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        //codes for orders to cut recycleview
        rc2=findViewById(R.id.viewordewr);
        odb=new orderDatabase(this);

        oid=new ArrayList<>();
        cn=new ArrayList<>();
        tamt=new ArrayList<>();
        dd=new ArrayList<>();
        s=new ArrayList<>();
        en=new ArrayList<>();

        ViewOrderstoStitch();


        ca2=new customAdapter_orderANDresizeCardview(viewTailorAllOrderStitched.this,oid,cn,tamt,dd,s,en);
        rc2.setAdapter(ca2);
        rc2.setLayoutManager(new LinearLayoutManager(viewTailorAllOrderStitched.this));
        //end of recycleview for orders to cut

    }

    public void ViewOrderstoStitch() {
        String phonenumber=employeeid.getText().toString();
        Cursor c = odb.viewallorderstostitchTAILOR(phonenumber);
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
}