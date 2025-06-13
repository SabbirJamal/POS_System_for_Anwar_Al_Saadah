package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class updateResizeProcess extends AppCompatActivity {

    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView,selectDate;
    final Calendar mycalender =Calendar.getInstance();
    ImageView transfer,home,search;
    TextView newemployeename,newemployeeid;

    Spinner categorySpinner;
    empdatabase empdb;
    Map<String, String> tailorMap;
    List<String> tailorNames;

    TextView oid,cn,amt,dd,s,empid;

    //recycleview for remaing data
    resizeDatabase rdb;
    viewResizeDetails ca1;
    ArrayList<String>h,wc,wh,sh,b,hand,a,w;
    RecyclerView rc1;
    Button f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_resize_process);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        search=findViewById(R.id.imgsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updateResizeProcess.this, search_CustomerItem.class);
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
                Intent intent=new Intent(updateResizeProcess.this, employeeHomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
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

        //code for drop down list of employee names
        //finding Spinner
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
        ca1=new viewResizeDetails(updateResizeProcess.this,h,wc,wh,sh,b,hand,a,w);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(updateResizeProcess.this));
        //end of recycle view

        //updating status
        f=findViewById(R.id.finish);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderID = oid.getText().toString();  // Get Order ID from input
                String tailor = newemployeeid.getText().toString();  // Get tailor from dropdown
                String s = "Stitching";  // Fixed status

                boolean update = rdb.updateresizestatus(orderID, s, tailor);  // Only pass Order_ID, Status, Tailor

                if (update) {
                    Toast.makeText(updateResizeProcess.this, "Data updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(updateResizeProcess.this, "Data not updated", Toast.LENGTH_LONG).show();
                }



            }
        });


    }

    public void viewordertoresizedetails(){
        String phonenumber=empid.getText().toString();
        String orderid=oid.getText().toString();
        Cursor c = rdb.viewepecificresize2(phonenumber,orderid);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            h.add(c.getString(5));
            wc.add(c.getString(6));
            wh.add(c.getString(7));
            sh.add(c.getString(8));
            b.add(c.getString(9));
            hand.add(c.getString(10));
            a.add(c.getString(11));
            w.add(c.getString(12));
        }
    }







}