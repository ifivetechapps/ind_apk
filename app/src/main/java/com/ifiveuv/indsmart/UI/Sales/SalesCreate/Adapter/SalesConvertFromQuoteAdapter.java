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
import com.ifiveuv.indsmart.UI.Masters.Model.CustomerList;
import com.ifiveuv.indsmart.UI.Sales.OnlineModel.SoHeaderquoteDetail;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.ConvertFromQuoteToSalesActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.SalesConvertFromQuote;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import io.realm.Realm;
import io.realm.RealmResults;

public class SalesConvertFromQuoteAdapter extends RecyclerView.Adapter<SalesConvertFromQuoteAdapter.MyViewHolder> {
    Realm realm;
    private Context context;
    private RealmResults<SoHeaderquoteDetail> cartList;


    public SalesConvertFromQuoteAdapter(RealmResults<SoHeaderquoteDetail> cartList, SalesConvertFromQuote context) {
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
        final SoHeaderquoteDetail item = cartList.get(position);

        holder.so_num.setText(item.getSalesQuoteNo ());
        holder.so_date.setText(item.getDeliveryDate ());
        CustomerList customer=realm.where (CustomerList.class).equalTo ("cusNo",item.getCustomerId ()).findFirst ();
        holder.cus_name.setText(customer.getCusName ());

        if(item.getTypeId ()==1){
            holder.enq_type.setText ("Standard");
        }else{
            holder.enq_type.setText ("Labour");
        }
        holder.status.setText(item.getApproveStatus ());
        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConvertFromQuoteToSalesActivity.class);
                intent.putExtra("hdrid", String.valueOf(item.getSalesQuoteId ()));
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