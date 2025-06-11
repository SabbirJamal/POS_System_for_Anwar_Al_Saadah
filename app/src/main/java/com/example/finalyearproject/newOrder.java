package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    //finding all columns for adding new order
    EditText phn,name,design,h,wc,wh,s,b,hl,arms,waist,adinfo;

    //drop down list
    Spinner itemSpinner;
    inventoryDatabase invdb;

    //order database
    orderDatabase odb;

    //button for order
    Button order;

    TextView employeename,employeetype,employeeid;

    //getting current date
    TextView dateTextView;


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

        //getting current date
        dateTextView = findViewById(R.id.currentdate);

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Set date to text field
        dateTextView.setText(currentDate);

        //end of getting current date

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


        //finding Spinner
        categorySpinner=findViewById(R.id.clothcategorySpinner);

        //initializing database
        ivnDB=new inventoryDatabase(this);

        List<String> clothcategory=ivnDB.getCLothname();

        ArrayAdapter<String> clothnameadapter=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,clothcategory);
        clothnameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(clothnameadapter);
        //end of dropdownlist


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



        //Finding all editextfields for adding
        phn=findViewById(R.id.customerphonenum);
        name=findViewById(R.id.customername);
        design=findViewById(R.id.clothdesignnumber);
        h=findViewById(R.id.heighttxt);
        wc=findViewById(R.id.widthctxt);
        wh=findViewById(R.id.widthhtxt);
        s=findViewById(R.id.shoulderstxt);
        b=findViewById(R.id.backtxt);
        hl=findViewById(R.id.handtxt);
        arms=findViewById(R.id.armstxt);
        waist=findViewById(R.id.waisttxt);
        adinfo=findViewById(R.id.additionalinfottxt);

        //finding order button
        order=findViewById(R.id.placeorder);

        //database call
        odb=new orderDatabase(this);

        //code start for inserting new order
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=phn.getText().toString();
                String n=name.getText().toString();
                String dn=design.getText().toString();
                String clth=categorySpinner.getSelectedItem().toString();
                String he=h.getText().toString();
                String wic=wc.getText().toString();
                String wih=wh.getText().toString();
                String sh=s.getText().toString();
                String ba=b.getText().toString();
                String hal=hl.getText().toString();
                String a=arms.getText().toString();
                String w=waist.getText().toString();
                String ai=adinfo.getText().toString();
                String tamt=tot.getText().toString();
                String ad=adv.getText().toString();;
                String bamt=bal.getText().toString();
                String curd=dateTextView.getText().toString();
                String dd=deliverydate.getText().toString();
                String eid=employeeid.getText().toString();
                String en=employeename.getText().toString();
                String status="Cut";
                String tailor="NULL";
                String isle="NULL";
                boolean insert=odb.addneworder(p,n,dn,clth,he,wic,wih,sh,ba,hal,a,w,ai,tamt,ad,bamt,curd,dd,eid,en,status,tailor,isle);
                if(insert==true)
                {
                    Toast.makeText(newOrder.this,"Order Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(newOrder.this, employeeHomePage.class);
                    intent.putExtra("phn",phn.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(newOrder.this,"Order Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //end of inserting






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