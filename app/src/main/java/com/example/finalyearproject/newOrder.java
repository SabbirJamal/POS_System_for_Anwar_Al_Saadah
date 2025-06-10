package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class newOrder extends AppCompatActivity {

    //creatubg spinner
    Spinner categorySpinner;

    //creating invetory database
    inventoryDatabase ivnDB;

    //for current time and date
    TextView cdate;

    //payment fields
    EditText tot, adv,bal;

    //edittext for delivery date
    EditText deliverydate;
    final Calendar mycalender =Calendar.getInstance();
    String phonenumber;

    //top header part
    RecyclerView rc1;
    customAdapterEmployePage_topDetails ca1;
    ArrayList<String> empname,emptype,empid;


    //calling database
    empdatabase empdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //intended phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }


        //calculation and setting balance amount
        //finding the fields
        tot=findViewById(R.id.totalpayableamount);
        adv=findViewById(R.id.advancepayableamount);
        bal=findViewById(R.id.balancepayableamount);

        //end

        //delivery date setting
        deliverydate=findViewById(R.id.deliveryDate);
        deliverydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(newOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        deliverydate.setText(dateFormat.format(mycalender.getTime()));



                        //calculation of amt
                        String t=tot.getText().toString();
                        Double tot2=Double.parseDouble(t);

                        String a=adv.getText().toString();
                        Double a2=Double.parseDouble(a);

                        Double bal2=tot2-a2;

                        bal.setText(""+bal2);
                        //end of amt calculation
                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        //start of top header coding
        //locating
        rc1=findViewById(R.id.recyclerView);
        empdb=new empdatabase(this);
        empname=new ArrayList<>();
        emptype=new ArrayList<>();
        empid=new ArrayList<>();

        viewheaderdetails();

        ca1=new customAdapterEmployePage_topDetails(newOrder.this,empname,emptype,empid);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(newOrder.this));
        //end of it


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