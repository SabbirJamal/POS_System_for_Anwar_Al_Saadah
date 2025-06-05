package com.example.finalyearproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class newOrder extends AppCompatActivity {

    //creatubg spinner
    Spinner categorySpinner;

    //creating invetory database
    inventoryDatabase ivnDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //finding Spinner
        categorySpinner=findViewById(R.id.clothcategorySpinner);

        //initializing database
        ivnDB=new inventoryDatabase(this);

        List<String> clothcategory=ivnDB.getCLothname();

        ArrayAdapter<String> clothnameadapter=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,clothcategory);
        clothnameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(clothnameadapter);

    }
}