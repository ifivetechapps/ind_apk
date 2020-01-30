package com.ifiveuv.indsmart.UI.Sales.SalesCreate.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.ConvertFromQuoteToSalesActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.SalesConvertFromQuote;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import io.realm.Realm;
import io.realm.RealmResults;

public class SalesConvertFromQuoteAdapter extends RecyclerView.Adapter<SalesConvertFromQuoteAdapter.MyViewHolder> {
    Realm realm;
    private Context context;
    private RealmResults<QuoteItemList> cartList;


    public SalesConvertFromQuoteAdapter(RealmResults<QuoteItemList> cartList, SalesConvertFromQuote context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public SalesConvertFromQuoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.se_order_list_item, parent, false);
        return new SalesConvertFromQuoteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SalesConvertFromQuoteAdapter.MyViewHolder holder, final int position) {
        final QuoteItemList item = cartList.get(position);

        holder.so_num.setText(item.getOnlineId ());
        holder.so_date.setText(item.getQodate ());
        holder.cus_name.setText(item.getQcus_name ());
        holder.enq_type.setText(item.getQuoteType ());
        holder.status.setText(item.getQstatus ());
        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConvertFromQuoteToSalesActivity.class);
                intent.putExtra("hdrid", String.valueOf(item.getQuoteItemlist ()));
                context.startActivity(intent);
            }
        });

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