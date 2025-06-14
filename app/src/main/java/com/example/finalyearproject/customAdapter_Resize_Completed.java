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

public class customAdapter_Resize_Completed extends RecyclerView.Adapter<customAdapter_Resize_Completed.MyViewHolder> {
    Context context;
    private ArrayList h,wc,wh,s,b,hand,a,w;

    customAdapter_Resize_Completed(Context context,ArrayList h,ArrayList wc,ArrayList wh,ArrayList s,ArrayList b,ArrayList hand,ArrayList a,ArrayList w){
        this.context=context;
        this.h=h;
        this.wc=wc;
        this.wh=wh;
        this.s=s;
        this.b=b;
        this.hand=hand;
        this.a=a;
        this.w=w;;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.view_resize_data,parent,false);
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
    }

    @Override
    public int getItemCount() {
        return h.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txth,txtwc,txtwh,txts,txtb,txthand,txta,txtw;
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
        }
    }
}
