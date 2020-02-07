package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter;

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
import com.ifiveuv.indsmart.Connectivity.Tax_type;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Adapter.ProductAdapter;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.ConvertFromEnquiryToQuoteActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class EnquiryToQuoteLineAdapter extends RecyclerView.Adapter<EnquiryToQuoteLineAdapter.MyViewHolder> {
    ConvertFromEnquiryToQuoteActivity saleOrderActivity;
    RealmList<EnquiryLineList> itemLists;
    List<Products> productsList;
    int tax_id;
    Realm realm;
    private Context context;
    double tax_value=0.0;
    public EnquiryToQuoteLineAdapter(Context context, RealmList<EnquiryLineList> cartList, List<Products> products, ConvertFromEnquiryToQuoteActivity saleOrderActivity) {
        this.context = context;
        this.saleOrderActivity = saleOrderActivity;
        this.itemLists = cartList;
        this.productsList = products;
    }

    @Override
    public EnquiryToQuoteLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enquiry_to_quoteadapter, parent, false);

        return new EnquiryToQuoteLineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EnquiryToQuoteLineAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
        holder.productAdapter = new ProductAdapter(context, android.R.layout.simple_spinner_item, productsList, holder.productId);
        holder.product.setAdapter(holder.productAdapter);
       if(itemList.getEnquiryProductId ()!=null){
           Products products=realm.where (Products.class).equalTo ("pro_id",Integer.parseInt (itemList.getEnquiryProductId ())).findFirst ();
           int spinnerPosition = holder.productAdapter.getPosition(products);
           holder.product.setSelection(spinnerPosition);

       }



        holder.product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition(gPosition);
                realm.beginTransaction();
                itemList.setEnquiryProductPosition (gPosition);
                itemList.setEnquiryProduct (holder.productAdapter.getItem(gPosition).getProduct_name());
                realm.commitTransaction();
                RealmResults<Tax_type> taxModels = realm.where (Tax_type.class).equalTo ("taxGroupId", Integer.valueOf (holder.productAdapter.getItem (gPosition).getTax_group_id ())).findAll ();
                tax_value= Integer.parseInt (taxModels.get (0).getDisplayName ());
                tax_id= Integer.parseInt (String.valueOf (taxModels.get (0).getTaxGroupId ()));
                RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(holder.productAdapter.getItem(gPosition).getUom_id())).findAll();
                holder.uom.setText(uomModels.get(0).getUom_name());
                saleOrderActivity.setProductList(mPosition,  holder.productAdapter.getItem(gPosition).getPro_id (),holder.productAdapter.getItem(gPosition).getProduct_name(), holder.productAdapter.getItem(gPosition).getUom_id(), uomModels.get(0).getUom_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (itemList.getEnquiryRequiredQuantity () == null) {
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
                        saleOrderActivity.setQuantity(mPosition, quantity);
                    } else {
                        quantity = Integer.parseInt(s.toString());
                        saleOrderActivity.setQuantity(mPosition, quantity);
                    }
                    realm.commitTransaction();

                }
            });
        } else {
            holder.quantity.setText(String.valueOf(itemList.getEnquiryRequiredQuantity()));
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
                    int quantity;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt(s.toString());
                        saleOrderActivity.setQuantity(mPosition, quantity);
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
                    double quantity,up  ;


                    realm.beginTransaction();
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ());
                        quantity = 0.0;

                        up=0.0;

                    } else {
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ());
                        up = Double.parseDouble (s.toString ());
                        saleOrderActivity.setUnitPrice(mPosition, up);
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
                    Realm.init(context);
                    RealmConfiguration realmConfiguration = new RealmConfiguration
                            .Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    Realm.setDefaultConfiguration(realmConfiguration);
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    double up;


                    if (s.toString().equals(null) || s.toString().equals("")) {

                        up=0.0;
                    } else {

                        up = Double.parseDouble (s.toString ());

                        saleOrderActivity.setUnitPrice(mPosition, up);
                    }
                    realm.commitTransaction();

                }
            });

        }
        if (itemList.getDiscountPercent () == null) {
            holder.disountPer.addTextChangedListener(new TextWatcher() {
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
                    double tax_cal;


                    double unit_price=0.0;
                    if (s.toString().equals(null) || s.toString().equals("")) {
                        total = 0;
                        discountper = 0;
                        realm.beginTransaction ();
                        saleOrderActivity.setDiscount(mPosition, discountper,total,disamt,total_amount,tax_id );
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
                        itemList.setTaxAmt (String.valueOf (tax_cal));
                        saleOrderActivity.setDiscount(mPosition, discountper,total,disamt,total_amount,tax_id );
                        realm.commitTransaction ();
                    }

                }
            });
        } else {
            holder.disountPer.setText(String.valueOf(itemList.getDiscountPercent ()));
            holder.amount.setText(String.valueOf(itemList.getLineTotal ()));
            holder.disountPer.addTextChangedListener(new TextWatcher() {
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
                        saleOrderActivity.setDiscount(mPosition, discountper,total,disamt,total_amount,tax_id );
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
                        itemList.setTaxAmt (String.valueOf (tax_cal));
                        saleOrderActivity.setDiscount(mPosition, discountper,total,disamt,total_amount,tax_id );
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

    public void restoreItem(EnquiryLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  uom,amount;
        public EditText quantity,disountPer,unit_price;
        public Spinner  product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            quantity = view.findViewById(R.id.quantity);
            unit_price = view.findViewById(R.id.unit_price);
            amount = view.findViewById(R.id.amount);
            disountPer = view.findViewById(R.id.disountPer);
            uom = view.findViewById(R.id.uom);
            realm = Realm.getDefaultInstance();
        }
    }
}