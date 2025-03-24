package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class inven_adapter1 extends RecyclerView.Adapter<inven_adapter1.MyViewHolder> {

    private Context context;
    private ArrayList ct,od,sn,oi,cn;
    inven_adapter1 (Context context, ArrayList ct, ArrayList od, ArrayList sn, ArrayList oi,ArrayList cn){
        this.context=context;
        this.ct=ct;
        this.od=od;
        this.sn=sn;
        this.oi=oi;
        this.cn=cn;
    }

    @NonNull
    @Override
    public inven_adapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.iv,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull inven_adapter1.MyViewHolder holder, final int position) {
        holder.txtct.setText(String.valueOf(ct.get(position)));
        holder.txtod.setText(String.valueOf(od.get(position)));
        holder.txtsn.setText(String.valueOf(sn.get(position)));
        holder.txtoi.setText(String.valueOf(oi.get(position)));
        holder.txtcn.setText(String.valueOf(cn.get(position)));

   holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, advancedinvenview.class);
                intent.putExtra("comnum",String.valueOf(cn.get(position)));
                intent.putExtra("ioid",String.valueOf(oi.get(position)));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtct,txtod,txtsn,txtoi,txtcn;
        CardView cv;
        public MyViewHolder(View itemView){
            super(itemView);
            txtct=itemView.findViewById(R.id.clothtype);
            txtod=itemView.findViewById(R.id.orderdate);
            txtsn=itemView.findViewById(R.id.suppliername);
            txtoi=itemView.findViewById(R.id.orderid);
            txtcn=itemView.findViewById(R.id.companynaumber);
            cv=itemView.findViewById(R.id.cavi);
        }

    }
}
