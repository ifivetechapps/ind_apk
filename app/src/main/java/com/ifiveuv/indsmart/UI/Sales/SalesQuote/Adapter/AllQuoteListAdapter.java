package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter;


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
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.AllQuoteList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.SalesQuoteEditActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.SalesQuoteViewActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class AllQuoteListAdapter extends RecyclerView.Adapter<AllQuoteListAdapter.MyViewHolder> {
    Realm realm;
    AllQuoteList allQuoteList;
    private Context context;
    private RealmResults<QuoteItemList> cartList;


    public AllQuoteListAdapter(Context context, RealmResults<QuoteItemList> cartList, AllQuoteList allQuoteList) {
        this.context = context;
        this.allQuoteList = allQuoteList;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public AllQuoteListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quot_list_all_adapter, parent, false);
        return new AllQuoteListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllQuoteListAdapter.MyViewHolder holder, final int position) {
        final QuoteItemList item = cartList.get(position);

        holder.so_num.setText(item.getOnlineId ());
        holder.so_date.setText(item.getQodate ());
        holder.cus_name.setText(item.getQcus_name ());
        holder.enq_type.setText(item.getQuoteType ());
        holder.status.setText(item.getQstatus ());
        if (item.getQstatus().equals("Opened")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            holder.sync_data.setVisibility (View.GONE);
        }else{
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            if(item.getOnlineStatus ().equals ("1")){
                holder.sync_data.setVisibility (View.GONE);
            }else{
                holder.sync_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (context, "calling method-elsse", Toast.LENGTH_SHORT).show ();
                        allQuoteList.onlineSyncMethodSingle(item.getQuoteItemlist ());

                    }
                });
            }

        }

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQstatus().equals("Opened")) {
                    Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
                    img.setBounds (0, 0, 60, 60);
                    holder.status.setCompoundDrawables (null, null, img, null);
                    Intent intent = new Intent(context, SalesQuoteEditActivity.class);
                    intent.putExtra("hdrid", String.valueOf(item.getQuoteItemlist()));
                    intent.putExtra("typeof", "edit");

                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SalesQuoteViewActivity.class);
                    intent.putExtra("hdrid", String.valueOf(item.getQuoteItemlist()));
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