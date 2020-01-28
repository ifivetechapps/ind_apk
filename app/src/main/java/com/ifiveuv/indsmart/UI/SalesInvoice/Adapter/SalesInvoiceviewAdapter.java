package com.ifiveuv.indsmart.UI.SalesInvoice.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;


import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemLinelist;
import com.ifiveuv.indsmart.UI.SalesInvoice.SalesInvoiceViewActivity;

import io.realm.RealmList;

public class SalesInvoiceviewAdapter extends RecyclerView.Adapter<SalesInvoiceviewAdapter.MyViewHolder> {
    SalesInvoiceViewActivity createSalesInvoice;
    RealmList<InvoiceItemLinelist> itemLists;
    private Context context;


    public SalesInvoiceviewAdapter(Context context, RealmList<InvoiceItemLinelist> cartList, SalesInvoiceViewActivity salesOrderEditActivity) {
        this.context = context;
        this.createSalesInvoice = salesOrderEditActivity;
        this.itemLists = cartList;
    }

    @Override
    public SalesInvoiceviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_sales_view, parent, false);

        return new SalesInvoiceviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesInvoiceviewAdapter.MyViewHolder holder, final int mPosition) {
        final InvoiceItemLinelist itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));

        holder.ord_qty.setText(String.valueOf(itemList.getOrdqty()));
        holder.amount.setText(String.valueOf(itemList.getInvoiceTotal ()));
        holder.discount.setText(String.valueOf(itemList.getInvoiceDis ()));
        holder.product.setText(String.valueOf(itemList.getProduct()));
        holder.uom.setText(String.valueOf(itemList.getUom()));
        holder.inv_qty.setText(itemList.getInvqty());

    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void removeItem(int position) {
        itemLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(InvoiceItemLinelist item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ord_qty, discount, amount, product, uom;
        public TextView inv_qty;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            uom = view.findViewById(R.id.uom);
            ord_qty = view.findViewById(R.id.ord_qty);

            inv_qty = view.findViewById(R.id.inv_qty);
            discount = view.findViewById(R.id.discount);
            amount = view.findViewById(R.id.amount);

        }
    }
}