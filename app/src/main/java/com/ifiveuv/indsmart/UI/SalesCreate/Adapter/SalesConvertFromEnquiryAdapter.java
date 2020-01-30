package com.ifiveuv.indsmart.UI.SalesCreate.Adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.SalesCreate.SalesConvertFromEnquiry;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryItemModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class SalesConvertFromEnquiryAdapter extends RecyclerView.Adapter<SalesConvertFromEnquiryAdapter.MyViewHolder> {
    Realm realm;
    private Context context;
    private RealmResults<EnquiryItemModel> cartList;


    public SalesConvertFromEnquiryAdapter(RealmResults<EnquiryItemModel> cartList, SalesConvertFromEnquiry context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public SalesConvertFromEnquiryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.se_order_list_item, parent, false);
        return new SalesConvertFromEnquiryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SalesConvertFromEnquiryAdapter.MyViewHolder holder, final int position) {
        final EnquiryItemModel item = cartList.get(position);

//        holder.so_num.setText(item.getEnqOnlineId ());
//        holder.so_date.setText(item.getEnquiryDate ());
//        holder.cus_name.setText(item.getEnquiryCustomerName ());
//        holder.enq_type.setText(item.getEnquiryType ());
//        holder.status.setText(item.getEnquiryStatus ());
//        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ConvertFromEnquiryToSalesActivity.class);
//                intent.putExtra("hdrid", String.valueOf(item.getEnquiryId ()));
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView so_date, cus_name, enq_type, so_num, status;

        public MyViewHolder(View view) {
            super(view);
            so_num = view.findViewById(R.id.se_num);
            so_date = view.findViewById(R.id.se_date);
            cus_name = view.findViewById(R.id.cus_name);
            enq_type = view.findViewById(R.id.enq_type);
            status = view.findViewById(R.id.status);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


}