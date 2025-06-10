package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        //move to resize page




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


}