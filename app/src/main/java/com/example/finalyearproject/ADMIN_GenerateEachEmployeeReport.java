package com.example.finalyearproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ADMIN_GenerateEachEmployeeReport extends AppCompatActivity {

    //DROP DOWN LIST
    Spinner categorySpinner;
    TextView newEmployeeName, newEmployeeId, ordersCountTextView;
    empdatabase empdb;
    orderDatabase orderDB; // Assuming you have a method to get order counts
    Map<String, String> tailorMap;
    List<String> tailorNames;
    orderDatabase odb;
    resizeDatabase rdb;

    private TextView orderCountTextView,rct,ordercountbydate,resizecountbydate;

    private Button checkOrderCountButton,tailorsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_generate_each_employee_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        newEmployeeName = findViewById(R.id.newempname);
        newEmployeeId = findViewById(R.id.newempid);
        categorySpinner = findViewById(R.id.tailorspinner);

        // Initialize databases
        empdb = new empdatabase(this);
        orderDB = new orderDatabase(this); // Initialize order DB

        // Fetch employee details: names and phone numbers
        tailorMap = empdb.getEmployeeNamePhoneMap();
        tailorNames = new ArrayList<>(tailorMap.keySet());

        // Populate Spinner with employee names (tailors)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tailorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Handle Spinner item selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = tailorNames.get(position);
                String selectedPhone = tailorMap.get(selectedName);

                // Set employee name and phone number
                newEmployeeName.setText(selectedName);
                newEmployeeId.setText(selectedPhone);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        odb=new orderDatabase(this);
        orderCountTextView = findViewById(R.id.ordersCountTextView);
        checkOrderCountButton = findViewById(R.id.salesman);
        rdb=new resizeDatabase(this);
        rct = findViewById(R.id.resizeCountTextView);
        ordercountbydate = findViewById(R.id.orderCountLast30Days);
        resizecountbydate = findViewById(R.id.resizeCountLast30Days);


        // Set up the button click listener
        checkOrderCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Employee ID from the TextView
                String employeeId = newEmployeeId.getText().toString().trim();

                // Check if the employee ID is not empty
                if (!employeeId.isEmpty()) {
                    // Get the count of orders associated with the employee ID
                    int orderCount = odb.getOrderCountByEmployee(employeeId);
                    int resizeCount = rdb.getResizeCountByEmployee(employeeId);
                    int totalOrders = odb.getTotalOrdersInLast30Days(employeeId);
                    int totalResize = rdb.getTotalResizeInLast30Days(employeeId);

                    // Display the result in the TextView
                    orderCountTextView.setText("Total Orders: " + orderCount);
                    rct.setText("Total Resize: " + resizeCount);
                    ordercountbydate.setText("Orders in Last 30 Days: " + totalOrders);
                    resizecountbydate.setText("Resize in Last 30 Days: " + totalResize);
                } else {
                    // Display a message if the employee ID is empty
                    Toast.makeText(ADMIN_GenerateEachEmployeeReport.this, "Please select an Employee", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tailorsearch=findViewById(R.id.tailorSearch);
        tailorsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeId=newEmployeeId.getText().toString().trim();
                // Check if the employee ID is not empty
                if (!employeeId.isEmpty()) {
                    // Get the count of orders associated with the employee ID
                    int orderCount = odb.getOrderCountByTailor(employeeId);
                    int resizeCount = rdb.getResizeCountByTailor(employeeId);
                    int totalOrders = odb.getTailorTotalOrderInLast30Days(employeeId);
                    int totalResize = rdb.getTailorTotalResizeInLast30Days(employeeId);

                    // Display the result in the TextView
                    orderCountTextView.setText("Total Orders: " + orderCount);
                    rct.setText("Total Resize: " + resizeCount);
                    ordercountbydate.setText("Orders in Last 30 Days: " + totalOrders);
                    resizecountbydate.setText("Resize in Last 30 Days: " + totalResize);
                } else {
                    // Display a message if the employee ID is empty
                    Toast.makeText(ADMIN_GenerateEachEmployeeReport.this, "Please select an Employee", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
