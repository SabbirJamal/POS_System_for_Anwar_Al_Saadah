package com.example.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class newSale extends AppCompatActivity {

    //textviews for autofilling the name and email of cusotmer
    TextView name, email;

    //check boxes for sale selection
    CheckBox cbd, cbm, cbk, cba, cbs, cbmun;

    //quantity textfields
    EditText dq, mq,kq,aq,sq,munq;

    //total amount fields
    TextView dt,mt,kt,at,st,munt;

    //initializing quantity;
    double cost,ctd,ctm,ctk,cta,cts,ctmun;
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
        //finding name and email field of customer
        name=findViewById(R.id.custname);
        email=findViewById(R.id.custemail);


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
    }
}