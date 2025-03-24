package com.example.finalyearproject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addinventory extends AppCompatActivity {

    final Calendar mycalender =Calendar.getInstance();
    //identifying the fields for displaying company details
    EditText phn;
    TextView comname,comemail,comcontractor;
    inventoryDatabase idb;

    //giving variable for new inventory orders
    EditText iid,d;
    private invencalc ic;
    EditText cname,sname,nrolls,leng,pri,totpri,totleng;
    Button a;
    inventoryDatabase ivdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addinventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        idb=new inventoryDatabase(this);

        //locating and connecting the fields for inventory company names
        phn=findViewById(R.id.cphn);
        comname=findViewById(R.id.cname);
        comemail=findViewById(R.id.cemail);
        comcontractor=findViewById(R.id.ccontractor);


        //locating and connecting the fields for inventory order names
        iid=findViewById(R.id.ioid);
        a=findViewById(R.id.add);
        d=findViewById(R.id.date);

        viewcompany();

        //getting the incencalc class
        ic=new invencalc();

        //code for getting the date
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(addinventory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        mycalender.set(Calendar.YEAR,year);
                        mycalender.set(Calendar.MONTH,month);
                        mycalender.set(Calendar.DAY_OF_MONTH,dayofmonth);

                        String myFormat="dd-MMM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        d.setText(dateFormat.format(mycalender.getTime()));


                        //calculation of inventory
                        String p=pri.getText().toString();
                        String l=leng.getText().toString();
                        String r=nrolls.getText().toString();

                        Double dp=Double.parseDouble(p);
                        Double dl=Double.parseDouble(l);
                        Double dr=Double.parseDouble(r);

                        Double tl=ic.totlengthcalc(dl,dr);
                        Double tp=ic.totpricecalc(dp,dr);

                        totpri.setText(""+tp);
                        totleng.setText(tl+"");
                        totpri.setVisibility(View.VISIBLE);
                        totleng.setVisibility(View.VISIBLE);
                    }
                }, mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //end of selecting date

        //finding the remaining edittextfields
        cname=findViewById(R.id.clothname);
        sname=findViewById(R.id.suppliername);
        nrolls=findViewById(R.id.numberofrolls);
        leng=findViewById(R.id.length);
        pri=findViewById(R.id.price);
        totpri=findViewById(R.id.totalprice);
        totleng=findViewById(R.id.totallength);


        //initializing inventory database
        ivdb=new inventoryDatabase(this);

        adddata();


    }

    public void viewcompany(){
        iid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p=phn.getText().toString();
                Cursor c=idb.inventoryaccsdetails(p);
                if(c.getCount()==0)
                {
                    try {
                        alertdialogforaddacc();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
                StringBuffer sb=new StringBuffer();
                while(c.moveToNext())
                {
                    comname.setText(c.getString(1));
                    comemail.setText(c.getString(2));
                    comcontractor.setText(c.getString(3));
                }
            }
        });
    }

    public void alertdialogforaddacc(){
        AlertDialog.Builder builder=new AlertDialog.Builder(addinventory.this);
        builder.setTitle("ALERT");
        builder.setMessage("The company isn't registered. Please Register the company");
        builder.setCancelable(false); //this code for not allowing the user to cancel the alert box by touching on anywhere of screen
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(addinventory.this, addinventoryaccounts.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(addinventory.this, inventory.class));
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    public void adddata(){
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=iid.getText().toString();
                String pn=phn.getText().toString();
                String cn=cname.getText().toString();
                String sn=sname.getText().toString();
                String r=nrolls.getText().toString();
                String l=leng.getText().toString();
                String p=pri.getText().toString();
                String da=d.getText().toString();
                String tr=totpri.getText().toString();
                String tp=totpri.getText().toString();
                boolean insert=ivdb.addinventory(id,pn,cn,sn,r,l,p,da,tr,tp);
                if(insert==true)
                {
                    Toast.makeText(addinventory.this,"Order sucessfully placed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(addinventory.this,"not placed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}