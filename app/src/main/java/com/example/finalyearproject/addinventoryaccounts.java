package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addinventoryaccounts extends AppCompatActivity {

    EditText phn,name,email,contractor;
    Button add;
    inventoryDatabase idb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addinventoryaccounts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phn=findViewById(R.id.txtphn);
        name=findViewById(R.id.txtcname);
        email=findViewById(R.id.txtcemail);
        contractor=findViewById(R.id.txtccname);
        add=findViewById(R.id.addinvenacc);

        idb=new inventoryDatabase(this);

        insertdata();
    }
    public void insertdata(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aphn=phn.getText().toString();
                String aname=name.getText().toString();
                String aemail=email.getText().toString();
                String acontractor=contractor.getText().toString();

                boolean insert=idb.addnewinvenacc(aphn,aname,aemail,acontractor);
                if(insert==true)
                {
                    Toast.makeText(addinventoryaccounts.this,"New Company has been Added", Toast.LENGTH_SHORT).show();
                    Intent login=new Intent(addinventoryaccounts.this,inventory.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(addinventoryaccounts.this,"Employee is already existing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}