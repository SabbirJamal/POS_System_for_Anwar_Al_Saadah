package com.example.finalyearproject;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ReportModel> reportList;

    public ReportAdapter(List<ReportModel> reportList) {
        this.reportList = reportList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReportModel report = reportList.get(position);
        holder.idText.setText(report.getId());
        holder.amountText.setText("OMR " + report.getAmount());
        holder.dateText.setText(report.getDate());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idText, amountText, dateText;

        public ViewHolder(View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.reportId);
            amountText = itemView.findViewById(R.id.reportAmount);
            dateText = itemView.findViewById(R.id.reportDate);
        }
    }
}





