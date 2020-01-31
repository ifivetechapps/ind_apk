package com.ifiveuv.indsmart.UI.SalesApprove;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.Fragment.SalesQuoteApprove;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.Model.QuoteItemList;

import io.realm.RealmResults;


public class SaleQuoteApproveAdapter extends RecyclerView.Adapter<SaleQuoteApproveAdapter.MyViewHolder> {
    SalesQuoteApprove context;
    RealmResults<QuoteItemList> results;
    SalesQuoteApprove purchaseOrderActivity;


    public SaleQuoteApproveAdapter(SalesQuoteApprove context, RealmResults<QuoteItemList> results, SalesQuoteApprove purchaseOrderActivity) {
        this.context = context;
        this.results = results;
        this.purchaseOrderActivity = purchaseOrderActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_order_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final QuoteItemList item = results.get(position);
        holder.customer_Name.setText(item.getQcus_name());
        holder.poNo.setText("SOQ" + item.getQuoteItemlist());
        holder.total.setText(item.getTotalPrice());
        holder.orderDate.setText(item.getQodate());
        holder.source.setText("Sales Quote");
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("REJECTED", position, item.getQuoteItemlist());
            }
        });
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("APPROVED", position, item.getQuoteItemlist());
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