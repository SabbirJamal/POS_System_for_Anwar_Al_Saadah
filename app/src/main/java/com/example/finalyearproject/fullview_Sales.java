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

public class fullview_Sales extends RecyclerView.Adapter<fullview_Sales.MyViewHolder> {
    Context context;
    private ArrayList h,wc,wh,s,b,hand,a,w,phn,od,empid,il;

    fullview_Sales(Context context,ArrayList h,ArrayList wc,ArrayList wh,ArrayList s,ArrayList b,ArrayList hand,ArrayList a,ArrayList w,ArrayList phn,ArrayList od,ArrayList empid,ArrayList il){
        this.context=context;
        this.h=h;
        this.wc=wc;
        this.wh=wh;
        this.s=s;
        this.b=b;
        this.hand=hand;
        this.a=a;
        this.w=w;;
        this.phn=phn;
        this.od=od;
        this.empid=empid;
        this.il=il;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.fullview_sales,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txth.setText(String.valueOf(h.get(position)));
        holder.txtwc.setText(String.valueOf(wc.get(position)));
        holder.txtwh.setText(String.valueOf(wh.get(position)));
        holder.txts.setText(String.valueOf(s.get(position)));
        holder.txtb.setText(String.valueOf(b.get(position)));
        holder.txthand.setText(String.valueOf(hand.get(position)));
        holder.txta.setText(String.valueOf(a.get(position)));
        holder.txtw.setText(String.valueOf(w.get(position)));
        holder.txtphn.setText(String.valueOf(phn.get(position)));
        holder.txtod.setText(String.valueOf(od.get(position)));
        holder.txtempid.setText(String.valueOf(empid.get(position)));
        holder.txtli.setText(String.valueOf(il.get(position)));
    }


    @Override
    public int getItemCount() {
        return h.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txth,txtwc,txtwh,txts,txtb,txthand,txta,txtw,txtphn,txtod,txtempid,txtli;
        CardView cv;
        public MyViewHolder(@NonNull View itemView){
            super (itemView);
            txth=itemView.findViewById(R.id.heighttxt);
            txtwc=itemView.findViewById(R.id.widthctxt);
            txtwh=itemView.findViewById(R.id.widthhtxt);
            txts=itemView.findViewById(R.id.shoulderstxt);
            txtb=itemView.findViewById(R.id.backtxt);
            txthand=itemView.findViewById(R.id.handtxt);
            txta=itemView.findViewById(R.id.armstxt);
            txtw=itemView.findViewById(R.id.waisttxt);
            txtphn=itemView.findViewById(R.id.phntxt);
            txtod=itemView.findViewById(R.id.odate);
            txtempid=itemView.findViewById(R.id.ttxt);
            txtli=itemView.findViewById(R.id.itxt);
        }
    }
}
