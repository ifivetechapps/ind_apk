package com.ifiveuv.indsmart.UI.PurchaseQuote;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.CommanAdapter.SupplierListAdapter;
import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.Masters.Model.FreightList;
import com.ifiveuv.indsmart.UI.Masters.Model.SupplierList;

import com.ifiveuv.indsmart.UI.PurchaseQuote.Adapter.EditQuoteLineAdapter;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Model.QuotationHeader;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Model.QuotationLines;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.FreightListAdapter;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class EditQuotationActivity extends BaseActivity implements SupplierListAdapter.onItemClickListner{
    Realm realm;

    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.supplier_name)
    TextView supplier_name;
    @BindView(R.id.supplier_site)
    TextView supplier_site;
    @BindView(R.id.freight_address)
    TextView freight_address;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.tax_total)
    TextView tax_total;
    @BindView(R.id.grand_total)
    TextView grand_total;
    int supplier_id, freight_id, nextId;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    RecyclerView.LayoutManager mLayoutManager;
    Calendar deliveryDate, enquiryDate;
    String enquiry_Date;
    EditQuoteLineAdapter quoteLineAdapter;
    RealmList<QuotationLines> quoteItemLineLists = new RealmList<> ();
    QuotationHeader quotationHeader;
    String type_quote;
    Menu menu;
    int qo_id;
    List<AllDataList> allDataLists = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.create_purchase_quote);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        allDataLists.addAll (realm.where (AllDataList.class).findAll ());
        deliveryDate = Calendar.getInstance ();
        enquiryDate = Calendar.getInstance ();
        enquiry_Date = IFiveEngine.myInstance.getSimpleCalenderDate (enquiryDate);

        type_quote = "standard";
        Intent intent = getIntent ();
        qo_id = Integer.parseInt (intent.getStringExtra ("headerid"));
        loadItemAdapter ();
    }

    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (EditQuotationActivity.this, new DatePickerDialog.OnDateSetListener () {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                deliveryDate.set (Calendar.YEAR, year);
                deliveryDate.set (Calendar.MONTH, monthOfYear);
                deliveryDate.set (Calendar.DAY_OF_MONTH, dayOfMonth);
                delivery_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (deliveryDate));

            }
        }, deliveryDate.get (Calendar.YEAR), deliveryDate.get (Calendar.MONTH), deliveryDate.get (Calendar.DAY_OF_MONTH));
        dialog.show ();
    }

    @OnClick(R.id.supplier_name)
    public void loadSupplierNames() {

        View view = LayoutInflater.from (this)
                .inflate (R.layout.autosearch_recycler, null);
        chartDialog = new AlertDialog.Builder (this);
        chartDialog.setView (view);
        chartAlertDialog = chartDialog.show ();

        chartAlertDialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
        RecyclerView townsDataList = view.findViewById (R.id.items_data_list);
        final EditText search_type = view.findViewById (R.id.search_type);
        TextView textTitle = view.findViewById (R.id.text_title);
        textTitle.setText ("Supplier Name");

        final SupplierListAdapter itemShowAdapter = new SupplierListAdapter (this, allDataLists.get (0).getSupplierList (), this);

        townsDataList.setAdapter (itemShowAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        townsDataList.setLayoutManager (mLayoutManager);
        townsDataList.setItemAnimator (new DefaultItemAnimator ());
        search_type.addTextChangedListener (new TextWatcher () {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_type.getText ().toString ().toLowerCase (Locale.getDefault ());
                itemShowAdapter.filter (text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });


    }



    private void loadItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        RealmResults<QuotationHeader> results;
        results = realm.where (QuotationHeader.class)
                .equalTo ("quote_id", qo_id)
                .findAll ();
        supplier_name.setText (results.get (0).getSupplierName ());
        supplier_site.setText (results.get (0).getSupplierSite ());
        freight_address.setText (results.get (0).getFreight_carrier ());
        delivery_date.setText (results.get (0).getDeliveryDate ());
        tax_total.setText (results.get (0).getGrandTax ());
        grand_total.setText (results.get (0).getGrandTotal ());
        quoteItemLineLists.addAll (results.get (0).getQuotationLines ());
        quoteLineAdapter = new EditQuoteLineAdapter (this, quoteItemLineLists, products, this);
        items_data.setAdapter (quoteLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        quoteLineAdapter.notifyDataSetChanged ();


    }

    public void setQuantity(int position, int quant) {
        quoteItemLineLists.get (position).setQuoteQty (String.valueOf (quant));
    }

    public void setDiscount(int position, double quant, double taxtotal, double grandtotal, double discount) {
        quoteItemLineLists.get (position).setDiscountAmt (String.valueOf (quant));
        quoteItemLineLists.get (position).setTaxtotal (String.valueOf (taxtotal));
        quoteItemLineLists.get (position).setLineTotal (String.valueOf (grandtotal));
        quoteItemLineLists.get (position).setDiscountPercent (String.valueOf (discount));
        grandTotal (quoteItemLineLists);
    }

    private void grandTotal(RealmList<QuotationLines> quoteItemLineLists) {

        double totalPrice = 0;
        double taxTotal = 0;
        for (int i = 0; i < quoteItemLineLists.size (); i++) {
            if (quoteItemLineLists.get (i).getLineTotal () != null) {
                totalPrice += Double.parseDouble (quoteItemLineLists.get (i).getLineTotal ());
            }

        }
        for (int i = 0; i < quoteItemLineLists.size (); i++) {
            if (quoteItemLineLists.get (i).getTaxtotal () != null) {
                taxTotal += Double.parseDouble (quoteItemLineLists.get (i).getTaxtotal ());
            }

        }
        grand_total.setText (totalPrice + "");
        tax_total.setText (taxTotal + "");
    }


    public void setDuedate(int mPosition, String date) {
        quoteItemLineLists.get (mPosition).setPromisedDate (date);
    }

    @Override
    public void onItemPostion(int position) {
        String name = allDataLists.get (0).getSupplierList ().get (position).getSupplierName ();
        supplier_name.setText (name);
        freight_address.setText (allDataLists.get (0).getSupplierList ().get (position).getSupplierAddress ());
        supplier_id = allDataLists.get (0).getSupplierList ().get (position).getSupplierTblId ();
        chartAlertDialog.dismiss ();

    }



    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = quoteItemLineLists.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (quoteItemLineLists.get (position - 1).getDiscountAmt () == null) {
            Toast.makeText (this, "Enter the Quantity", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }


    @OnClick(R.id.submit_data)
    public void submitData() {
        int position = quoteItemLineLists.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (quoteItemLineLists.get (position - 1).getDiscountAmt () == null) {
            Toast.makeText (this, "Enter the Quantity", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }

    }

    private void headerSave() {

        quotationHeader = new QuotationHeader ();
        quotationHeader.setQuote_id (qo_id);
        quotationHeader.setSupplierName (supplier_name.getText ().toString ());
        quotationHeader.setSupplierSite (supplier_site.getText ().toString ());
        quotationHeader.setQuoteType (type_quote);
        quotationHeader.setSupplierId (supplier_id);
        quotationHeader.setStatus ("Submit");
        quotationHeader.setQuoteDate (enquiry_Date);
        quotationHeader.setDeliveryDate (delivery_date.getText ().toString ());
        quotationHeader.setFreight_carrier (freight_address.getText ().toString ());
        quotationHeader.setGrandTotal (grand_total.getText ().toString ());
        quotationHeader.setGrandTax (tax_total.getText ().toString ());
        quotationHeader.setQuotationLines (quoteItemLineLists);
        uploadLocalPurchase (quotationHeader);
    }

    private void headerdraftSave() {

        quotationHeader = new QuotationHeader ();
        quotationHeader.setQuote_id (qo_id);
        quotationHeader.setSupplierName (supplier_name.getText ().toString ());
        quotationHeader.setSupplierSite (supplier_site.getText ().toString ());
        quotationHeader.setQuoteType (type_quote);
        quotationHeader.setSupplierId (supplier_id);
        quotationHeader.setStatus ("Draft");
        quotationHeader.setQuoteDate (enquiry_Date);
        quotationHeader.setDeliveryDate (delivery_date.getText ().toString ());
        quotationHeader.setFreight_carrier (freight_address.getText ().toString ());
        quotationHeader.setGrandTotal (grand_total.getText ().toString ());
        quotationHeader.setGrandTax (tax_total.getText ().toString ());
        quotationHeader.setQuotationLines (quoteItemLineLists);
        uploadLocalPurchase (quotationHeader);

    }

    private void uploadLocalPurchase(final QuotationHeader quotationHeader) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        QuotationHeader allSalesOrder = realm.copyToRealmOrUpdate (quotationHeader);
                        realm.commitTransaction ();
                        Intent intent = new Intent (EditQuotationActivity.this, SubDashboard.class);
                        intent.putExtra ("type", "Purchase");
                        startActivity (intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d ("Test", "In onError()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction (new Realm.Transaction () {
                            @Override
                            public void execute(Realm realm) {
                            }
                        });
                    }
                });
    }

    public void setProductList(int mPosition, String product, Integer po_id, Integer uom_id, String uom_name, Integer hsnId, String hsnName, Integer taxId, String taxName, Integer rate) {
        quoteItemLineLists.get (mPosition).setProduct (product);
        quoteItemLineLists.get (mPosition).setProductId (po_id);
        quoteItemLineLists.get (mPosition).setUomId (uom_id);
        quoteItemLineLists.get (mPosition).setUom (uom_name);
        quoteItemLineLists.get (mPosition).setHsnCode (hsnName);
        quoteItemLineLists.get (mPosition).setHsnId (hsnId);
        quoteItemLineLists.get (mPosition).setTaxId (taxId);
        quoteItemLineLists.get (mPosition).setTaxGroup (taxName);
        quoteItemLineLists.get (mPosition).setPrice (rate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater ().inflate (R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId ();
        if (id == R.id.add_item) {
            int position = quoteItemLineLists.size ();
            if (quoteItemLineLists.get (position - 1).getPromisedDate () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        quoteItemLineLists.add (new QuotationLines ());
        quoteLineAdapter.notifyItemInserted (position);
    }

}