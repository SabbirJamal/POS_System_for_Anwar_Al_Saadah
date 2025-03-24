package com.example.finalyearproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customAdapter2 extends RecyclerView.Adapter<customAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList alsal,alage,alpass;


    customAdapter2 (Context context,ArrayList alsal, ArrayList alage,ArrayList alpass){
        this.context=context;
        this.alsal=alsal;
        this.alage=alage;
        this.alpass=alpass;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.eachempdetails,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        holder.saltxt.setText(String.valueOf(alsal.get(position)));
        holder.agetxt.setText(String.valueOf(alage.get(position)));
        holder.passtxt.setText(String.valueOf(alpass.get(position)));

    }

    @Override
    public int getItemCount() {
        return alsal.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView saltxt,agetxt,passtxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            saltxt=itemView.findViewById(R.id.txtsal);
            agetxt=itemView.findViewById(R.id.txtage);
            passtxt=itemView.findViewById(R.id.txtpass);

        }
    }

    ;
}
