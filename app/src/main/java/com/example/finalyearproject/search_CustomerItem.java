package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class search_CustomerItem extends AppCompatActivity {

    //for getting header intent data
    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView;

    RecyclerView rc1;
    Button id,sphonenumber;
    EditText number;
    //all order view
    resizeDatabase rdb;

    ArrayList<String>oid,cn,tamt, bamt,dd,s,aamt,en;
    customAdapter_orderANDresizeCardview ca2;

    //end of order view


    //all resize view
    //identifying database
    orderDatabase odb;
    //calling the custom adapter
    resize_to_cut_customAdapter ca1;
    ArrayList<String>roid,rcn,rtamt,rdd,rs,ren;

    ImageView menu,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_customer_item);
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
                Intent intent=new Intent(search_CustomerItem.this, employeeHomePage.class);
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
                Intent intent=new Intent(search_CustomerItem.this, employeeHomePage.class);
                intent.putExtra("ename",employeename.getText().toString());
                intent.putExtra("etype",employeetype.getText().toString());
                intent.putExtra("eid",employeeid.getText().toString());
                //send phone number data
                intent.putExtra("phn",employeeid.getText().toString());
                startActivity(intent);
            }
        });

        number=findViewById(R.id.searchEditText);
        rc1=findViewById(R.id.searchRecycleView);
        odb=new orderDatabase(this);


        //codes for orders to cut recycleview
        odb=new orderDatabase(this);

        oid=new ArrayList<>();
        cn=new ArrayList<>();
        tamt=new ArrayList<>();
        dd=new ArrayList<>();
        s=new ArrayList<>();
        en=new ArrayList<>();

        ViewOrderstoCut();

        ca2=new customAdapter_orderANDresizeCardview(search_CustomerItem.this,oid,cn,tamt,dd,s,en);
        rc1.setAdapter(ca2);
        rc1.setLayoutManager(new LinearLayoutManager(search_CustomerItem.this));
        //end of recycleview for orders to cut

        //view orders by id
        id=findViewById(R.id.searchbyid);
        viewspecificorderbyid();

        sphonenumber=findViewById(R.id.searchbyphonenumber);
        viewspecificorderbyphone();

    }

    public void ViewOrderstoCut() {
        Cursor c = odb.viewallorders();
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

        rdb=new resizeDatabase(this);
    }

    public void viewspecificorderbyphone() {
        sphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = number.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(search_CustomerItem.this, "Enter a Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor c = rdb.viewresizedatabyPhone(phone);
                if (c.getCount() == 0) {
                    viewcustomerdetails("Error", "Nothing Found");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                try {
                    while (c.moveToNext()) {
                        sb.append("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
                        sb.append("Order ID         :- ").append(c.getString(0)).append("\n");
                        sb.append("Phone Number     :- ").append(c.getString(1)).append("\n");
                        sb.append("Customer Name    :- ").append(c.getString(2)).append("\n");
                        sb.append("Total Payable    :- ").append(c.getString(11)).append("\n");
                        sb.append("Order Date       :- ").append(c.getString(12)).append("\n");
                        sb.append("Delivery Date    :- ").append(c.getString(13)).append("\n");
                        sb.append("Employee Name    :- ").append(c.getString(15)).append("\n");
                        sb.append("Status           :- ").append(c.getString(16)).append("\n");
                        sb.append("Tailor ID        :- ").append(c.getString(17)).append("\n");
                        sb.append("Isle Location    :- ").append(c.getString(18)).append("\n");
                        sb.append("---------------------------------------\n");
                    }
                    viewcustomerdetails("Order Details", sb.toString());
                } catch (Exception e) {
                    Toast.makeText(search_CustomerItem.this, "Error reading data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void viewspecificorderbyid() {
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iid = number.getText().toString().trim();
                if (iid.isEmpty()) {
                    Toast.makeText(search_CustomerItem.this, "Enter a Resize ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor c = rdb.viewresizedatabyID(iid);
                if (c.getCount() == 0) {
                    viewcustomerdetails("Error", "Nothing Found");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                try {
                    while (c.moveToNext()) {
                        sb.append("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-\n");
                        sb.append("Order ID         :- ").append(c.getString(0)).append("\n");
                        sb.append("Phone Number     :- ").append(c.getString(1)).append("\n");
                        sb.append("Customer Name    :- ").append(c.getString(2)).append("\n");
                        sb.append("Total Payable    :- ").append(c.getString(11)).append("\n");
                        sb.append("Order Date       :- ").append(c.getString(12)).append("\n");
                        sb.append("Delivery Date    :- ").append(c.getString(13)).append("\n");
                        sb.append("Employee Name    :- ").append(c.getString(15)).append("\n");
                        sb.append("Status           :- ").append(c.getString(16)).append("\n");
                        sb.append("Tailor ID        :- ").append(c.getString(17)).append("\n");
                        sb.append("Isle Location    :- ").append(c.getString(18)).append("\n");
                        sb.append("---------------------------------------\n");
                    }
                    viewcustomerdetails("Order Details", sb.toString());
                } catch (Exception e) {
                    Toast.makeText(search_CustomerItem.this, "Error reading data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewcustomerdetails(String title,String mes){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setCancelable(true);
        ad.setTitle(title);
        ad.setMessage(mes);
        ad.show();
    }
}