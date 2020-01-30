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

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.Model.InvoiceItemLinelist;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.SalesInvoiceEditActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class SalesInvoiceEditAdapter extends RecyclerView.Adapter<SalesInvoiceEditAdapter.MyViewHolder> {
    SalesInvoiceEditActivity salesInvoiceEditActivity;
    RealmList<InvoiceItemLinelist> itemLists;
    List<Products> productsList;
    String size;
    Realm realm;
    private Context context;

    public SalesInvoiceEditAdapter(Context context, RealmList<InvoiceItemLinelist> cartList, List<Products> products, SalesInvoiceEditActivity salesOrderEditActivity) {
        this.context = context;
        this.salesInvoiceEditActivity = salesOrderEditActivity;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public SalesInvoiceEditAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_sales_edit, parent, false);

        return new SalesInvoiceEditAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesInvoiceEditAdapter.MyViewHolder holder, final int mPosition) {
        final InvoiceItemLinelist itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));

        holder.amount.setText(itemList.getInvoiceTotal ());
        String product = itemList.getProduct();
        holder.product.setText(product);
        holder.uom.setText(itemList.getUom ());

        holder.ord_qty.setText(String.valueOf(itemList.getOrdqty ()));
        if(itemList.getInvqty ()==null){
            holder.inv_qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double quantity = 0;
                    double total = 0;


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        quantity = 0;
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setAmount(mPosition, quantity );
                        realm.commitTransaction ();
                    } else {
                        quantity = Double.parseDouble(s.toString());

                        Log.e("345r3", String.valueOf(total));

                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setAmount(mPosition, quantity );
                        realm.commitTransaction ();
                    }

                }
            });

        }else{
            holder.inv_qty.setText (itemList.getInvqty ());
            holder.inv_qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double quantity = 0;
                    double total = 0;


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        quantity = 0;
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setAmount(mPosition, quantity );
                        realm.commitTransaction ();
                    } else {
                        quantity = Double.parseDouble(s.toString());
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setAmount(mPosition, quantity );
                        realm.commitTransaction ();
                    }

                }
            });
        }
        if(itemList.getInvoiceDisPer ()==null){
            holder.discount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double discountper = 0;
                    double disamt = 0;
                    double quantity = 0;
                    double total = 0;
                    double total_amount=0.0;


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.inv_qty.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (itemList.getUnitSellingPrice ());

                        Log.e("454ff", String.valueOf(unit_price));

                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);

                        total_amount = total - disamt;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    }

                }
            });
        }else{
            holder.discount.setText (itemList.getInvoiceDisPer ());
            holder.amount.setText (itemList.getInvoiceTotal ());
            holder.discount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double discountper = 0;
                    double disamt = 0;
                    double quantity = 0;
                    double total = 0;
                    double total_amount=0.0;


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.inv_qty.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (itemList.getUnitSellingPrice ());

                        Log.e("454ff", String.valueOf(unit_price));

                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);

                        total_amount = total - disamt;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        salesInvoiceEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    }

                }
            });

        }
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
        public TextView ord_qty, discount, amount, uom;
        public EditText inv_qty;
        public TextView product;
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