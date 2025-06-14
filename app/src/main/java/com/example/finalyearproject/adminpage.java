package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class adminpage extends AppCompatActivity {

    CardView ae,inventory,empdetails,gene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //intent code for adding employees
        ae=findViewById(R.id.addemp);
        ae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminpage.this,addemployees.class);
                startActivity(intent);
            }
        });
        //end of intent page to admin

        //intent code for inventory page
        inventory=findViewById(R.id.inven);
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminpage.this,inventory.class);
                startActivity(intent);
            }
        });
        //end of intent to inventory page


        //intent code to viewing employee details
        empdetails=findViewById(R.id.empd);
        empdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminpage.this, viewemployees.class);
                startActivity(intent);
            }
        });

        gene=findViewById(R.id.gr);
        gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminpage.this, ADMIN_GenerateDailyReport.class);
                startActivity(intent);
            }
        });
    }
}