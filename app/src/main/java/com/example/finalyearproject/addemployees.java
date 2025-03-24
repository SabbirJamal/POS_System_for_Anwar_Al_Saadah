package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class addemployees extends AppCompatActivity {

    final Calendar mycalender =Calendar.getInstance();
    EditText ed1;
    EditText name,phn,sal,age,pass,conp;
    TextView etype;
    RadioButton r1,r2;
    Button add;
    empdatabase empd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addemployees);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //date of joining calender view
        ed1=findViewById(R.id.dateofjoining);
        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(addemployees.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        ed1.setText(dateFormat.format(mycalender.getTime()));
                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //end of selecting date

        //code for displaying employee type
        etype=findViewById(R.id.emptype);
        r1=findViewById(R.id.rb1);
        r2=findViewById(R.id.rb2);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etype.setText("Sales_Man");
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etype.setText("Tailor");
            }
        });
        //end of selecting employee type


        //identifying all the fields of input
        name=findViewById(R.id.ntxt);
        phn=findViewById(R.id.phntxt);
        sal=findViewById(R.id.stxt);
        age=findViewById(R.id.atxt);
        pass=findViewById(R.id.pastxt);
        conp=findViewById(R.id.cptxt);

        add=findViewById(R.id.addemp);
        //end of identifying the editexts

        //identifying the database
        empd= new empdatabase(this);
        addnewemployees();
    }

    public void addnewemployees(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empname=name.getText().toString();
                String empphn=phn.getText().toString();
                String empdj=ed1.getText().toString();
                String empty=etype.getText().toString();
                String empsalary=sal.getText().toString();
                String empage=age.getText().toString();
                String emppass=conp.getText().toString();
                boolean insert=empd.addnewemployees(empname,empphn,empdj,empty,empsalary,empage,emppass);
                if(insert==true)
                {
                    Toast.makeText(addemployees.this,"Employee has been added Successfully", Toast.LENGTH_SHORT).show();
                    Intent login=new Intent(addemployees.this, adminpage.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(addemployees.this,"Employee is already existing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}