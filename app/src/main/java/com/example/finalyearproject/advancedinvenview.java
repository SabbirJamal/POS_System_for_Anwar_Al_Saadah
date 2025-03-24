package com.example.finalyearproject;

import android.database.Cursor;
import android.os.Bundle;
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

//working one
public class advancedinvenview extends AppCompatActivity {

    TextView o,c;
    String orderid,companynumber;


    //customAdapter for inventory accounts
    String num,nam,em;
    customAdapter_inventory_comdetails ca1;
    ArrayList<String> cn,ce,conn;
    RecyclerView rc1;


    //database
    inventoryDatabase idb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advancedinvenview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //identifying orderid and company number for getting the infos
        o=findViewById(R.id.od);
        c=findViewById(R.id.cnn);

        //codes for getting intended data
        if(getIntent().hasExtra("comnum") && getIntent().hasExtra("ioid"))
        {
            //codes for getting the data
            orderid=getIntent().getStringExtra("ioid");
            companynumber=getIntent().getStringExtra("comnum");

            //setting intent data to textviews
            o.setText(orderid);
            c.setText(companynumber);
        }

        //customAdapter Inventory Company Details
        //identification
        rc1=findViewById(R.id.recyclerView1);
        idb=new inventoryDatabase(this);
        cn=new ArrayList<>();
        ce=new ArrayList<>();
        conn=new ArrayList<>();

        ca1=new customAdapter_inventory_comdetails(this,cn,ce,conn);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(advancedinvenview.this));

        retrievingcomdetails();

    }

    public void retrievingcomdetails() {
        String uphn1 = c.getText().toString();
        Cursor c = idb.inventoryaccsdetails(uphn1);
        if(c.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb=new StringBuffer();
        while (c.moveToNext()){
            cn.add(c.getString(1));
            ce.add(c.getString(2));
            conn.add(c.getString(3));
        }
    }
}