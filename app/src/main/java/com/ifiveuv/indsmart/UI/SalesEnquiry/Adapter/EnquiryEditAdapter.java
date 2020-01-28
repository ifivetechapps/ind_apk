package com.ifiveuv.indsmart.UI.SalesEnquiry.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Adapter.ProductAdapter;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryLineList;
import com.ifiveuv.indsmart.UI.SalesEnquiry.SalesEnquiryEditActivity;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class EnquiryEditAdapter extends RecyclerView.Adapter<EnquiryEditAdapter.MyViewHolder> {
    SalesEnquiryEditActivity salesEnquiryEditActivitys;
    RealmList<EnquiryLineList> itemLists;
    List<Products> productsList;
    Realm realm;
    int rate;
    private Context context;
    Calendar neededByDate;

    public EnquiryEditAdapter(Context context, RealmList<EnquiryLineList> cartList, List<Products> products, SalesEnquiryEditActivity salesEnquiryEditActivity) {
        this.context = context;
        this.salesEnquiryEditActivitys = salesEnquiryEditActivity;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public EnquiryEditAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.sales_item_view, parent, false);

        return new EnquiryEditAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final EnquiryEditAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryLineList itemList = itemLists.get (mPosition);
        neededByDate = Calendar.getInstance ();
        holder.productAdapter = new ProductAdapter (context, android.R.layout.simple_spinner_item,
                productsList, holder.productId);
        holder.product.setAdapter (holder.productAdapter);

        holder.product.setSelection (itemList.getEnquiryProductPosition ());

        holder.product.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition (gPosition);
                RealmResults<UomModel> uomModels = realm.where (UomModel.class).equalTo ("uom_id", Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ())).findAll ();
                holder.uom.setText (uomModels.get (0).getUom_name ());
                realm.beginTransaction ();
                salesEnquiryEditActivitys.setProductList (mPosition, gPosition, holder.productAdapter.getItem (gPosition).getPro_id (), String.valueOf (holder.productAdapter.getItem (gPosition).getProduct_name ()), Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ()), uomModels.get (0).getUom_name ());
                realm.commitTransaction ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        if (itemList.getEnquiryRequiredQuantity () == null) {
            holder.quantity.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String quantity;
                    realm.beginTransaction ();
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = null;
                        salesEnquiryEditActivitys.setQuantity (mPosition, quantity);
                    } else {
                        quantity = s.toString ();
                        salesEnquiryEditActivitys.setQuantity (mPosition, quantity);
                    }
                    realm.commitTransaction ();
                }
            });
        } else {

            holder.quantity.setText (String.valueOf (itemList.getEnquiryRequiredQuantity ()));

            holder.quantity.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String quantity;
                    realm.beginTransaction ();
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = null;
                        salesEnquiryEditActivitys.setQuantity (mPosition, quantity);
                    } else {
                        quantity = s.toString ();
                        salesEnquiryEditActivitys.setQuantity (mPosition, quantity);
                    }
                    realm.commitTransaction ();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemLists.size ();
    }

    public void removeItem(int position) {
        itemLists.remove (position);
        notifyItemRemoved (position);
    }

    public void restoreItem(EnquiryLineList item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uom, quantity,date;
        public Spinner product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;

        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            date = view.findViewById (R.id.date);
            uom = view.findViewById (R.id.uom);
            realm = Realm.getDefaultInstance ();

        }
    }
}