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

public class inventory extends AppCompatActivity {

    CardView ai;
    inventoryDatabase idb;
    ArrayList<String> ct,od,sn,oi,cn;
    inven_adapter1 ia1;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //identifying the field
        ai=findViewById(R.id.addinven);

        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(inventory.this, addinventory.class);
                startActivity(intent);
            }
        });

        //identifying the inputs and arraylist
        rv=findViewById(R.id.recyclerView);
        idb=new inventoryDatabase(this);
        ct=new ArrayList<>();
        od=new ArrayList<>();
        sn=new ArrayList<>();
        oi=new ArrayList<>();
        cn=new ArrayList<>();

        storedatainarrays();

        ia1=new inven_adapter1(inventory.this,ct,od,sn,oi,cn);
        rv.setAdapter(ia1);
        rv.setLayoutManager(new LinearLayoutManager(inventory.this));
    }

    //code for retreiving data from database
    public void storedatainarrays(){
        Cursor cursor=idb.allinvenaccdata();
        if (cursor.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                ct.add(cursor.getString(2));
                od.add(cursor.getString(7));
                sn.add(cursor.getString(3));
                oi.add(cursor.getString(0));
                cn.add(cursor.getString(1));
            }
        }

        ImageView b=findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(inventory.this, adminpage.class);
                startActivity(intent);
            }
        });
    }

}