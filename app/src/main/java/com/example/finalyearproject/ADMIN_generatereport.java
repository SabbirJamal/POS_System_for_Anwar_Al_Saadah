package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ADMIN_generatereport extends AppCompatActivity {

    CardView dr,reportbydate,empreport,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_generatereport);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dr=findViewById(R.id.dailyreport);
        dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ADMIN_generatereport.this,ADMIN_GenerateDailyReport.class);
                startActivity(intent);
            }
        });

        reportbydate=findViewById(R.id.chhoosedatereport);
        reportbydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ADMIN_generatereport.this, ADMIN_GenerateReportByDate.class);
                startActivity(intent);
            }
        });

        empreport=findViewById(R.id.viewemployeereport);
        empreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ADMIN_generatereport.this, ADMIN_GenerateEachEmployeeReport.class);
                startActivity(intent);
            }
        });

    }
}