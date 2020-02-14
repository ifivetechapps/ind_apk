package com.ifive.indsmart.UI.Sales.SalesEnquiry.Adapter;

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

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryEditActivity;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryList;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryViewActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class SalesEnquiryAllListAdapter extends RecyclerView.Adapter<SalesEnquiryAllListAdapter.MyViewHolder> {
    Realm realm;
    SalesEnquiryList salesEnquiryListl;
    private Context context;
    private RealmResults<EnquiryItemModel> cartList;

    public SalesEnquiryAllListAdapter(Context context,RealmResults<EnquiryItemModel> cartList, SalesEnquiryList salesEnquiryList) {
        this.context = context;
        this.cartList = cartList;
        this.salesEnquiryListl = salesEnquiryList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public SalesEnquiryAllListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.se_order_list_item_all, parent, false);
        return new SalesEnquiryAllListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesEnquiryAllListAdapter.MyViewHolder holder, final int position) {
        final EnquiryItemModel item = cartList.get(position);

        holder.so_num.setText(item.getEnqOnlineId ());
        holder.so_date.setText(item.getEnquiryDate ());
        holder.cus_name.setText(item.getEnquiryCustomerName ());
        holder.del_date.setText(item.getEnquiryType ());
        holder.status.setText(item.getEnquiryStatus ());
        if (item.getEnquiryStatus().equals("Opened")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            holder.sync_data.setVisibility (View.GONE);
        }else{
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            if(item.getStautsOnline ().equals ("1")){
                holder.sync_data.setVisibility (View.GONE);
            }else{
                holder.sync_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (context, "calling method-elsse", Toast.LENGTH_SHORT).show ();
                        salesEnquiryListl.onlineSyncMethodSingle(item.getEnquiryId());

                    }
                });
            }

        }


        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getEnquiryStatus().equals("Opened")) {
                    Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
                    img.setBounds (0, 0, 60, 60);
                    holder.status.setCompoundDrawables (null, null, img, null);
                    Intent intent = new Intent(context, SalesEnquiryEditActivity.class);
                    intent.putExtra("hdrid", String.valueOf(item.getEnquiryId()));
                    intent.putExtra("typeof", "edit");

                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SalesEnquiryViewActivity.class);
                    intent.putExtra("hdrid", String.valueOf(item.getEnquiryId()));
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
        public TextView so_date, cus_name, del_date, so_num, status;
        public Button sync_data;
        public MyViewHolder(View view) {
            super(view);
            so_num = view.findViewById(R.id.se_num);
            so_date = view.findViewById(R.id.se_date);
            cus_name = view.findViewById(R.id.cus_name);
            del_date = view.findViewById(R.id.enq_type);
            sync_data = view.findViewById(R.id.sync_data);
            status = view.findViewById(R.id.status);
            viewForeground = view.findViewById(R.id.viewForeground);

        }
    }
}