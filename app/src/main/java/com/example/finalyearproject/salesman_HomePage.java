package com.example.finalyearproject;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

public class salesman_HomePage extends AppCompatActivity {

    //getting header intended data
    TextView employeename,employeetype,employeeid;

    String phonenumber;

    //recycle view of orders to stitch
    RecyclerView rc2;
    //identifying order database
    orderDatabase odb;
    //calling the custom adapter
    order_to_cut_customAdapter ca2;
    ArrayList<String> oid,cn,tamt, bamt,dd,s,aamt,en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salesman_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



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

        //getting intented phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }


        //codes for orders to stitch recycleview
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

        ViewOrderstoStitch();

        ca2=new order_to_cut_customAdapter(salesman_HomePage.this,oid,cn,tamt,bamt,dd,s,aamt,en);
        rc2.setAdapter(ca2);
        rc2.setLayoutManager(new LinearLayoutManager(salesman_HomePage.this));
        //end of recycleview for orders to cut


    }
    public void ViewOrderstoStitch() {
        Cursor c = odb.vieworderstostitch(phonenumber);
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
}