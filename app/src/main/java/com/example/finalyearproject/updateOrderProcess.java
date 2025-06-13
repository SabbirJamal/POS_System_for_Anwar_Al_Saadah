package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class updateOrderProcess extends AppCompatActivity {

    //intended data
    TextView employeename,employeetype,employeeid;

    //fields for getting prvs intend data
    TextView oid,cn,amt,bal,dd,s,advanceamt,empid;

    //DROP DOWN LIST
    Spinner categorySpinner;
    TextView tailor;
    empdatabase empdb;

    //recycleview for remaing data
    orderDatabase odb;
    customAdapter_orderProcess ca1;
    ArrayList<String>phone,cd,ct,h,wc,wh,sh,b,hand,a,w,ai;
    RecyclerView rc1;

    Button f;




    Map<String, String> tailorMap;
    List<String> tailorNames;
    TextView newemployeename,newemployeeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_order_process);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

/*
        //code for drop down list of employee names
        //finding Spinner
        categorySpinner=findViewById(R.id.tailorspinner);
        empdb=new empdatabase(this);

        List<String> tailortype=empdb.getempname();

        ArrayAdapter<String> clothnameadapter=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,tailortype);
        clothnameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(clothnameadapter);



        //tailor.setText(categorySpinner);
        //end of dropdownlist
 */
        newemployeename=findViewById(R.id.newempid);
        newemployeeid=findViewById(R.id.newempname);
        categorySpinner = findViewById(R.id.tailorspinner);
        empdb = new empdatabase(this);

        // Fetch tailor data
        tailorMap = empdb.getTailorNamePhoneMap();
        tailorNames = new ArrayList<>(tailorMap.keySet());

        // Populate spinner with tailor names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tailorNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Handle selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = tailorNames.get(position);
                String selectedPhone = tailorMap.get(selectedName);
                newemployeeid.setText(selectedPhone);

                // Do something with the phone number (e.g., save it or pass via intent)
                Log.d("TAILOR_SELECTED", "Name: " + selectedName + ", Phone: " + selectedPhone);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //code for getting intended data from prvs cardview
        oid=findViewById(R.id.orderID);
        cn=findViewById(R.id.custName);
        amt=findViewById(R.id.totalamt);
        bal=findViewById(R.id.balanceamt);
        dd=findViewById(R.id.deliveryDate);
        s=findViewById(R.id.clothStatus);
        advanceamt=findViewById(R.id.advanceamt);
        empid=findViewById(R.id.employeeID);

        if(getIntent().hasExtra("orid") && getIntent().hasExtra("cn") && getIntent().hasExtra("tamt") && getIntent().hasExtra("bamt") && getIntent().hasExtra("dd") &&
                getIntent().hasExtra("s") && getIntent().hasExtra("aamt") && getIntent().hasExtra("en")) {
            String a1 = getIntent().getStringExtra("orid");
            String a2 = getIntent().getStringExtra("cn");
            String a3 = getIntent().getStringExtra("tamt");
            String a4 = getIntent().getStringExtra("bamt");
            String a5 = getIntent().getStringExtra("dd");
            String a6 = getIntent().getStringExtra("s");
            String a7 = getIntent().getStringExtra("aamt");
            String a8 = getIntent().getStringExtra("en");

            String eid=a8;

            oid.setText(a1);
            cn.setText(a2);
            amt.setText(a3);
            bal.setText(a4);
            dd.setText(a5);
            s.setText(a6);
            advanceamt.setText(a7);
            empid.setText(a8);

            String phonenumber=empid.getText().toString();
        }


            //code for remaining information in recycleview
            rc1=findViewById(R.id.recyclerView);
            odb=new orderDatabase(this);
            phone=new ArrayList<>();
            cd=new ArrayList<>();
            ct=new ArrayList<>();
            h=new ArrayList<>();
            wc=new ArrayList<>();
            wh=new ArrayList<>();
            sh=new ArrayList<>();
            b=new ArrayList<>();
            hand=new ArrayList<>();
            a=new ArrayList<>();
            w=new ArrayList<>();
            ai=new ArrayList<>();



           viewordertocutdetails();
            ca1=new customAdapter_orderProcess(updateOrderProcess.this,phone,cd,ct,h,wc,wh,sh,b,hand,a,w,ai);
            rc1.setAdapter(ca1);
            rc1.setLayoutManager(new LinearLayoutManager(updateOrderProcess.this));
            //end of recycle view



        //updating status
        f=findViewById(R.id.finish);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderID = oid.getText().toString();  // Get Order ID from input
                String tailor = newemployeeid.getText().toString();  // Get tailor from dropdown
                String s = "Stitching";  // Fixed status

                boolean update = odb.updatestatus(orderID, s, tailor);  // Only pass Order_ID, Status, Tailor

                if (update) {
                    Toast.makeText(updateOrderProcess.this, "Data updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(updateOrderProcess.this, "Data not updated", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    public void viewordertocutdetails(){
        String phonenumber=empid.getText().toString();
        String orderid=oid.getText().toString();
        Cursor c = odb.viewepecificorders2(phonenumber,orderid);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            phone.add(c.getString(1));
            cd.add(c.getString(3));
            ct.add(c.getString(4));
            h.add(c.getString(5));
            wc.add(c.getString(6));
            wh.add(c.getString(7));
            sh.add(c.getString(8));
            b.add(c.getString(9));
            hand.add(c.getString(10));
            a.add(c.getString(11));
            w.add(c.getString(12));
            ai.add(c.getString(13));
        }
    }






}