package com.ifiveuv.indsmart.UI.PurchaseRequisition.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Adapter.ProductAdapter;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.CreateRequisitionActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionLines;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RequisitionLineAdapter extends RecyclerView.Adapter<RequisitionLineAdapter.MyViewHolder> {
    private Context context;
    CreateRequisitionActivity createEnquiryActivity;
    RealmList<RequisitionLines> itemLists;

    List<Products> productsList;

    Realm realm;
    Calendar expirydate;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount,uom;
        public EditText quantity;
        public Spinner  product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;


        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            amount = view.findViewById (R.id.amount);

            uom = view.findViewById (R.id.uom);
            realm = Realm.getDefaultInstance();
        }
    }

    public RequisitionLineAdapter(Context context, RealmList<RequisitionLines> cartList, List<Products> products, CreateRequisitionActivity createPurchaseOrder) {
        this.context = context;
        this.createEnquiryActivity = createPurchaseOrder;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public RequisitionLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.purchaserequsition_line, parent, false);

        return new RequisitionLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final RequisitionLineAdapter.MyViewHolder holder, final int mPosition) {
        final RequisitionLines itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));
        expirydate = Calendar.getInstance ();

        if (itemList.getOrdQty () == null) {

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

                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt (s.toString ());

                        createEnquiryActivity.setQuantity(mPosition, quantity);
                    }
                }
            });
        } else {
            holder.quantity.setText (itemList.getOrdQty ());
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

                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt (s.toString ());

                        createEnquiryActivity.setQuantity(mPosition, quantity);
                    }
                }
            });
        }


        holder.productAdapter = new ProductAdapter(context, android.R.layout.simple_spinner_item,
                productsList, holder.productId);
        holder.product.setAdapter(holder.productAdapter);

        holder.product.setSelection(itemList.getProductPosition());

        holder.product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {


                Log.d ("gPostion", String.valueOf (gPosition));

                holder.productAdapter.setPosition(gPosition);
                itemList.setProductPosition(gPosition);

                itemList.setProduct(holder.productAdapter.getItem(gPosition).getProduct_name());
                RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(holder.productAdapter.getItem(gPosition).getUom_id())).findAll();

                holder.uom.setText(uomModels.get(0).getUom_name());


                createEnquiryActivity.setProductList(mPosition,

                        holder.productAdapter.getItem(gPosition).getPro_id (),
                        String.valueOf(holder.productAdapter.getItem(gPosition).getProduct_name()),
                        uomModels.get(0).getUom_id (),uomModels.get(0).getUom_name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (itemList.getPromised_date () != null) {
            holder.amount.setText (itemList.getPromised_date ());

            holder.amount.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                            expirydate.set (Calendar.YEAR, year);
                            expirydate.set (Calendar.MONTH, monthOfYear);
                            expirydate.set (Calendar.DAY_OF_MONTH, dayOfMonth);

                            holder.amount.setText (IFiveEngine.myInstance.getSimpleCalenderDate (expirydate));
                            String date = IFiveEngine.myInstance.getSimpleCalenderDate (expirydate);
                            createEnquiryActivity.setDuedate(mPosition, date);

                        }
                    }, expirydate.get (Calendar.YEAR), expirydate.get (Calendar.MONTH), expirydate.get (Calendar.DAY_OF_MONTH));
                    dialog.show ();
                }
            });
        } else {

            holder.amount.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                            expirydate.set (Calendar.YEAR, year);
                            expirydate.set (Calendar.MONTH, monthOfYear);
                            expirydate.set (Calendar.DAY_OF_MONTH, dayOfMonth);

                            holder.amount.setText (IFiveEngine.myInstance.getSimpleCalenderDate (expirydate));
                            String date = IFiveEngine.myInstance.getSimpleCalenderDate (expirydate);
                            createEnquiryActivity.setDuedate(mPosition, date);

                        }
                    }, expirydate.get (Calendar.YEAR), expirydate.get (Calendar.MONTH), expirydate.get (Calendar.DAY_OF_MONTH));
                    dialog.show ();
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

    public void restoreItem(RequisitionLines item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }
}