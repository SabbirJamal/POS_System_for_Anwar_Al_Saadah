package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class newOrder extends AppCompatActivity {

    //creatubg spinner
    Spinner categorySpinner;

    //creating invetory database
    inventoryDatabase ivnDB;

    //for current time and date
    TextView cdate;

    //payment fields
    EditText tot, adv,bal;

    //edittext for delivery date
    EditText deliverydate;
    final Calendar mycalender =Calendar.getInstance();

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

        //end of drop down list for cloth selection


        //start of current date and time
        //finding the fields
        cdate=findViewById(R.id.currentdate);

        // Get current date and time
        Date currentDate = new Date();

        // Format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        // Set to EditTexts
        cdate.setText(dateFormat.format(currentDate));
        String  ct=timeFormat.format(currentDate);
        //end of current time and date



        //calculation and setting balance amount
        //finding the fields
        tot=findViewById(R.id.totalpayableamount);
        adv=findViewById(R.id.advancepayableamount);
        bal=findViewById(R.id.balancepayableamount);

        //end

        //delivery date setting
        deliverydate=findViewById(R.id.deliveryDate);
        deliverydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(newOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        deliverydate.setText(dateFormat.format(mycalender.getTime()));



                        //calculation of amt
                        String t=tot.getText().toString();
                        Double tot2=Double.parseDouble(t);

                        String a=adv.getText().toString();
                        Double a2=Double.parseDouble(a);

                        Double bal2=tot2-a2;

                        bal.setText(""+bal2);
                        //end of amt calculation
                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
}