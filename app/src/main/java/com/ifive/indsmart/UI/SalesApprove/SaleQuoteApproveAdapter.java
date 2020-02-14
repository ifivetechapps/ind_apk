package com.ifive.indsmart.UI.SalesApprove;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;

import java.util.ArrayList;
import java.util.List;


public class SaleQuoteApproveAdapter extends RecyclerView.Adapter<SaleQuoteApproveAdapter.MyViewHolder> {
    SalesQuoteApprove context;
    List<SoQuoteList> results = new ArrayList<>();
    SalesQuoteApprove purchaseOrderActivity;


    public SaleQuoteApproveAdapter(SalesQuoteApprove context, List<SoQuoteList> results, SalesQuoteApprove purchaseOrderActivity) {
        this.context = context;
        this.results = results;
        this.purchaseOrderActivity = purchaseOrderActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_orderapprove_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SoQuoteList item = results.get(position);
        holder.customer_Name.setText(item.getUsername());
        holder.poNo.setText(item.getSalesQuoteNo());
        holder.total.setText(item.getTotal());
        holder.orderDate.setText(item.getSalesQuoteDate());
        holder.source.setText("Sales Quote");
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("REJECTED", position, item.getSalesQuoteId());
            }
        });
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("APPROVED", position, item.getSalesQuoteId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void removeItem(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewForeground;
        public TextView poNo, customer_Name, orderDate, source, total;
        Button acceptButton, rejectButton;

        public MyViewHolder(View view) {
            super(view);
            poNo = view.findViewById(R.id.so_no);
            customer_Name = view.findViewById(R.id.customer_Name);
            orderDate = view.findViewById(R.id.order_date);
            total = view.findViewById(R.id.total);
            source = view.findViewById(R.id.source);
            acceptButton = view.findViewById(R.id.accept_button);
            rejectButton = view.findViewById(R.id.reject_button);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }
}