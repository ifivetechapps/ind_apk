package com.ifive.indsmart.UI.Sales.SalesCreate.Adapter;

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
import android.widget.Toast;

import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.Tax_type;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Adapter.ProductAdapter;
import com.ifive.indsmart.UI.Masters.Model.UomModel;
import com.ifive.indsmart.UI.Sales.SalesCreate.CreateSalesActivity;
import com.ifive.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesLineAdapter extends RecyclerView.Adapter<SalesLineAdapter.MyViewHolder> {
    CreateSalesActivity saleOrderActivity;
    RealmList<SalesItemLineList> itemLists;
    List<Products> productsList;
    Realm realm;
    private Context context;
    int tax_id;
    double tax_value=0.0;
    public SalesLineAdapter(Context context, RealmList<SalesItemLineList> cartList, List<Products> products, CreateSalesActivity saleOrderActivity) {
        this.context = context;
        this.saleOrderActivity = saleOrderActivity;
        this.itemLists = cartList;
        this.productsList = products;
    }

    @Override
    public SalesLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.enquiry_to_salesadapter, parent, false);

        return new SalesLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final SalesLineAdapter.MyViewHolder holder, final int mPosition) {
        final SalesItemLineList itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));
        holder.productAdapter = new ProductAdapter (context, android.R.layout.simple_spinner_item, productsList, holder.productId);
        holder.product.setAdapter (holder.productAdapter);

        if(itemList.getProductId ()!=null){
            Products products=realm.where (Products.class).equalTo ("pro_id",Integer.parseInt (itemList.getProductId ())).findFirst ();
            int spinnerPosition = holder.productAdapter.getPosition(products);
            holder.product.setSelection(spinnerPosition);

        }

        holder.product.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition (gPosition);
                realm.beginTransaction ();
                itemList.setProductPosition (gPosition);
                itemList.setProduct (holder.productAdapter.getItem (gPosition).getProduct_name ());
                realm.commitTransaction ();
                RealmResults<Tax_type> taxModels = realm.where (Tax_type.class).equalTo ("taxGroupId", Integer.valueOf (holder.productAdapter.getItem (gPosition).getTax_group_id ())).findAll ();
                tax_value= Integer.parseInt (taxModels.get (0).getDisplayName ());
                tax_id= Integer.parseInt (String.valueOf (taxModels.get (0).getTaxGroupId ()));

                RealmResults<UomModel> uomModels = realm.where (UomModel.class).equalTo ("uom_id", Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ())).findAll ();
                holder.uom.setText (uomModels.get (0).getUom_name ());
                saleOrderActivity.setProductList (mPosition, holder.productAdapter.getItem (gPosition).getPro_id (), holder.productAdapter.getItem (gPosition).getProduct_name (), holder.productAdapter.getItem (gPosition).getUom_id (), uomModels.get (0).getUom_name (),tax_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (itemList.getQuantity () == null) {
            holder.quantity.addTextChangedListener (new TextWatcher () {
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
                    realm.beginTransaction ();
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        total = 0;
                        quantity = 0;
                        saleOrderActivity.setQuantity (mPosition, quantity);
                    } else {
                        quantity = Integer.parseInt (s.toString ());
                        saleOrderActivity.setQuantity (mPosition, quantity);
                    }
                    realm.commitTransaction ();

                }
            });
        } else {
            holder.quantity.setText (String.valueOf (itemList.getQuantity ()));
            holder.quantity.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Realm.init (context);
                    RealmConfiguration realmConfiguration = new RealmConfiguration
                            .Builder ()
                            .deleteRealmIfMigrationNeeded ()
                            .build ();
                    Realm.setDefaultConfiguration (realmConfiguration);
                    realm = Realm.getDefaultInstance ();
                    realm.beginTransaction ();

                    int quantity;
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt (s.toString ());
                        saleOrderActivity.setQuantity (mPosition, quantity);
                    }
                    realm.commitTransaction ();

                }
            });

        }
        if (itemList.getUnitPrice () == null) {
            holder.unit_price.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int up = 0;

                    realm.beginTransaction ();
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        up = 0;
                    } else {
                        up = Integer.parseInt (s.toString ());
                        saleOrderActivity.setUnitPrice (mPosition, up);
                    }
                    realm.commitTransaction ();

                }
            });
        } else {
            holder.unit_price.setText (String.valueOf (itemList.getUnitPrice ()));
            holder.amount.setText (String.valueOf (itemList.getLineTotal ()));
            holder.unit_price.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Realm.init (context);
                    RealmConfiguration realmConfiguration = new RealmConfiguration
                            .Builder ()
                            .deleteRealmIfMigrationNeeded ()
                            .build ();
                    Realm.setDefaultConfiguration (realmConfiguration);
                    realm = Realm.getDefaultInstance ();
                    realm.beginTransaction ();
                    int up = 0;
                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        up = 0;
                    } else {
                        up = Integer.parseInt (s.toString ());
                        saleOrderActivity.setUnitPrice (mPosition, up);
                    }
                    realm.commitTransaction ();

                }
            });

        }

        if (itemList.getDisPer () == null) {
            holder.discount.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double quantity, up, amount, disper, disamt, unit_quan, tax_cal, total_amount;


                    if (holder.quantity.getText ().toString () == null ||holder.quantity.getText ().toString ().equals ("") || holder.unit_price.getText ().toString () == null|| holder.unit_price.getText ().toString ().equals ("")) {
                        Toast.makeText (saleOrderActivity, "Enter quantity or price", Toast.LENGTH_SHORT).show ();
                    } else {
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ());
                        up = Double.parseDouble (holder.unit_price.getText ().toString ());
                        realm.beginTransaction ();
                        if (s.toString ().equals (null) || s.toString ().equals ("")) {
                            disper = 0.0;
                            unit_quan = 0.0;
                            disamt = 0.0;

                        } else {
                            disper = Double.parseDouble (s.toString ());
                            unit_quan = up * quantity;
                            disamt = ((disper / 100) * unit_quan);
                            amount = unit_quan - disamt;
                            tax_cal = ((tax_value / 100) * amount);

                            total_amount = amount + tax_cal;
                            itemList.setTaxAmt (String.valueOf (tax_cal));
                            holder.amount.setText (String.valueOf (total_amount));
                            saleOrderActivity.setLineTotal (mPosition, disper, unit_quan, disamt, total_amount);
                        }
                        realm.commitTransaction ();

                    }
                }
            });
        } else {
            holder.discount.setText (String.valueOf (itemList.getDisPer ()));
            holder.amount.setText (String.valueOf (itemList.getLineTotal ()));
            holder.discount.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double quantity, up, amount, disper, disamt, unit_quan,tax_cal,total_amount;
                    if (holder.quantity.getText ().toString () == null ||holder.quantity.getText ().toString ().equals ("") || holder.unit_price.getText ().toString () == null|| holder.unit_price.getText ().toString ().equals ("")) {
                        Toast.makeText (saleOrderActivity, "Enter quantity or price", Toast.LENGTH_SHORT).show ();
                    } else {
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ());
                        up = Double.parseDouble (holder.unit_price.getText ().toString ());

                        realm.beginTransaction ();
                        if (s.toString ().equals (null) || s.toString ().equals ("")) {
                            disper = 0.0;
                            unit_quan = 0.0;
                            disamt = 0.0;

                        } else {
                            disper = Double.parseDouble (s.toString ());
                            unit_quan = up * quantity;
                            disamt = ((disper / 100) * unit_quan);
                            amount = unit_quan - disamt;
                            tax_cal = ((tax_value / 100) * amount);

                            total_amount = amount + tax_cal;
                            itemList.setTaxAmt (String.valueOf (tax_cal));
                            holder.amount.setText (String.valueOf (total_amount));
                            saleOrderActivity.setLineTotal (mPosition, disper, unit_quan, disamt, total_amount);
                        }
                        realm.commitTransaction ();
                    }
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

    public void restoreItem(SalesItemLineList item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uom, amount;
        public EditText quantity, unit_price, discount;
        public Spinner product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;

        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            unit_price = view.findViewById (R.id.unit_price);
            amount = view.findViewById (R.id.amount);
            uom = view.findViewById (R.id.uom);
            discount = view.findViewById (R.id.discount);
            realm = Realm.getDefaultInstance ();
        }
    }
}