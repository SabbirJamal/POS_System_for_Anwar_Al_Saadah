package com.example.finalyearproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ADMIN_GenerateDailyReport extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totalText, dateTextView;
    ReportAdapter adapter;
    List<ReportModel> reportList = new ArrayList<>();

    orderDatabase orderDB;
    saleDatabase saleDB;
    resizeDatabase resizeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_generate_daily_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recyclerView);
        totalText = findViewById(R.id.totalAmountText);
        dateTextView = findViewById(R.id.dateTextView);
        // This TextView holds the selected date


        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Set date to text field
        dateTextView.setText(currentDate);
        //end of getting current date



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DB Helpers
        orderDB = new orderDatabase(this);
        saleDB = new saleDatabase(this);
        resizeDB = new resizeDatabase(this);

        // Get the date from TextView
        String date = dateTextView.getText().toString();

        // Load the report
        loadReport(date);
    }

    private void loadReport(String date) {
        reportList.clear();
        reportList.addAll(orderDB.getOrderReportByDate(date));
        reportList.addAll(saleDB.getSaleReportByDate(date));
        reportList.addAll(resizeDB.getResizeReportByDate(date));

        adapter = new ReportAdapter(reportList);
        recyclerView.setAdapter(adapter);

        // Calculate total
        double total = 0;
        for (ReportModel model : reportList) {
            try {
                total += Double.parseDouble(model.getAmount());
            } catch (NumberFormatException e) {
                // Skip invalid entries
            }
        }
        totalText.setText("Total: OMR " + total);
    }
}