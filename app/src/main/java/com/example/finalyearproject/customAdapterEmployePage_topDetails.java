package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customAdapterEmployePage_topDetails extends RecyclerView.Adapter<customAdapterEmployePage_topDetails.MyViewHolder> {

    private Context context;
    private ArrayList empname,emptype,empid;

    customAdapterEmployePage_topDetails (Context context, ArrayList empname, ArrayList emptype,ArrayList empid){
        this.context=context;
        this.empname=empname;
        this.emptype=emptype;
        this.empid=empid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.employee_top_bar,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtempname.setText(String.valueOf(empname.get(position)));
        holder.txtempid.setText(String.valueOf(empid.get(position)));
        holder.txtemptyp.setText(String.valueOf(emptype.get(position)));
    }

    @Override
    public int getItemCount() {
        return empid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtempname,txtemptyp,txtempid;
        CardView cv;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtempname=itemView.findViewById(R.id.fullnametxt);
            txtemptyp=itemView.findViewById(R.id.employeetypetxt);
            txtempid=itemView.findViewById(R.id.employeeidtxt);
            cv=itemView.findViewById(R.id.emptopview);
        }
    }
}
