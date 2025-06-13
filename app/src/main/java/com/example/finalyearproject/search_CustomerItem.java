package com.example.finalyearproject;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Date;
import java.util.Locale;

public class search_CustomerItem extends AppCompatActivity {

    //for getting header intent data
    TextView employeename,employeetype,employeeid;
    //for getting current date
    TextView dateTextView;

    RecyclerView rc1;
    Button id;
    EditText number;
    //all order view
    resizeDatabase rdb;

    ArrayList<String>oid,cn,tamt, bamt,dd,s,aamt,en;
    order_to_cut_customAdapter ca2;

    //end of order view


    //all resize view
    //identifying database
    orderDatabase odb;
    //calling the custom adapter
    resize_to_cut_customAdapter ca1;
    ArrayList<String>roid,rcn,rtamt,rdd,rs,ren;

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

        id=findViewById(R.id.searchbyid);

        number=findViewById(R.id.searchEditText);
        rc1=findViewById(R.id.searchRecycleView);
        odb=new orderDatabase(this);


        //codes for orders to cut recycleview
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

        ca2=new order_to_cut_customAdapter(search_CustomerItem.this,oid,cn,tamt,bamt,dd,s,aamt,en);
        rc1.setAdapter(ca2);
        rc1.setLayoutManager(new LinearLayoutManager(search_CustomerItem.this));
        //end of recycleview for orders to cut

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
            bamt.add(c.getString(15));
            dd.add(c.getString(17));
            s.add(c.getString(21));
            aamt.add(c.getString(16));
            en.add(c.getString(19));
        }
    }
}