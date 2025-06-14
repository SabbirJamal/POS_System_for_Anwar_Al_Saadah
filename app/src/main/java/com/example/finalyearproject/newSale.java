package com.example.finalyearproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    TextView ta,r;

    //finding the calc java class
    private salecalculation sc;

    //intent phone number
    String phonenumber;


    //calling database
    empdatabase empdb;

    //header
    TextView employeename,employeetype,employeeid;

    //getting current date
    TextView dateTextView;

    //add new sales in database
    saleDatabase sdb;
    Button confirm;
    EditText cn,custphn;

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

        r=findViewById(R.id.alloutput);


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


                dp.setText("8");
                i1="Dress";

                mp.setText("5");
                i2="Moriolla";


                kp.setText("4");
                i3="Khameez";


                ap.setText("4");
                i4="Anabi";

                sp.setText("3");
                i5="Shaila";

                munp.setText("2");
                i6="Mundeel";


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

                dt.setText("OMR"+c1);
                mt.setText("OMR"+c2);
                kt.setText("OMR"+c3);
                at.setText("OMR"+c4);
                st.setText("OMR"+c5);
                munt.setText("OMR"+c6);

                Double totalamount=c1+c2+c3+c4+c5+c6;
                ta.setText(""+totalamount);
                String dsale=i1+" - Price:-"+damt+" - Quantity:"+ddquan+" - Total Amount:"+c1;
                String msale=i2+" - Price:-"+mamt+" - Quantity:"+dmquan+" - Total Amount:"+c2;
                String ksale=i3+" - Price:-"+kamt+" - Quantity:"+dkquan+" - Total Amount:"+c3;
                String asale=i3+" - Price:-"+aamt+" - Quantity:"+daquan+" - Total Amount:"+c4;
                String ssale=i4+" - Price:-"+samt+" - Quantity:"+dsquan+" - Total Amount:"+c5;
                String munsale=i5+" - Price:-"+munamt+" - Quantity:"+dmunquan+" - Total Amount:"+c6;

                String finalsale=dsale+"\n"+msale+"\n"+ksale+"\n"+asale+"\n"+ssale+"\n"+munsale+"\n"+"Total Payable Amount: "+totalamount;
               // String finalsale=dsale+msale+ksale+asale+ssale+munsale+"Total Payable Amount: "+totalamount;
                r.setText(finalsale);

            }
        });


        employeename=findViewById(R.id.fullnametxt);
        employeetype=findViewById(R.id.employeetypetxt);
        employeeid=findViewById(R.id.employeeidtxt);

        //code for getting intended data
        if(getIntent().hasExtra("ename") && getIntent().hasExtra("etype") && getIntent().hasExtra("eid")) {
            //getting the data from intent
            String n = getIntent().getStringExtra("ename");
            String et = getIntent().getStringExtra("etype");
            String id = getIntent().getStringExtra("eid");

            //setting intended data
            employeename.setText(n);
            employeetype.setText(et);
            employeeid.setText(id);

            Log.d("sample",n+" "+et+" "+id);
        }else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }


        //getting current date
        dateTextView = findViewById(R.id.currentdate);

        // Get current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Set date to text field
        dateTextView.setText(currentDate);

        //end of getting current date

        //addition in sales databse
        sdb=new saleDatabase(this);
        custphn=findViewById(R.id.customerphonenum);
        cn=findViewById(R.id.customername);
        confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=custphn.getText().toString();
                String custn=cn.getText().toString();
                String oi=r.getText().toString();
                String tamt=ta.getText().toString();
                String sd=dateTextView.getText().toString();
                String eid=employeeid.getText().toString();
                String en=employeename.getText().toString();
                boolean insert=sdb.addnewsale(p,custn,tamt,sd,eid,en);
                if(insert==true)
                {
                    Toast.makeText(newSale.this,"Order Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(newSale.this, employeeHomePage.class);
                    intent.putExtra("phn",custphn.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(newSale.this,"Order Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}