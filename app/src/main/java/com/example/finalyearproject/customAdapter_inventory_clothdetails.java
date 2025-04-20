package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customAdapter_inventory_clothdetails extends RecyclerView.Adapter<customAdapter_inventory_clothdetails.MyViewHolder> {

    private Context context;
    private ArrayList clothn, supn,numr,rl,pr,ddate,totprice,totlength;

    customAdapter_inventory_clothdetails(Context context,ArrayList clothn,ArrayList supn,ArrayList numr,ArrayList rl,ArrayList pr,ArrayList ddate,ArrayList totprice,ArrayList totlength){
        this.context=context;
        this.clothn=clothn;
        this.supn=supn;
        this.numr=numr;
        this.rl=rl;
        this.pr=pr;
        this.ddate=ddate;
        this.totprice=totprice;
        this.totlength=totlength;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ca_view_inven_cloths,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.txtclothn.setText((String.valueOf(clothn.get(position))));
        holder.txtsupn.setText(String.valueOf(supn.get(position)));
        holder.txtnumr.setText(String.valueOf(numr.get(position)));
        holder.txtrl.setText(String.valueOf(rl.get(position)));
        holder.txtpr.setText(String.valueOf(pr.get(position)));
        holder.txtddate.setText(String.valueOf(ddate.get(position)));
        holder.txttotprice.setText(String.valueOf(totprice.get(position)));
        holder.txttotlength.setText(String.valueOf(totlength.get(position)));
    }

    @Override
    public int getItemCount() {
        return clothn.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtclothn, txtsupn, txtnumr, txtrl, txtpr, txtddate, txttotprice, txttotlength;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtclothn=itemView.findViewById(R.id.clothname);
            txtsupn=itemView.findViewById(R.id.supname);
            txtnumr=itemView.findViewById(R.id.nrolls);
            txtrl=itemView.findViewById(R.id.lrolls);
            txtpr=itemView.findViewById(R.id.pproll);
            txtddate=itemView.findViewById(R.id.dd);
            txttotprice=itemView.findViewById(R.id.totp);
            txttotlength=itemView.findViewById(R.id.totl);
        }
    }
}
