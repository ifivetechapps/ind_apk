package com.ifiveuv.indsmart.UI.SalesApprove;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.Fragment.SalesOrderApprove;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.Model.SalesItemList;

import io.realm.RealmResults;


public class SaleApproveAdapter extends RecyclerView.Adapter<SaleApproveAdapter.MyViewHolder> {
    SalesOrderApprove context;
    RealmResults<SalesItemList> results;
    SalesOrderApprove purchaseOrderActivity;


    public SaleApproveAdapter(SalesOrderApprove context, RealmResults<SalesItemList> results, SalesOrderApprove purchaseOrderActivity) {
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
        final SalesItemList item = results.get(position);
        holder.customer_Name.setText(item.getCus_name());
        holder.poNo.setText("SO" + item.getSalesorderId());
        holder.orderDate.setText(item.getSodate());
        holder.total.setText(item.getTotalPrice());
        holder.source.setText("Sales");
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("REJECTED", position, item.getSalesorderId());
            }
        });
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("APPROVED", position, item.getSalesorderId());
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
            source = view.findViewById(R.id.source);
            total = view.findViewById(R.id.total);
            acceptButton = view.findViewById(R.id.accept_button);
            rejectButton = view.findViewById(R.id.reject_button);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }
}