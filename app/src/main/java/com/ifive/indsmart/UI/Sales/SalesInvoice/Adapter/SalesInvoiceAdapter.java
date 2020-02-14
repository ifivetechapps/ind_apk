package com.ifive.indsmart.UI.Sales.SalesInvoice.Adapter;


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

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Masters.Model.CustomerList;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoHeaderTable;
import com.ifive.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifive.indsmart.UI.Sales.SalesInvoice.CreateSalesInvoice;
import com.ifive.indsmart.UI.Sales.SalesInvoice.SalesOrdertoInvoice;

import io.realm.Realm;
import io.realm.RealmResults;


public class SalesInvoiceAdapter extends RecyclerView.Adapter<SalesInvoiceAdapter.MyViewHolder> {
    Realm realm;
    private Context context;
    private RealmResults<SoHeaderTable> cartList;


    public SalesInvoiceAdapter(RealmResults<SoHeaderTable> cartList, SalesOrdertoInvoice context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public SalesInvoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.so_order_list_item, parent, false);
        return new SalesInvoiceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesInvoiceAdapter.MyViewHolder holder, final int position) {
        final SoHeaderTable item = cartList.get(position);

        holder.so_num.setText(item.getSalesOrderNo ());
        holder.so_date.setText(item.getSalesOrderDate ());
        holder.sync_data.setVisibility (View.GONE);
        if(item.getTypeId ()==1){
            holder.enq_type.setText ("Standard");
        }else{
            holder.enq_type.setText ("Labour");
        }
        CustomerList customer=realm.where (CustomerList.class).equalTo ("cusNo",item.getCustomerId ()).findFirst ();
        holder.cus_name.setText(customer.getCusName ());
        holder.status.setText(item.getApproveStatus ());
        Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
        img.setBounds (0, 0, 60, 60);
        holder.status.setCompoundDrawables (null, null, img, null);
        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(context, CreateSalesInvoice.class);
                    intent.putExtra("hdrid", String.valueOf(item.getSalesOrderHdrId ()));
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

