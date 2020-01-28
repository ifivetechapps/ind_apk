package com.ifiveuv.indsmart.UI.SalesInvoice.Adapter;

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

import com.ifiveuv.indsmart.UI.SalesCreate.SalesOrderEditActivity;
import com.ifiveuv.indsmart.UI.SalesCreate.SalesOrderViewActivity;
import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemList;
import com.ifiveuv.indsmart.UI.SalesInvoice.SalesInvoiceActivity;
import com.ifiveuv.indsmart.UI.SalesInvoice.SalesInvoiceEditActivity;
import com.ifiveuv.indsmart.UI.SalesInvoice.SalesInvoiceViewActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.MyViewHolder> {
    Realm realm;
    private Context context;
    SalesInvoiceActivity salesInvoiceActivity;

    private RealmResults<InvoiceItemList> cartList;


    public InvoiceListAdapter(Context context,RealmResults<InvoiceItemList> cartList, SalesInvoiceActivity salesInvoiceActivity) {
        this.context = context;
        this.cartList = cartList;
        this.salesInvoiceActivity = salesInvoiceActivity;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public InvoiceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.so_order_list_item, parent, false);
        return new InvoiceListAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final InvoiceListAdapter.MyViewHolder holder, final int position) {
        final InvoiceItemList item = cartList.get (position);

        holder.so_num.setText (item.getOnlineId ());
        holder.so_date.setText (item.getInvDate ());
        holder.cus_name.setText (item.getCus_name ());
        holder.enq_type.setText (item.getTypeOfOrder ());
        holder.status.setText (item.getStatus ());
        if (item.getStatus ().equals ("Opened")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            holder.sync_data.setVisibility (View.GONE);
        } else {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            if (item.getOnlineStatus ().equals ("1")) {
                holder.sync_data.setVisibility (View.GONE);
            } else {
                holder.sync_data.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (context, "calling method-elsse", Toast.LENGTH_SHORT).show ();
                        salesInvoiceActivity.onlineSyncMethodSingle (item.getInvoiceid ());

                    }
                });
            }

        }

        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (item.getStatus ().equals ("Opened")) {
                    Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
                    img.setBounds (0, 0, 60, 60);
                    holder.status.setCompoundDrawables (null, null, img, null);
                    Intent intent = new Intent (context, SalesInvoiceEditActivity.class);

                    intent.putExtra ("hdrid", String.valueOf (item.getInvoiceid ()));
                    intent.putExtra ("typeof", "edit");

                    context.startActivity (intent);
                } else {
                    Intent intent = new Intent (context, SalesInvoiceViewActivity.class);
                    intent.putExtra ("hdrid", String.valueOf (item.getInvoiceid ()));
                    context.startActivity (intent);
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

