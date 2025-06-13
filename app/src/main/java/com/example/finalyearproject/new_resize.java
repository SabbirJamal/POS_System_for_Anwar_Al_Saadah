package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class new_resize extends AppCompatActivity {

    String phonenumber;

    //getting current date
    TextView dateTextView,deliverydate;
    final Calendar mycalender =Calendar.getInstance();


    //calling database
    empdatabase empdb;

    //header
    TextView employeename,employeetype,employeeid;

    //finding all columns for adding new resize
    EditText phn,name,h,wc,wh,s,b,hl,arms,waist,tot;

    //calling database
    resizeDatabase rdb;
    Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_resize);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //intended phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }


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

        //getting new date
        deliverydate=findViewById(R.id.deliveryDate);
        deliverydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(new_resize.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        deliverydate.setText(dateFormat.format(mycalender.getTime()));

                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


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
        h=findViewById(R.id.heighttxt);
        wc=findViewById(R.id.widthctxt);
        wh=findViewById(R.id.widthhtxt);
        s=findViewById(R.id.shoulderstxt);
        b=findViewById(R.id.backtxt);
        hl=findViewById(R.id.handtxt);
        arms=findViewById(R.id.armstxt);
        waist=findViewById(R.id.waisttxt);
        tot=findViewById(R.id.totalamount);

        rdb=new resizeDatabase(this);

        confirm=findViewById(R.id.confirmresize);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=phn.getText().toString();
                String n=name.getText().toString();
                String he=h.getText().toString();
                String wic=wc.getText().toString();
                String wih=wh.getText().toString();
                String sh=s.getText().toString();
                String ba=b.getText().toString();
                String hal=hl.getText().toString();
                String a=arms.getText().toString();
                String w=waist.getText().toString();
                String tamt=tot.getText().toString();
                String curd=dateTextView.getText().toString();
                String dd=deliverydate.getText().toString();
                String eid=employeeid.getText().toString();
                String en=employeename.getText().toString();
                String status="Cut";
                String tailor="NULL";
                String isle="NULL";
                boolean insert=rdb.addnewresize(p,n,he,wic,wih,sh,ba,hal,a,w,tamt,curd,dd,eid,en,status,tailor,isle);
                if(insert==true)
                {
                    Toast.makeText(new_resize.this,"Order Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(new_resize.this, employeeHomePage.class);
                    intent.putExtra("phn",employeeid.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(new_resize.this,"Order Fail", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }




}