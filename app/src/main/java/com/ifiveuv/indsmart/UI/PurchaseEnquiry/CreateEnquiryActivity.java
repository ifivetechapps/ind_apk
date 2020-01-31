package com.ifiveuv.indsmart.UI.PurchaseEnquiry;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.CommanAdapter.SupplierListAdapter;
import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Adapter.EnquiryLineAdapter;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;
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
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateEnquiryActivity extends BaseActivity implements SupplierListAdapter.onItemClickListner {
    @BindView(R.id.supplier_name)
    TextView supplier_name;
    @BindView(R.id.supplier_site_name)
    TextView supplier_site_name;
    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    Realm realm;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    RecyclerView.LayoutManager mLayoutManager;
    int supplier_id;
    EnquiryLineAdapter enquiryLineAdapter;
    Menu menu;
    PurchaseEnquiryData purchaseEnquiryData;
    RealmList<EnquiryItemList> enquiryItemLists = new RealmList<> ();
    RealmList<EnquiryItemList> tempenquiryItemLists;
    int nextId;
    Calendar podateCalendar;
    String enq_date, type_enquiry, source_enquiry;
    List<AllDataList> allDataLists = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate (savedInstance);
        setContentView (R.layout.create_purchaseenquiry);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        Intent intent = getIntent ();
        type_enquiry = intent.getStringExtra ("type");
        source_enquiry = intent.getStringExtra ("source");
        loadItemAdapter ();
        podateCalendar = Calendar.getInstance ();
        enq_date = IFiveEngine.myInstance.getSimpleCalenderDate (podateCalendar);
        enquiryItemLists.add (new EnquiryItemList ());
        allDataLists.addAll (realm.where (AllDataList.class)
                .findAll ());
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
            int position = enquiryItemLists.size ();
            if (enquiryItemLists.get (position - 1).getOrdQty () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        enquiryItemLists.add (new EnquiryItemList ());
        enquiryLineAdapter.notifyItemInserted (position);
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
        enquiryLineAdapter = new EnquiryLineAdapter (this, enquiryItemLists, products, this);
        items_data.setAdapter (enquiryLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        enquiryLineAdapter.notifyDataSetChanged ();
        tempenquiryItemLists = enquiryItemLists;

    }

    public void setQuantity(int position, int quant) {
        enquiryItemLists.get (position).setOrdQty (String.valueOf (quant));
    }

    public void setProductList(int pos,int poId, String name, int uomId, String uomName) {
        enquiryItemLists.get (pos).setProductId (poId);
        enquiryItemLists.get (pos).setProduct (name);
        enquiryItemLists.get (pos).setUom (uomName);
        enquiryItemLists.get (pos).setUom_id (uomId);

    }

    public void setDuedate(int mPosition, String date) {
        enquiryItemLists.get (mPosition).setPromised_date (IFiveEngine.myInstance.formatDate (date));
    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = enquiryItemLists.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (enquiryItemLists.get (position - 1).getOrdQty () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        }  else {
            headerdraftSave ();
        }

    }


    @OnClick(R.id.submit_data)
    public void submitData() {
        int position = enquiryItemLists.size ();
        if (supplier_name.getText ().toString ().equals ("")) {
            supplier_name.setError ("Required");

        } else if (enquiryItemLists.get (position - 1).getOrdQty () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        }  else {
            headerSave ();
        }

    }

    private void headerSave() {
        Number currentIdNum = realm.where (PurchaseEnquiryData.class).max ("enquiryId");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        purchaseEnquiryData = new PurchaseEnquiryData ();
        purchaseEnquiryData.setEnquiryId (nextId);
        purchaseEnquiryData.setSupplierName (supplier_name.getText ().toString ());
        purchaseEnquiryData.setSupplierSiteName (supplier_site_name.getText ().toString ());
        purchaseEnquiryData.setEnquiryType (type_enquiry);
        purchaseEnquiryData.setEnquirySource (source_enquiry);
        purchaseEnquiryData.setSupplierId (String.valueOf (supplier_id));
        purchaseEnquiryData.setSupplierSitestatus ("Submit");
        purchaseEnquiryData.setSource ("Enquiry");
        purchaseEnquiryData.setOnlineStatus ("0");
        purchaseEnquiryData.setEnquiryDate (enq_date);
        purchaseEnquiryData.setEnquiryItemLists (tempenquiryItemLists);
        uploadLocalPurchase (purchaseEnquiryData);
    }

    private void headerdraftSave() {
        Number currentIdNum = realm.where (PurchaseEnquiryData.class).max ("enquiryId");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        purchaseEnquiryData = new PurchaseEnquiryData ();
        purchaseEnquiryData.setEnquiryId (nextId);
        purchaseEnquiryData.setSupplierName (supplier_name.getText ().toString ());
        purchaseEnquiryData.setSupplierSiteName (supplier_site_name.getText ().toString ());
        purchaseEnquiryData.setEnquiryType (type_enquiry);
        purchaseEnquiryData.setSupplierId (String.valueOf (supplier_id));
        purchaseEnquiryData.setSupplierSitestatus ("Draft");
        purchaseEnquiryData.setEnquiryDate (enq_date);
        purchaseEnquiryData.setOnlineStatus ("0");
        purchaseEnquiryData.setSource ("Enquiry");
        purchaseEnquiryData.setEnquirySource (source_enquiry);
        purchaseEnquiryData.setEnquiryItemLists (tempenquiryItemLists);
        uploadLocalPurchase (purchaseEnquiryData);

    }

    private void uploadLocalPurchase(final PurchaseEnquiryData purchaseEnquiryData) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        PurchaseEnquiryData allSalesOrder = realm.copyToRealmOrUpdate (purchaseEnquiryData);
                        realm.commitTransaction ();
                        Intent intent = new Intent (CreateEnquiryActivity.this, SubDashboard.class);
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


    @Override
    public void onItemPostion(int position) {

            String name = allDataLists.get (0).getSupplierList ().get (position).getSupplierName ();
            supplier_name.setText (name);
            supplier_site_name.setText (allDataLists.get (0).getSupplierList ().get (position).getSupplierAddress ());
            supplier_id = allDataLists.get (0).getSupplierList ().get (position).getSupplierTblId ();
            chartAlertDialog.dismiss ();


    }
}
