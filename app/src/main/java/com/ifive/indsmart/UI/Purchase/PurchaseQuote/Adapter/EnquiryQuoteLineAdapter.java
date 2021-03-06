package com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter;

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

import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Adapter.ProductAdapter;
import com.ifive.indsmart.UI.Masters.Model.UomModel;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.ConvertEnquirytoQuoteActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class EnquiryQuoteLineAdapter extends RecyclerView.Adapter<EnquiryQuoteLineAdapter.MyViewHolder> {
    private Context context;
    ConvertEnquirytoQuoteActivity createEnquiryActivity;
    RealmList<EnquiryItemList> itemLists;
    String uom, product;
    ArrayList<String> productArray = new ArrayList<String> ();
    ArrayList<String> uomArray = new ArrayList<String> ();
    List<Products> productsList;
    int rate = 0;
    Realm realm;
    String tax;
    Calendar expirydate;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, date, tax, price;
        public EditText quantity, discount;
        public Spinner product;
        public LinearLayout viewForeground;
        ProductAdapter productAdapter;
        int productId = 0;


        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            amount = view.findViewById (R.id.amount);
            tax = view.findViewById (R.id.tax);
            price = view.findViewById (R.id.price);
            discount = view.findViewById (R.id.discount);
            date = view.findViewById (R.id.date);
            realm = Realm.getDefaultInstance ();
        }
    }

    public EnquiryQuoteLineAdapter(Context context, RealmList<EnquiryItemList> cartList, List<Products> products, ConvertEnquirytoQuoteActivity createPurchaseOrder) {
        this.context = context;
        this.createEnquiryActivity = createPurchaseOrder;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public EnquiryQuoteLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.quote_line, parent, false);

        return new EnquiryQuoteLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final EnquiryQuoteLineAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryItemList itemList = itemLists.get (mPosition);
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
                        realm.beginTransaction ();
                        createEnquiryActivity.setQuantity (mPosition, quantity);
                        realm.commitTransaction ();
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
                        realm.beginTransaction ();
                        createEnquiryActivity.setQuantity (mPosition, quantity);
                        realm.commitTransaction ();
                    }
                }
            });
        }
        if (itemList.getDiscountAmt () == null) {

            holder.discount.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double discount = 0;
                    double quantity = 0;
                    double total = 0;
                    double discountCal;
                    double taxCount = 0, totalGrand = 0;

                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        discount = 0;
                    } else {
                        discount = Double.parseDouble (s.toString ());
                        Log.d ("discount", String.valueOf (discount));
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        double number = Double.parseDouble (holder.price.getText ().toString ().trim ());
                        double subtotal = quantity * number;
                        Log.d ("subtotal", String.valueOf (subtotal));
                        discountCal = ((discount / 100) * subtotal);
                        total = subtotal - discountCal;
                        if (tax.equals ("GST-18%")) {
                            taxCount = ((18.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        } else if (tax.equals ("GST-15%")) {
                            taxCount = ((15.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        } else if (tax.equals ("GST-12%")) {
                            taxCount = ((12.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        }
                        holder.amount.setText (String.valueOf (totalGrand));
                        realm.beginTransaction ();
                        createEnquiryActivity.setDiscount (mPosition, discountCal, taxCount, totalGrand);
                        realm.commitTransaction ();
                    }
                }
            });
        } else {
            holder.discount.setText (itemList.getOrdQty ());
            holder.amount.setText (itemList.getLineTotal ());
            holder.discount.addTextChangedListener (new TextWatcher () {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    double discount = 0;
                    double quantity = 0;
                    double total = 0;
                    double discountCal;
                    double taxCount = 0, totalGrand = 0;

                    if (s.toString ().equals (null) || s.toString ().equals ("")) {
                        quantity = 0;
                    } else {
                        discount = Double.parseDouble (s.toString ());
                        Log.d ("discount", String.valueOf (discount));
                        quantity = Double.parseDouble (holder.quantity.getText ().toString ().trim ());
                        double number = Double.parseDouble (holder.price.getText ().toString ().trim ());
                        double subtotal = quantity * number;
                        Log.d ("subtotal", String.valueOf (subtotal));
                        discountCal = ((discount / 100) * subtotal);
                        total = subtotal - discountCal;
                        if (tax.equals ("GST-18%")) {
                            taxCount = ((18.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        } else if (tax.equals ("GST-15%")) {
                            taxCount = ((15.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        } else if (tax.equals ("GST-12%")) {
                            taxCount = ((12.00 / 100.00) * total);
                            totalGrand = total + taxCount;
                            Log.d ("totalGrand", String.valueOf (totalGrand));
                            Log.d ("taxCount", String.valueOf (taxCount));
                            Log.d ("discountCal", String.valueOf (discountCal));
                        }
                        holder.amount.setText (String.valueOf (totalGrand));
                        realm.beginTransaction ();
                        createEnquiryActivity.setDiscount (mPosition, discountCal, taxCount, totalGrand);
                        realm.commitTransaction ();
                    }
                }
            });
        }


        holder.productAdapter = new ProductAdapter (context, android.R.layout.simple_spinner_item,
                productsList, holder.productId);
        holder.product.setAdapter (holder.productAdapter);

        holder.product.setSelection (itemList.getProductPostion ());

        holder.product.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int gPosition, long l) {
                if (itemList.getUom_id () == null || itemList.getUom_id () == -1) {
                    itemList.setUom_id (-1);
                    itemList.setUom_id (-1);
                }

                realm.beginTransaction ();
                holder.productAdapter.setPosition (gPosition);
                itemList.setProductPostion (gPosition);

                itemList.setProduct (holder.productAdapter.getItem (gPosition).getProduct_name ());
                RealmResults<UomModel> uomModels = realm.where (UomModel.class).equalTo ("uom_id", Integer.valueOf (holder.productAdapter.getItem (gPosition).getUom_id ())).findAll ();
              int uomId = uomModels.get (0).getUom_id ();
                String uomName = uomModels.get (0).getUom_name ();

                Log.d ("values", String.valueOf (uomId));
                createEnquiryActivity.setProductList (
                        mPosition,
                        String.valueOf (holder.productAdapter.getItem (gPosition).getProduct_name ()),
                        holder.productAdapter.getItem (gPosition).getPro_id (),
                        uomId, uomName

                );
                realm.commitTransaction ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (itemList.getPromised_date () != null) {
            holder.date.setText (itemList.getPromised_date ());

            holder.date.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                            expirydate.set (Calendar.YEAR, year);
                            expirydate.set (Calendar.MONTH, monthOfYear);
                            expirydate.set (Calendar.DAY_OF_MONTH, dayOfMonth);

                            holder.date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (expirydate));
                            String date = IFiveEngine.myInstance.getSimpleCalenderDate (expirydate);
                            realm.beginTransaction ();
                            createEnquiryActivity.setDuedate (mPosition, date);
                            realm.commitTransaction ();

                        }
                    }, expirydate.get (Calendar.YEAR), expirydate.get (Calendar.MONTH), expirydate.get (Calendar.DAY_OF_MONTH));
                    dialog.show ();
                }
            });
        } else {

            holder.date.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog (context, new DatePickerDialog.OnDateSetListener () {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                            expirydate.set (Calendar.YEAR, year);
                            expirydate.set (Calendar.MONTH, monthOfYear);
                            expirydate.set (Calendar.DAY_OF_MONTH, dayOfMonth);

                            holder.date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (expirydate));
                            String date = IFiveEngine.myInstance.getSimpleCalenderDate (expirydate);
                            realm.beginTransaction ();
                            createEnquiryActivity.setDuedate (mPosition, date);
                            realm.commitTransaction ();

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

    public void restoreItem(EnquiryItemList item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }
}