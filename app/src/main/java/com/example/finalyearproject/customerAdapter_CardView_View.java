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

public class customerAdapter_CardView_View extends RecyclerView.Adapter<customerAdapter_CardView_View.MyViewHolder> {
    private Context context;
    private ArrayList oid,cn,tamt,dd,s,en;

    customerAdapter_CardView_View(Context context,ArrayList oid,ArrayList cn,ArrayList tamt,ArrayList dd,ArrayList s,ArrayList en){
        this.context=context;
        this.oid=oid;
        this.cn=cn;
        this.tamt=tamt;
        this.dd=dd;
        this.s=s;
        this.en=en;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.order_to_cut_customadapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtoid.setText(String.valueOf(oid.get(position)));
        holder.txtcn.setText(String.valueOf(cn.get(position)));
        holder.txttamt.setText(String.valueOf(tamt.get(position)));
        holder.txtdd.setText(String.valueOf(dd.get(position)));
        holder.txts.setText(String.valueOf(s.get(position)));
        holder.txten.setText(String.valueOf(en.get(position)));

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,updateOrderStitch.class);
                intent.putExtra("orid",String.valueOf(oid.get(position)));
                intent.putExtra("cn",String.valueOf(cn.get(position)));
                intent.putExtra("tamt",String.valueOf(tamt.get(position)));
                intent.putExtra("dd",String.valueOf(dd.get(position)));
                intent.putExtra("s",String.valueOf(s.get(position)));
                intent.putExtra("en",String.valueOf(en.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return en.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtoid,txtcn,txttamt, txtbamt,txtdd,txts,txtaamt,txten;
        CardView cv;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            txtoid=itemView.findViewById(R.id.orderID);
            txtcn=itemView.findViewById(R.id.custName);
            txttamt=itemView.findViewById(R.id.totalamt);
            txtdd=itemView.findViewById(R.id.deliveryDate);
            txts=itemView.findViewById(R.id.clothStatus);
            txten=itemView.findViewById(R.id.employeeID);
            cv=itemView.findViewById(R.id.CardView1);

        }
    }
}
