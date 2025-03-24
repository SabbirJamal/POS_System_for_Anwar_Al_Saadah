package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customAdapter_inventory_comdetails extends RecyclerView.Adapter<customAdapter_inventory_comdetails.MyViewHolder> {
    private Context context;
    private ArrayList cn,ce,conn;

    customAdapter_inventory_comdetails(Context context, ArrayList cn, ArrayList ce, ArrayList conn){
        this.context=context;
        this.cn=cn;
        this.ce=ce;
        this.conn=conn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.inventory_detailed_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.cntxt.setText(String.valueOf(cn.get(position)));
        holder.cetxt.setText(String.valueOf(ce.get(position)));
        holder.conntxt.setText(String.valueOf(conn.get(position)));
    }

    @Override
    public int getItemCount() {
        return cn.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cntxt,cetxt,conntxt;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cntxt=itemView.findViewById(R.id.comname);
            cetxt=itemView.findViewById(R.id.comemail);
            conntxt=itemView.findViewById(R.id.contname);
        }
    }
}
