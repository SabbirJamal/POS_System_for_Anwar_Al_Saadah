package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class employeeHomePage extends AppCompatActivity {

    //finding intent to order,sale and resize
    Button sale,order,resize;

    //finding field for getting intended data
    TextView phn;
    String phonenumber;


    //top header part
    RecyclerView rc1;
    customAdapterEmployePage_topDetails ca1;
    ArrayList<String> empname,emptype,empid;


    //calling database
    empdatabase empdb;

    //header fields
    TextView employeename,employeetype,employeeid;


    //recycle view of orders to cut
    RecyclerView rc2;
    //identifying order database
    orderDatabase odb;
    //calling the custom adapter
    order_to_cut_customAdapter ca2;
    ArrayList<String>oid,cn,tamt, bamt,dd,s,aamt,en;


    //recycle view of resize to cut
    RecyclerView rc3;
    //identifying order database
    resizeDatabase rdb;
    //calling the custom adapter
    resize_to_cut_customAdapter ca3;
    ArrayList<String>roid,rcn,rtamt,rdd,rs,ren;

    TextView ename,eid,etype;

    ImageView search;
    TextView intentename,intenteid,intentetype;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting intented phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }



        //getting intented phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }

        //intended data from order
        ename=findViewById(R.id.intentfullnametxt);
        eid=findViewById(R.id.intentemployeeidtxt);
        etype=findViewById(R.id.intentemployeetypetxt);




        //start of top header coding
        //locating
        rc1=findViewById(R.id.recyclerView);
        empdb=new empdatabase(this);
        empname=new ArrayList<>();
        emptype=new ArrayList<>();
        empid=new ArrayList<>();

        viewheaderdetails();

        ca1=new customAdapterEmployePage_topDetails(employeeHomePage.this,empname,emptype,empid);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(employeeHomePage.this));




        employeename=findViewById(R.id.fullnametxt);
        employeetype=findViewById(R.id.employeetypetxt);
        employeeid=findViewById(R.id.employeeidtxt);



        //codes for orders to cut recycleview
        rc2=findViewById(R.id.orderrecyclerView);
        odb=new orderDatabase(this);

        oid=new ArrayList<>();
        cn=new ArrayList<>();
        tamt=new ArrayList<>();
        bamt=new ArrayList<>();
        dd=new ArrayList<>();
        s=new ArrayList<>();
        aamt=new ArrayList<>();
        en=new ArrayList<>();

        ViewOrderstoCut();

        ca2=new order_to_cut_customAdapter(employeeHomePage.this,oid,cn,tamt,bamt,dd,s,aamt,en);
        rc2.setAdapter(ca2);
        rc2.setLayoutManager(new LinearLayoutManager(employeeHomePage.this));
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

        ViewResizetoCut();

        ca3=new resize_to_cut_customAdapter(employeeHomePage.this,roid,rcn,rtamt,rdd,rs,ren);
        rc3.setAdapter(ca3);
        rc3.setLayoutManager(new LinearLayoutManager(employeeHomePage.this));
        //end of recycleview for resize to cut

        employeename=findViewById(R.id.intentfullnametxt);
        employeetype=findViewById(R.id.intentemployeetypetxt);
        employeeid=findViewById(R.id.intentemployeeidtxt);
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

        search=findViewById(R.id.imgsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(employeeHomePage.this, search_CustomerItem.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                startActivity(intent);
            }
        });


    }


    //code for retreving and displaying header details
    public void viewheaderdetails(){
        Cursor c=empdb.viewepecificempdata(phonenumber);
        if(c.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb=new StringBuffer();
        while (c.moveToNext())
        {
            empname.add(c.getString(0));
            emptype.add(c.getString(3));
            empid.add(c.getString(1));

        }
    }

    public void ViewOrderstoCut() {
       Cursor c = odb.viewepecificorders(phonenumber);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb = new StringBuffer();
        while (c.moveToNext()) {
            oid.add(c.getString(0));
            cn.add(c.getString(2));
            tamt.add(c.getString(14));
            bamt.add(c.getString(15));
            dd.add(c.getString(17));
            s.add(c.getString(21));
            aamt.add(c.getString(16));
            en.add(c.getString(19));
        }
    }

    public void ViewResizetoCut() {
        Cursor c = rdb.viewepecificresize(phonenumber);
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