package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class viewemployees extends AppCompatActivity {

    RecyclerView recyclerView;
    empdatabase empdb;
    ArrayList<String> alname,alphn,aldj,altype;
    customAdapter1 ca1;

    CardView removeemp,updateemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewemployees);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //identifying the inputs and arraylist
        recyclerView=findViewById(R.id.recyclerView);
        empdb=new empdatabase(this);
        alname=new ArrayList<>();
        alphn=new ArrayList<>();
        aldj=new ArrayList<>();
        altype=new ArrayList<>();

        storedatainarrays();

        ca1=new customAdapter1(viewemployees.this,alname,alphn,aldj,altype);
        recyclerView.setAdapter(ca1);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewemployees.this));

        removeemp=findViewById(R.id.removeemployee);
        removeemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(viewemployees.this, ADMIN_removeEmployee.class);
                startActivity(intent);
            }
        });

        updateemp=findViewById(R.id.updateSalary);
        updateemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(viewemployees.this, ADMIN_updateEmployeeSalary.class);
                startActivity(intent);
            }
        });

    }
    //code for retreiving all data
    public void storedatainarrays(){
        Cursor cursor= empdb.allempdata();
        if (cursor.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                alname.add(cursor.getString(0));
                alphn.add(cursor.getString(1));
                aldj.add(cursor.getString(2));
                altype.add(cursor.getString(3));
            }
        }

        ImageView b=findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(viewemployees.this, adminpage.class);
                startActivity(intent);
            }
        });
    }
}