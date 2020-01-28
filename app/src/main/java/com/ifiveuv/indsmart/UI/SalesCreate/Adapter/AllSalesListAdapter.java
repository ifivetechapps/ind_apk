package com.ifiveuv.indsmart.UI.SalesCreate.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.SalesCreate.AllSalesList;
import com.ifiveuv.indsmart.UI.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.SalesCreate.SalesOrderEditActivity;
import com.ifiveuv.indsmart.UI.SalesCreate.SalesOrderViewActivity;
import com.ifiveuv.indsmart.UI.SalesQuote.SalesQuoteEditActivity;
import com.ifiveuv.indsmart.UI.SalesQuote.SalesQuoteViewActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class AllSalesListAdapter extends RecyclerView.Adapter<AllSalesListAdapter.MyViewHolder> {
    Realm realm;
    AllSalesList allQuoteList;
    private Context context;
    private RealmResults<SaleItemList> cartList;


    public AllSalesListAdapter(Context context, RealmResults<SaleItemList> cartList, AllSalesList allQuoteList) {
        this.context = context;
        this.allQuoteList = allQuoteList;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public AllSalesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_list_all_adapter, parent, false);
        return new AllSalesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllSalesListAdapter.MyViewHolder holder, final int position) {
        final SaleItemList item = cartList.get(position);

        holder.so_num.setText(item.getOnlineId ());
        holder.so_date.setText(item.getSodate ());
        holder.cus_name.setText(item.getCus_name ());
        holder.enq_type.setText(item.getTypeOfOrder ());
        holder.status.setText(item.getStatus ());
        if (item.getStatus().equals("Opened")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            holder.sync_data.setVisibility (View.GONE);
        }else{
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            if(item.getOnlinestatus ().equals ("1")){
                holder.sync_data.setVisibility (View.GONE);
            }else{
                holder.sync_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (context, "calling method-elsse", Toast.LENGTH_SHORT).show ();
                        allQuoteList.onlineSyncMethodAll(item.getSalesOrderid ());

                    }
                });
            }

        }

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatus ().equals("Opened")) {
                    Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
                    img.setBounds (0, 0, 60, 60);
                    holder.status.setCompoundDrawables (null, null, img, null);
                    Intent intent = new Intent(context, SalesOrderEditActivity.class);

                    intent.putExtra("hdrid", String.valueOf(item.getSalesOrderid()));
                    intent.putExtra("typeof", "edit");

                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SalesOrderViewActivity.class);
                    intent.putExtra("hdrid", String.valueOf(item.getSalesOrderid()));
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public Button sync_data;
        public TextView so_date, cus_name, enq_type, so_num, status;

        public MyViewHolder(View view) {
            super(view);
            so_num = view.findViewById(R.id.se_num);
            so_date = view.findViewById(R.id.se_date);
            cus_name = view.findViewById(R.id.cus_name);
            enq_type = view.findViewById(R.id.enq_type);
            status = view.findViewById(R.id.status);
            sync_data = view.findViewById(R.id.sync_data);
            viewForeground = view.findViewById(R.id.viewForeground);
        }
    }


}