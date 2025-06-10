package com.example.finalyearproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class newSale extends AppCompatActivity {

    //textviews for autofilling the name and email of cusotmer
    TextView name, email;

    //check boxes for sale selection
    CheckBox cbd, cbm, cbk, cba, cbs, cbmun;

    //quantity textfields
    EditText dq, mq,kq,aq,sq,munq;

    //total amount fields
    TextView dt,mt,kt,at,st,munt;

    //per item price textfield
    TextView dp,mp,kp,ap,sp,munp;

    //for storing items names
    String i1,i2,i3,i4,i5,i6;

    //button for calculation
    Button c;

    //finding total amt textview
    TextView ta;

    //finding the calc java class
    private salecalculation sc;

    //intent phone number
    String phonenumber;

    //top header part
    RecyclerView rc1;
    customAdapterEmployePage_topDetails ca1;
    ArrayList<String> empname,emptype,empid;


    //calling database
    empdatabase empdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //intended phone number
        if(getIntent().hasExtra("phn")){
            phonenumber=getIntent().getStringExtra("phn");
        }

        //finding name and email field of customer
        name=findViewById(R.id.customername);


        //finding checkboxes
        cbd=findViewById(R.id.cbdress);
        cbm=findViewById(R.id.cbmoriolla);
        cbk=findViewById(R.id.cbkhamez);
        cba=findViewById(R.id.cbanabi);
        cbs=findViewById(R.id.cbshaila);
        cbmun=findViewById(R.id.cbmundeel);

        //finding quantity editextboxes
        dq=findViewById(R.id.dressquantity);
        mq=findViewById(R.id.moriollaquantity);
        kq=findViewById(R.id.khameezquantity);
        aq=findViewById(R.id.anabiquantity);
        sq=findViewById(R.id.shailaquantity);
        munq=findViewById(R.id.mundeelquantity);

        //finding totamt fields
        dt=findViewById(R.id.dresstotamt);
        mt=findViewById(R.id.moriollatotamt);
        kt=findViewById(R.id.khameeztotamt);
        at=findViewById(R.id.anabitotamt);
        st=findViewById(R.id.shailatotamt);
        munt=findViewById(R.id.mundeeltotamt);

        //identifying per item price textfields
        dp=findViewById(R.id.dressprice);
        mp=findViewById(R.id.moriollaprice);
        kp=findViewById(R.id.khameezprice);
        ap=findViewById(R.id.anabiprice);
        sp=findViewById(R.id.shailaprice);
        munp=findViewById(R.id.mundeelprice);

        //finding total amt text view
        ta=findViewById(R.id.totamt);


        sc=new salecalculation();

        cbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dp.setText("8");
                i1="Dress";
            }
        });



        cbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setText("5");
                i2="Moriolla";
            }
        });

        cbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kp.setText("4");
                i3="Khameez";
            }
        });

        cba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ap.setText("4");
                i4="Anabi";
            }
        });

        cbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.setText("3");
                i5="Shaila";
            }
        });

        cbmun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                munp.setText("2");
                i6="Mundeel";
            }
        });

        c=findViewById(R.id.calc);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting the price per item amt
                String damt=dp.getText().toString();
                String mamt=mp.getText().toString();
                String kamt=kp.getText().toString();
                String aamt=ap.getText().toString();
                String samt=sp.getText().toString();
                String munamt=munp.getText().toString();

                //getting quantity per item
                String dquan=dq.getText().toString();
                String mquan=mq.getText().toString();
                String kquan=kq.getText().toString();
                String aquan=aq.getText().toString();
                String squan=sq.getText().toString();
                String munquan=munq.getText().toString();


                //converting price string to double
                Double ddamt=Double.parseDouble(damt);
                Double dmamt=Double.parseDouble(mamt);
                Double dkamt=Double.parseDouble(kamt);
                Double daamt=Double.parseDouble(aamt);
                Double dsamt=Double.parseDouble(samt);
                Double dmunamt=Double.parseDouble(munamt);


                //converting quantity string to double
                Double ddquan=Double.parseDouble(dquan);
                Double dmquan=Double.parseDouble(mquan);
                Double dkquan=Double.parseDouble(kquan);
                Double daquan=Double.parseDouble(aquan);
                Double dsquan=Double.parseDouble(squan);
                Double dmunquan=Double.parseDouble(munquan);

                Double c1=sc.salecalc(ddamt,ddquan);
                Double c2=sc.salecalc(dmamt,dmquan);
                Double c3=sc.salecalc(dkamt,dkquan);
                Double c4=sc.salecalc(daamt,daquan);
                Double c5=sc.salecalc(dsamt,dsquan);
                Double c6=sc.salecalc(dmunamt,dmunquan);

                dt.setText(""+c1);
                mt.setText(""+c2);
                kt.setText(""+c3);
                at.setText(""+c4);
                st.setText(""+c5);
                munt.setText(""+c6);

                Double totalamount=c1+c2+c3+c4+c5+c6;
                ta.setText("OMR "+totalamount);

            }
        });


        //start of top header coding
        //locating
        rc1=findViewById(R.id.recyclerView);
        empdb=new empdatabase(this);
        empname=new ArrayList<>();
        emptype=new ArrayList<>();
        empid=new ArrayList<>();

        viewheaderdetails();

        ca1=new customAdapterEmployePage_topDetails(newSale.this,empname,emptype,empid);
        rc1.setAdapter(ca1);
        rc1.setLayoutManager(new LinearLayoutManager(newSale.this));
        //end of it

    }

    //code for retreving and displaying header details
    public void viewheaderdetails(){
        Cursor c=empdb.viewepecificempdata(phonenumber);
        if(c.getCount()==0){
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
        StringBuffer sb=new StringBuffer();
        while (c.moveToNext())
        {
            empname.add(c.getString(0));
            emptype.add(c.getString(3));
            empid.add(c.getString(1));
        }
    }



}