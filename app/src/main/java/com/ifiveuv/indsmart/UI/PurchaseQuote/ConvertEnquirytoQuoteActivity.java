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
import android.widget.Button;
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
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Adapter.EnquiryQuoteLineAdapter;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Model.QuotationHeader;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Model.QuotationLines;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;

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

public class ConvertEnquirytoQuoteActivity extends BaseActivity implements SupplierListAdapter.onItemClickListner{
    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.supplier_name)
    TextView supplier_name;
    @BindView(R.id.supplier_site)
    TextView supplier_site;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.tax_total)
    TextView tax_total;
    @BindView(R.id.grand_total)
    TextView grand_total;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    Realm realm;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    RecyclerView.LayoutManager mLayoutManager;
    int supplier_id, freight_id;
    Calendar deliveryDate;
    EnquiryQuoteLineAdapter enquiryQuoteLineAdapter;
    Menu menu;
    PurchaseEnquiryData purchaseEnquiryData;

    RealmList<EnquiryItemList> enquiryItemListRealmList = new RealmList<> ();
    List<AllDataList> allDataLists = new ArrayList<> ();
    QuotationHeader quotationHeader;
    int nextId, enquiryId;
    Calendar podateCalendar;
    String enq_date, type_quote, source_enquiry;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate (savedInstance);
        setContentView (R.layout.create_purchase_quote);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();

        Intent intent = getIntent ();
        enquiryId = Integer.parseInt (intent.getStringExtra ("headerid"));
        type_quote = intent.getStringExtra ("type");
        source_enquiry = intent.getStringExtra ("source");
        loadRequisitionItemAdapter ();

        allDataLists.addAll (realm.where (AllDataList.class).findAll ());
        podateCalendar = Calendar.getInstance ();
        enq_date = IFiveEngine.myInstance.getSimpleCalenderDate (podateCalendar);

        deliveryDate = Calendar.getInstance ();

    }

    private void loadRequisitionItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        RealmResults<PurchaseEnquiryData> results;
        results = realm.where (PurchaseEnquiryData.class)
                .equalTo ("enquiryId", enquiryId)
                .findAll ();

        enquiryItemListRealmList.addAll (results.get (0).getEnquiryItemLists ());

        enquiryQuoteLineAdapter = new EnquiryQuoteLineAdapter (this, enquiryItemListRealmList, products, this);
        items_data.setAdapter (enquiryQuoteLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        enquiryQuoteLineAdapter.notifyDataSetChanged ();


    }

    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (ConvertEnquirytoQuoteActivity.this, new DatePickerDialog.OnDateSetListener () {

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
            int position = enquiryItemListRealmList.size ();
            if (enquiryItemListRealmList.get (position - 1).getOrdQty () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        enquiryItemListRealmList.add (new EnquiryItemList ());
        enquiryQuoteLineAdapter.notifyItemInserted (position);
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


    public void setQuantity(int position, int quant) {
        enquiryItemListRealmList.get (position).setOrdQty (String.valueOf (quant));
    }

    public void setDiscount(int position, double quant, double taxtotal, double grandtotal) {
        enquiryItemListRealmList.get (position).setDiscountAmt (String.valueOf (quant));
        enquiryItemListRealmList.get (position).setTaxtotal (String.valueOf (taxtotal));
        enquiryItemListRealmList.get (position).setLineTotal (String.valueOf (grandtotal));
        grandTotal (enquiryItemListRealmList);
    }

    private void grandTotal(RealmList<EnquiryItemList> quoteItemLineLists) {

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
        enquiryItemListRealmList.get (mPosition).setPromised_date (date);
    }

    public void setProductList(int mPosition, String product, Integer po_id, Integer uom_id, String uom_name) {
        enquiryItemListRealmList.get (mPosition).setProduct (product);
        enquiryItemListRealmList.get (mPosition).setProductId (po_id);
        enquiryItemListRealmList.get (mPosition).setUom_id (uom_id);
        enquiryItemListRealmList.get (mPosition).setUom (uom_name);

    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = enquiryItemListRealmList.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (enquiryItemListRealmList.get (position - 1).getDiscountAmt () == null) {
            Toast.makeText (this, "Enter the Quantity", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }


    @OnClick(R.id.submit_data)
    public void submitData() {
        int position = enquiryItemListRealmList.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (enquiryItemListRealmList.get (position - 1).getDiscountAmt () == null) {
            Toast.makeText (this, "Enter the Quantity", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }

    }

    private void headerSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                PurchaseEnquiryData quoteItemList = realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId).findFirst ();
                quoteItemList.setSupplierSitestatus ("completed");

            }
        });

        Number currentIdNum = realm.where (QuotationHeader.class).max ("quote_id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        quotationHeader = new QuotationHeader ();
        quotationHeader.setQuote_id (nextId);
        quotationHeader.setSupplierName (supplier_name.getText ().toString ());
        quotationHeader.setSupplierSite (supplier_site.getText ().toString ());
        quotationHeader.setQuoteType (type_quote);
        quotationHeader.setSupplierId (supplier_id);
        quotationHeader.setSource (source_enquiry);
        quotationHeader.setStatus ("Submit");
        quotationHeader.setQuoteDate (enq_date);
        quotationHeader.setDeliveryDate (delivery_date.getText ().toString ());
        quotationHeader.setGrandTotal (grand_total.getText ().toString ());
        quotationHeader.setGrandTax (tax_total.getText ().toString ());

        uploadLocalPurchase (quotationHeader, nextId);
    }

    private void headerdraftSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                PurchaseEnquiryData quoteItemList = realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId).findFirst ();
                quoteItemList.setSupplierSitestatus ("completed");

            }
        });

        Number currentIdNum = realm.where (QuotationHeader.class).max ("quote_id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        quotationHeader = new QuotationHeader ();
        quotationHeader.setQuote_id (nextId);
        quotationHeader.setSupplierName (supplier_name.getText ().toString ());
        quotationHeader.setSupplierSite (supplier_site.getText ().toString ());
        quotationHeader.setQuoteType (type_quote);
        quotationHeader.setSupplierId (supplier_id);
        quotationHeader.setStatus ("Draft");
        quotationHeader.setSource (source_enquiry);
        quotationHeader.setQuoteDate (enq_date);
        quotationHeader.setDeliveryDate (delivery_date.getText ().toString ());

        quotationHeader.setGrandTotal (grand_total.getText ().toString ());
        quotationHeader.setGrandTax (tax_total.getText ().toString ());
        uploadLocalPurchase (quotationHeader, nextId);

    }

    private void uploadLocalPurchase(final QuotationHeader purchaseEnquiryData, final int nextId) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        QuotationHeader allSalesOrder = realm.copyToRealmOrUpdate (purchaseEnquiryData);

                        realm.commitTransaction ();
                        uploadLine (nextId);
                        Intent intent = new Intent (ConvertEnquirytoQuoteActivity.this, SubDashboard.class);
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

    private void uploadLine(int nextid) {

        realm.beginTransaction ();
        for (int i = 0; i < enquiryItemListRealmList.size (); i++) {
            QuotationLines quotationLines = realm.createObject (QuotationLines.class);
            quotationLines.setProduct (enquiryItemListRealmList.get (i).getProduct ());
            quotationLines.setUom (enquiryItemListRealmList.get (i).getUom ());
            quotationLines.setUomId (enquiryItemListRealmList.get (i).getUom_id ());
            quotationLines.setQuoteQty (enquiryItemListRealmList.get (i).getOrdQty ());
            quotationLines.setPromisedDate (enquiryItemListRealmList.get (i).getPromised_date ());
            quotationLines.setPrice (enquiryItemListRealmList.get (i).getPrice ());
            quotationLines.setProductId (enquiryItemListRealmList.get (i).getProductId ());
            quotationLines.setDiscountAmt (enquiryItemListRealmList.get (i).getDiscountAmt ());
            quotationLines.setHsnId (enquiryItemListRealmList.get (i).getHsnId ());
            quotationLines.setHsnCode (enquiryItemListRealmList.get (i).getHsnCode ());
            quotationLines.setTaxGroup (enquiryItemListRealmList.get (i).getTaxGroup ());
            quotationLines.setTaxId (enquiryItemListRealmList.get (i).getTaxId ());
            quotationLines.setTaxtotal (enquiryItemListRealmList.get (i).getTaxtotal ());
            quotationLines.setLineTotal (enquiryItemListRealmList.get (i).getLineTotal ());
            quotationLines.setProductPosition (enquiryItemListRealmList.get (i).getProductPostion ());
            QuotationHeader salesItemList = realm.where (QuotationHeader.class).equalTo ("quote_id", nextid).findFirst ();
            salesItemList.getQuotationLines ().add (quotationLines);
        }

        realm.commitTransaction ();
        Intent intent = new Intent (ConvertEnquirytoQuoteActivity.this, Dashboard.class);
        startActivity (intent);
    }




    @Override
    public void onItemPostion(int position) {
        String name = allDataLists.get (0).getSupplierList ().get (position).getSupplierName ();
        supplier_name.setText (name);
        supplier_site.setText (allDataLists.get (0).getSupplierList ().get (position).getSupplierAddress ());
        supplier_id = allDataLists.get (0).getSupplierList ().get (position).getSupplierTblId ();
        chartAlertDialog.dismiss ();

    }


}
