package com.example.finalyearproject;

import android.content.Intent;
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

public class ADMIN_removeEmployee extends AppCompatActivity {

    //DROP DOWN LIST
    Spinner categorySpinner;
    TextView tailor;
    empdatabase empdb;
    Map<String, String> tailorMap;
    List<String> tailorNames;

    TextView newemployeename,newemployeeid;

    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_remove_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //code for drop down list of employee names
        //finding Spinner
        newemployeeid=findViewById(R.id.newempname);
        categorySpinner = findViewById(R.id.tailorspinner);
        empdb = new empdatabase(this);

        // Fetch tailor data
        tailorMap = empdb.getTailorNamePhoneMap();
        tailorNames = new ArrayList<>(tailorMap.keySet());

        // Populate spinner with tailor names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tailorNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Handle selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = tailorNames.get(position);
                String selectedPhone = tailorMap.get(selectedName);
                newemployeeid.setText(selectedPhone);

                // Do something with the phone number (e.g., save it or pass via intent)
                Log.d("TAILOR_SELECTED", "Name: " + selectedName + ", Phone: " + selectedPhone);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        delete=findViewById(R.id.removeemplyee);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iid=newemployeeid.getText().toString();
                Integer delete=empdb.deletemployee(iid);
                if(delete>0){
                    Toast.makeText(ADMIN_removeEmployee.this,"Order Cancelled Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ADMIN_removeEmployee.this,viewemployees.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ADMIN_removeEmployee.this,"Couldn't Cancel",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}