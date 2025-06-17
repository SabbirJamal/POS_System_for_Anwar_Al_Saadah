package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ADMIN_GenerateReportByDate extends AppCompatActivity {


    RecyclerView recyclerView;
    TextView totalText, startDateTextView, endDateTextView;
    Button generateReportButton;

    ReportAdapter adapter;
    List<ReportModel> reportList = new ArrayList<>();

    orderDatabase orderDB;
    saleDatabase saleDB;
    resizeDatabase resizeDB;

    Calendar calendar = Calendar.getInstance();
    String startDate = "", endDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_generate_report_by_date);

        recyclerView = findViewById(R.id.recyclerView);
        totalText = findViewById(R.id.totalAmountText);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        generateReportButton = findViewById(R.id.generateReportButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DB Helpers
        orderDB = new orderDatabase(this);
        saleDB = new saleDatabase(this);
        resizeDB = new resizeDatabase(this);

        // Set onClickListeners for the date pickers
        startDateTextView.setOnClickListener(view -> selectStartDate());
        endDateTextView.setOnClickListener(view -> selectEndDate());

        // Set onClickListener for the "Generate Report" button
        generateReportButton.setOnClickListener(view -> generateReport());
    }

    private void selectStartDate() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            startDate = sdf.format(calendar.getTime());
            startDateTextView.setText(startDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void selectEndDate() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            endDate = sdf.format(calendar.getTime());
            endDateTextView.setText(endDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void generateReport() {
        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show();
            return;
        }

        // Load the report based on the selected date range
        loadReport(startDate, endDate);
    }

    private void loadReport(String startDate, String endDate) {
        reportList.clear();
        reportList.addAll(orderDB.getOrderReportByDateRange(startDate, endDate));
        reportList.addAll(saleDB.getSaleReportByDateRange(startDate, endDate));
        reportList.addAll(resizeDB.getResizeReportByDateRange(startDate, endDate));

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