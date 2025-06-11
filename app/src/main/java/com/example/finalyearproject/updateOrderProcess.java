package com.example.finalyearproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class updateOrderProcess extends AppCompatActivity {

    //intended data
    TextView employeename,employeetype,employeeid;

    //fields for getting prvs intend data
    TextView oid,cn,amt,bal,dd,s,advanceamt,empid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_order_process);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //code for getting intended data for header
        employeename=findViewById(R.id.fullnametxt);
        employeetype=findViewById(R.id.employeetypetxt);
        employeeid=findViewById(R.id.employeeidtxt);

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

        //code for getting intended data from prvs cardview
        oid=findViewById(R.id.orderID);
        cn=findViewById(R.id.custName);
        amt=findViewById(R.id.totalamt);
        bal=findViewById(R.id.balanceamt);
        dd=findViewById(R.id.deliveryDate);
        s=findViewById(R.id.clothStatus);
        advanceamt=findViewById(R.id.advanceamt);
        empid=findViewById(R.id.employeeID);

        if(getIntent().hasExtra("orid") && getIntent().hasExtra("cn") && getIntent().hasExtra("tamt") && getIntent().hasExtra("bamt") && getIntent().hasExtra("dd") &&
                getIntent().hasExtra("s") && getIntent().hasExtra("aamt") && getIntent().hasExtra("en")){
            String a1=getIntent().getStringExtra("orid");
            String a2=getIntent().getStringExtra("cn");
            String a3=getIntent().getStringExtra("tamt");
            String a4=getIntent().getStringExtra("bamt");
            String a5=getIntent().getStringExtra("dd");
            String a6=getIntent().getStringExtra("s");
            String a7=getIntent().getStringExtra("aamt");
            String a8=getIntent().getStringExtra("en");

            oid.setText(a1);
            cn.setText(a2);
            amt.setText(a3);
            bal.setText(a4);
            dd.setText(a5);
            s.setText(a6);
            advanceamt.setText(a7);
            empid.setText(a8);


        }
    }
}