package com.ifiveuv.indsmart.UI.Sales.SalesInvoice.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.CreateSalesInvoice;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesInvoiceLineAdapter extends RecyclerView.Adapter<SalesInvoiceLineAdapter.MyViewHolder> {
    CreateSalesInvoice createSalesInvoice;
    RealmList<SalesItemLineList> itemLists;
    String size;
    Realm realm;
    private Context context;

    public SalesInvoiceLineAdapter(Context context, RealmList<SalesItemLineList> cartList, CreateSalesInvoice salesOrderEditActivity) {
        this.context = context;
        this.createSalesInvoice = salesOrderEditActivity;
        this.itemLists = cartList;
    }

    @Override
    public SalesInvoiceLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_sales, parent, false);

        return new SalesInvoiceLineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesInvoiceLineAdapter.MyViewHolder holder, final int mPosition) {
        final SalesItemLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));


        String product = itemList.getProduct();
        holder.product.setText(product);
        RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(itemList.getUomId())).findAll();
        holder.uom.setText(uomModels.get(0).getUom_name());

        holder.ord_qty.setText(String.valueOf(itemList.getQuantity()));
        holder.discount.setText (itemList.getDisPer ());
        holder.inv_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double quantity = 0.0;
                double total = 0;
                double discountper = 0;
                double disamt = 0.0;
                double total_amount=0.0;
                double unit_price=0.0;

                if (s.toString().equals(null) || s.toString().equals("")) {
                    quantity = 0;
                    createSalesInvoice.setAmount(mPosition, quantity );
                } else {
                    quantity = Double.parseDouble(s.toString());
                    unit_price= Double.parseDouble (itemList.getUnitPrice ());
                    discountper= Double.parseDouble (itemList.getDisPer ());

                    total = quantity * unit_price;
                    disamt = ((discountper / 100) * total);
                    total_amount = total - disamt;
                    holder.amount.setText (String.valueOf (total_amount));

                    createSalesInvoice.setAmount(mPosition, quantity );
                    createSalesInvoice.setDiscount(mPosition, discountper,total,disamt,total_amount );

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void removeItem(int position) {
        itemLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SalesItemLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ord_qty,discount, amount;
        public EditText inv_qty;
        public TextView uom, product;
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
            realm = Realm.getDefaultInstance();
        }
    }
}