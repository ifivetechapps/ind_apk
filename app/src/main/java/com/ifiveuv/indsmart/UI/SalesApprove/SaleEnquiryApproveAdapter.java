package com.ifiveuv.indsmart.UI.SalesApprove;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;

import java.util.ArrayList;
import java.util.List;

class SaleEnquiryApproveAdapter extends RecyclerView.Adapter<SaleEnquiryApproveAdapter.MyViewHolder> {
    SalesEnquiryApprove context;
    List<SoEnquiryList> results = new ArrayList<>();
    SalesEnquiryApprove purchaseOrderActivity;


    public SaleEnquiryApproveAdapter(SalesEnquiryApprove context, List<SoEnquiryList> results, SalesEnquiryApprove purchaseOrderActivity) {
        this.context = context;
        this.results = results;
        this.purchaseOrderActivity = purchaseOrderActivity;
    }

    @Override
    public SaleEnquiryApproveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_orderapprove_list, parent, false);

        return new SaleEnquiryApproveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SaleEnquiryApproveAdapter.MyViewHolder holder, final int position) {
        final SoEnquiryList item = results.get(position);
        holder.customer_Name.setText(item.getUsername());
        holder.poNo.setText(item.getSalesEnquiryNo ());
        holder.orderDate.setText(item.getSalesEnquiryDate());
        holder.total.setVisibility(View.INVISIBLE);
        holder.source.setText("Sales");
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("REJECTED", position, item.getSalesEnquiryHdrId ());
            }
        });
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                purchaseOrderActivity.changeStatus("APPROVED", position, item.getSalesEnquiryHdrId());
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