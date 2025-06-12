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

public class MainActivity extends AppCompatActivity {

    Button log,log2;

    EditText phn,pass;
    empdatabase empdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        log2=findViewById(R.id.employee);

        phn=findViewById(R.id.phntxt);
        pass=findViewById(R.id.pswdtxt);
        log=findViewById(R.id.login);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send phone number data
                String id=phn.getText().toString();
                String p=pass.getText().toString();
                if (id.equals("admin") && p.equals("admin"))
                {
                    Intent intent=new Intent(MainActivity.this, adminpage.class);
                    startActivity(intent);
                }
                else
                {
                    try{
                        String rp=empdb.getpasssalesman(id);
                        if (p.equals(rp))
                        {
                            Toast.makeText(MainActivity.this,"WELCOME",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this, employeeHomePage.class);
                            //send phone number data
                            intent.putExtra("phn",phn.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }

                try{
                    String rp=empdb.getpasstailor(id);
                    if (p.equals(rp))
                    {
                        Toast.makeText(MainActivity.this,"WELCOME",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this, newOrder.class);
                        //send phone number data
                        intent.putExtra("phn",phn.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });



        empdb=new empdatabase(this);


        log2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, adminpage.class);
                //send phone number data
                intent.putExtra("phn",phn.getText().toString());
                startActivity(intent);

            }
        });



    }
}

