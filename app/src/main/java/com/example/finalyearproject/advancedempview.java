package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

public class advancedempview extends AppCompatActivity {

    TextView ename,ephn,edj,etype,more;
    String name,phn,dj,type;
    CardView cv;
    empdatabase empdb;

    customAdapter2 ca2;


    RecyclerView rc;
    ArrayList<String> alsal,alage,alpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advancedempview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //identifying the textviews
        ename=findViewById(R.id.txtename);
        ephn=findViewById(R.id.txtephn);
        edj=findViewById(R.id.txtedj);
        etype=findViewById(R.id.txtetype);

        //code for getting the intended data
            if(getIntent().hasExtra("iname") && getIntent().hasExtra("iphn") && getIntent().hasExtra("idj") &&
                    getIntent().hasExtra("itype") ){
                //getting the data from intent
                name=getIntent().getStringExtra("iname");
                phn=getIntent().getStringExtra("iphn");
                dj=getIntent().getStringExtra("idj");
                type=getIntent().getStringExtra("itype");

                //setting the intented data into textviews
                ename.setText(name);
                ephn.setText(phn);
                edj.setText(dj);
                etype.setText(type);
                Log.d("sample",name+" "+phn+" "+dj+" "+type);
            }else{
                Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
    }
            //end of intended data code

        //codes for view remaining employee data

            //identifying the inputs of arraylist
        rc=findViewById(R.id.recyclerView);
        empdb=new empdatabase(this);
        alsal=new ArrayList<>();
        alage=new ArrayList<>();
        alpass=new ArrayList<>();

        ca2=new customAdapter2(this,alsal,alage,alpass);
        rc.setAdapter(ca2);
        rc.setLayoutManager(new LinearLayoutManager(advancedempview.this));

        retreivingremainingdata();

    }

    public void retreivingremainingdata(){
        String uphn=ephn.getText().toString();
        Cursor c=empdb.viewepecificempdata(uphn);
        if(c.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb=new StringBuffer();
        while (c.moveToNext())
        {
            alsal.add(c.getString(4));
            alage.add(c.getString(5));
            alpass.add(c.getString(6));
        }
    }

}