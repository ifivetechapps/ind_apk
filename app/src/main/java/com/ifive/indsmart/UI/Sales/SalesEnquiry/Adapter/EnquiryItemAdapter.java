package com.ifive.indsmart.UI.Sales.SalesEnquiry.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.Tax_type;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Adapter.ProductAdapter;
import com.ifive.indsmart.UI.Masters.Model.UomModel;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryLineList;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryCreatingActivity;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EnquiryItemAdapter extends RecyclerView.Adapter<EnquiryItemAdapter.MyViewHolder> {
    SalesEnquiryCreatingActivity salesEnquiryCreatingActivity;
    List<EnquiryLineList> itemLists;
    List<Products> productsList;
    String size;
    Realm realm;
    Calendar neededByDate;
    private Context context;
    int tax_id;
    double tax_value=0.0;

    public EnquiryItemAdapter(Context context, List<EnquiryLineList> cartList, List<Products> products, SalesEnquiryCreatingActivity salesEnquiryCreatingActivity) {
        this.context = context;
        this.salesEnquiryCreatingActivity = salesEnquiryCreatingActivity;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public EnquiryItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.sales_item_view, parent, false);

        return new EnquiryItemAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final EnquiryItemAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryLineList itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));
        neededByDate = Calendar.getInstance ();
        holder.productAdapter = new ProductAdapter (context, android.R.layout.simple_spinner_item,
                productsList, holder.productId);
        holder.product.setAdapter (holder.productAdapter);

        if(itemList.getEnquiryProductId ()!=null){
            String compareValue = itemList.getEnquiryProductId ();
            Products products=realm.where (Products.class).equalTo ("pro_id",Integer.parseInt (itemList.getEnquiryProductId ())).findFirst ();
            if (compareValue != null) {
                int spinnerPosition = holder.productAdapter.getPosition(products);
                holder.product.setSelection(spinnerPosition);
            }
        }



        holder.product.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {

                holder.productAdapter.setPosition (gPosition);
                RealmResults<Tax_type> taxModels = realm.where (Tax_type.class).equalTo ("taxGroupId", Integer.valueOf (holder.productAdapter.getItem (gPosition).getTax_group_id ())).findAll ();
                tax_value= Integer.parseInt (taxModels.get (0).getDisplayName ());
                tax_id= Integer.parseInt (String.valueOf (taxModels.get (0).getTaxGroupId ()));
                RealmResults<UomModel> uomModels = realm.where (UomModel.class).equalTo ("uom_id", Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ())).findAll ();
                holder.uom.setText (uomModels.get (0).getUom_name ());
                salesEnquiryCreatingActivity.setProductList (mPosition, gPosition,holder.productAdapter.getItem (gPosition).getPro_id (), String.valueOf (holder.productAdapter.getItem (gPosition).getProduct_name ()), Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ()), uomModels.get (0).getUom_name (),tax_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        holder.quantity.requestFocus ();

        holder.quantity.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = null;
                if (s.toString ().equals (null) || s.toString ().equals ("")) {

                    salesEnquiryCreatingActivity.setQuantity (mPosition, value);
                } else {
                    value = s.toString ();
                    salesEnquiryCreatingActivity.setQuantity (mPosition, value);

                }

            }
        });

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
        public TextView uom, quantity;
        public Spinner product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;

        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);


            uom = view.findViewById (R.id.uom);
            realm = Realm.getDefaultInstance ();
        }
    }
}