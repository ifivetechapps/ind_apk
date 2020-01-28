package com.ifiveuv.indsmart.UI.SalesQuote.Adapter;



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

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Adapter.ProductAdapter;

import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.SalesQuote.CreateSalesQuote;
import com.ifiveuv.indsmart.UI.SalesQuote.Model.QuoteItemLineList;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class QuoteItemAdapter extends RecyclerView.Adapter<QuoteItemAdapter.MyViewHolder> {
    private Context context;
    CreateSalesQuote createSalesQuote;
    RealmList<QuoteItemLineList> itemLists;
    List<Products> productsList;
    String size;
    Realm realm;
    int rate=0;
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
            discountPer = view.findViewById(R.id.discountPer);
            amount = view.findViewById(R.id.amount);
            uom = view.findViewById(R.id.uom);
            unit_price = view.findViewById(R.id.unit_price);
            realm = Realm.getDefaultInstance();
        }
    }

    public QuoteItemAdapter(Context context, RealmList<QuoteItemLineList> cartList, List<Products> products, CreateSalesQuote createSalesQuote) {
        this.context = context;
        this.createSalesQuote = createSalesQuote;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public QuoteItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_edit_view, parent, false);

        return new QuoteItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuoteItemAdapter.MyViewHolder holder, final int mPosition) {
        final QuoteItemLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
        holder.productAdapter = new ProductAdapter(context, android.R.layout.simple_spinner_item, productsList, holder.productId);
        holder.product.setAdapter(holder.productAdapter);

        holder.product.setSelection(itemList.getProductPosition ());

        holder.product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition(gPosition);
                realm.beginTransaction();
                itemList.setProductPosition (gPosition);
                itemList.setProduct (holder.productAdapter.getItem(gPosition).getProduct_name());
                realm.commitTransaction();
                RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(holder.productAdapter.getItem(gPosition).getUom_id())).findAll();
                holder.uom.setText(uomModels.get(0).getUom_name());
                createSalesQuote.setProductList(mPosition,  holder.productAdapter.getItem(gPosition).getPro_id (),holder.productAdapter.getItem(gPosition).getProduct_name(), holder.productAdapter.getItem(gPosition).getUom_id(), uomModels.get(0).getUom_name());
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
                        createSalesQuote.setQuantity(mPosition, quantity);
                    } else {
                        quantity = Integer.parseInt(s.toString());
                        createSalesQuote.setQuantity(mPosition, quantity);
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
                        createSalesQuote.setQuantity(mPosition, quantity);
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

                        createSalesQuote.setUnitPrice(mPosition, up);
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

                        createSalesQuote.setUnitPrice(mPosition, up);
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


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        createSalesQuote.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (holder.unit_price.getText().toString ().trim ());

                        Log.e("454ff", String.valueOf(unit_price));

                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);

                        total_amount = total - disamt;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        createSalesQuote.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    }

                }
            });
        } else {
            holder.discountPer.setText(String.valueOf(itemList.getDisPer ()));
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


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        createSalesQuote.setDiscount(mPosition, discountper,total,disamt,total_amount );
                        realm.commitTransaction ();
                    } else {
                        discountper = Double.parseDouble(s.toString());
                        quantity= Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        unit_price= Double.parseDouble (holder.unit_price.getText().toString ().trim ());

                        Log.e("454ff", String.valueOf(unit_price));

                        total = quantity * unit_price;
                        disamt = ((discountper / 100) * total);

                        total_amount = total - disamt;
                        holder.amount.setText (String.valueOf (total_amount));
                        realm.beginTransaction ();
                        createSalesQuote.setDiscount(mPosition, discountper,total,disamt,total_amount );
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
}