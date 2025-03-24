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

public class customAdapter1 extends RecyclerView.Adapter<customAdapter1.MyViewHolder> {

    private Context context;
    private ArrayList alname,alphn,aldj,altype;


    customAdapter1 (Context context,ArrayList alname, ArrayList alphn,ArrayList aldj,ArrayList altype){
        this.context=context;
        this.alname=alname;
        this.alphn=alphn;
        this.aldj=aldj;
        this.altype=altype;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        holder.txtalname.setText(String.valueOf(alname.get(position)));
        holder.txtalphn.setText(String.valueOf(alphn.get(position)));
        holder.txtaldj.setText(String.valueOf(aldj.get(position)));
        holder.txtaltype.setText(String.valueOf(altype.get(position)));

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, advancedempview.class);
                intent.putExtra("iname",String.valueOf(alname.get(position)));
                intent.putExtra("iphn",String.valueOf(alphn.get(position)));
                intent.putExtra("idj",String.valueOf(aldj.get(position)));
                intent.putExtra("itype",String.valueOf(altype.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alphn.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtalname,txtalphn,txtaldj,txtaltype;
        CardView cv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtalname=itemView.findViewById(R.id.txtempname);
            txtalphn=itemView.findViewById(R.id.txtempphn);
            txtaldj=itemView.findViewById(R.id.txtempdj);
            txtaltype=itemView.findViewById(R.id.txtempety);
            cv=itemView.findViewById(R.id.row1);
        }
    }


}
