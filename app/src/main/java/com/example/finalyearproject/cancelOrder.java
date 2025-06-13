package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class cancelOrder extends AppCompatActivity {

    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView;
    ImageView search,home;

    //database
    orderDatabase odb;
    resizeDatabase rdb;

    EditText id;
    Button co,cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cancel_order);
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

        search=findViewById(R.id.imgsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cancelOrder.this, search_CustomerItem.class);
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
                Intent intent=new Intent(cancelOrder.this, employeeHomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                //send phone number data
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        id=findViewById(R.id.receiptID);
        co=findViewById(R.id.cancelOrder);
        cr=findViewById(R.id.cancelResize);


        odb=new orderDatabase(this);
        rdb=new resizeDatabase(this);

        deletecustomerorder();
        deletecustomerresize();
    }
    public void deletecustomerresize(){
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iid=id.getText().toString();
                Integer delete=rdb.deleteResize(iid);
                if(delete>0){
                    Toast.makeText(cancelOrder.this,"Order Cancelled Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(cancelOrder.this,"Couldn't Cancel",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deletecustomerorder(){
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iid=id.getText().toString();
                Integer delete=odb.deleteOrder(iid);
                if(delete>0){
                    Toast.makeText(cancelOrder.this,"Order Cancelled Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(cancelOrder.this,"Couldn't Cancel",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}