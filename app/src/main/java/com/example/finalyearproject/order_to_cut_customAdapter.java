package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class order_to_cut_customAdapter extends RecyclerView.Adapter<order_to_cut_customAdapter.MyViewHolder> {

    private Context context;
    private ArrayList oid,cn,tamt, bamt,dd,s,aamt,en;

    order_to_cut_customAdapter(Context context,ArrayList oid,ArrayList cn,ArrayList tamt,ArrayList bamt,ArrayList dd,ArrayList s,ArrayList aamt,ArrayList en){
        this.context=context;
        this.oid=oid;
        this.cn=cn;
        this.tamt=tamt;
        this.bamt=bamt;
        this.dd=dd;
        this.s=s;
        this.aamt=aamt;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.txtoid.setText(String.valueOf(oid.get(position)));
        holder.txtcn.setText(String.valueOf(cn.get(position)));
        holder.txttamt.setText(String.valueOf(tamt.get(position)));
        holder.txtbamt.setText(String.valueOf(bamt.get(position)));
        holder.txtdd.setText(String.valueOf(dd.get(position)));
        holder.txts.setText(String.valueOf(s.get(position)));
        holder.txtaamt.setText(String.valueOf(aamt.get(position)));
        holder.txten.setText(String.valueOf(en.get(position)));

    }

    @Override
    public int getItemCount() {
        return en.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  txtoid,txtcn,txttamt, txtbamt,txtdd,txts,txtaamt,txten;
        CardView cv;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            txtoid=itemView.findViewById(R.id.orderID);
            txtcn=itemView.findViewById(R.id.custName);
            txttamt=itemView.findViewById(R.id.totalamt);
            txtbamt=itemView.findViewById(R.id.balanceamt);
            txtdd=itemView.findViewById(R.id.deliveryDate);
            txts=itemView.findViewById(R.id.clothStatus);
            txtaamt=itemView.findViewById(R.id.advanceamt);
            txten=itemView.findViewById(R.id.employeeID);
            cv=itemView.findViewById(R.id.CardView1);

        }
    }
}
