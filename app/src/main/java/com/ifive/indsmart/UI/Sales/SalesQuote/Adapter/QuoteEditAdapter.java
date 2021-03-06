package com.ifive.indsmart.UI.Sales.SalesQuote.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.Tax_type;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Adapter.ProductAdapter;
import com.ifive.indsmart.UI.Masters.Model.UomModel;
import com.ifive.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifive.indsmart.UI.Sales.SalesQuote.SalesQuoteEditActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class QuoteEditAdapter extends RecyclerView.Adapter<QuoteEditAdapter.MyViewHolder> {
    SalesQuoteEditActivity salesQuoteEditActivity;
    RealmList<QuoteItemLineList> itemLists;
    List<Products> productsList;
    Realm realm;
    int rate;
    int tax_value,tax_id;

    private Context context;

    public QuoteEditAdapter(Context context, RealmList<QuoteItemLineList> cartList, List<Products> products, SalesQuoteEditActivity salesQuoteEditActivity) {
        this.context = context;
        this.salesQuoteEditActivity = salesQuoteEditActivity;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public QuoteEditAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_edit_view, parent, false);

        return new QuoteEditAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuoteEditAdapter.MyViewHolder holder, final int mPosition) {
        final QuoteItemLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
        holder.productAdapter = new ProductAdapter(context, android.R.layout.simple_spinner_item, productsList, holder.productId);
        holder.product.setAdapter(holder.productAdapter);

        if(itemList.getProductId ()!=null){
            Products products=realm.where (Products.class).equalTo ("pro_id",itemList.getProductId ()).findFirst ();
            int spinnerPosition = holder.productAdapter.getPosition(products);
            holder.product.setSelection(spinnerPosition);

        }

        holder.product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition(gPosition);

                RealmResults<Tax_type> taxModels = realm.where (Tax_type.class).equalTo ("taxGroupId", Integer.valueOf (holder.productAdapter.getItem (gPosition).getTax_group_id ())).findAll ();
                tax_value= Integer.parseInt (taxModels.get (0).getDisplayName ());
                tax_id= Integer.parseInt (String.valueOf (taxModels.get (0).getTaxGroupId ()));
                RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(holder.productAdapter.getItem(gPosition).getUom_id())).findAll();
                holder.uom.setText(uomModels.get(0).getUom_name());
                salesQuoteEditActivity.setProductList(mPosition,  holder.productAdapter.getItem(gPosition).getPro_id (),holder.productAdapter.getItem(gPosition).getProduct_name(), holder.productAdapter.getItem(gPosition).getUom_id(), uomModels.get(0).getUom_name()
                        ,taxModels.get (0).getTaxGroupId (),taxModels.get (0).getTaxGroupName ());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (itemList.getQuantity () == null) {
            holder.quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int quantity = 0;
                    int total = 0;
                    realm.beginTransaction();
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        quantity = 0;
                        salesQuoteEditActivity.setQuantity(mPosition, quantity);
                    } else {
                        quantity = Integer.parseInt(s.toString());
                        salesQuoteEditActivity.setQuantity(mPosition, quantity);
                    }
                    realm.commitTransaction();

                }
            });
        } else {
            holder.quantity.setText(String.valueOf(itemList.getQuantity ()));
            holder.quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Realm.init(context);
                    RealmConfiguration realmConfiguration = new RealmConfiguration
                            .Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    Realm.setDefaultConfiguration(realmConfiguration);
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    int quan = IFiveEngine.myInstance.convertStringToInt(s.toString());
                    itemList.setQuantity (String.valueOf(quan));
                    int quantity;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt(s.toString());
                        salesQuoteEditActivity.setQuantity(mPosition, quantity);
                    }
                    realm.commitTransaction();

                }
            });

        }
        if (itemList.getUnitPrice () == null) {
            holder.unit_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double up  ;


                    realm.beginTransaction();
                    if (s.toString().equals(null) || s.toString().equals("")) {


                        up=0.0;

                    } else {
                        up = Double.parseDouble (s.toString ());

                        salesQuoteEditActivity.setUnitPrice(mPosition, up);
                    }
                    realm.commitTransaction();

                }
            });
        } else {
            holder.unit_price.setText(String.valueOf(itemList.getUnitPrice ()));
            holder.unit_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double up  ;


                    realm.beginTransaction();
                    if (s.toString().equals(null) || s.toString().equals("")) {


                        up=0.0;

                    } else {
                        up = Double.parseDouble (s.toString ());

                        salesQuoteEditActivity.setUnitPrice(mPosition, up);
                    }
                    realm.commitTransaction();

                }
            });

        }
        if (itemList.getDisPer () == null) {
            holder.discountPer.addTextChangedListener(new TextWatcher() {
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
                    double tax_cal=0.0;

                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        salesQuoteEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (holder.unit_price.getText().toString ().trim ());

                        Log.e("454ff", String.valueOf(unit_price));

                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);
                        tax_cal = ((tax_value / 100) * disamt);

                        total_amount = (total - disamt)+tax_cal;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        itemList.setQuote_taxAmt (String.valueOf (tax_cal));
                        salesQuoteEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    }

                }
            });
        } else {
            holder.discountPer.setText(String.valueOf(itemList.getDisPer ()));
            holder.amount.setText(String.valueOf(itemList.getLineTotal ()));
            holder.discountPer.addTextChangedListener(new TextWatcher() {
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
                    double tax_cal=0.0;

                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        salesQuoteEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (holder.unit_price.getText().toString ().trim ());

                        Log.e("454ff", String.valueOf(unit_price));
                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);
                        tax_cal = ((tax_value / 100) * disamt);

                        total_amount = (total - disamt)+tax_cal;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        itemList.setQuote_taxAmt (String.valueOf (tax_cal));
                        salesQuoteEditActivity.setDiscount(mPosition, discountper,total,disamt,total_amount );
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

    public void restoreItem(QuoteItemLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  amount, uom;
        public Spinner  product;
        public EditText quantity,unit_price,discountPer;

        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            quantity = view.findViewById(R.id.quantity);
            amount = view.findViewById(R.id.amount);
            discountPer = view.findViewById(R.id.discountPer);
            uom = view.findViewById(R.id.uom);
            unit_price = view.findViewById(R.id.unit_price);
            realm = Realm.getDefaultInstance();
        }
    }
}