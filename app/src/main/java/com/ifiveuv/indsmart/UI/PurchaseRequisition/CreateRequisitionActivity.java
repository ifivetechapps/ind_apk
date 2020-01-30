package com.ifiveuv.indsmart.UI.PurchaseRequisition;

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
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Adapter.RequisitionLineAdapter;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionHeader;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionLines;
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

public class CreateRequisitionActivity extends BaseActivity implements SupplierListAdapter.onItemClickListner {
    @BindView(R.id.requestor_name)
    TextView requestor_name;
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
    int requestor_id;
    RequisitionLineAdapter requisitionLineAdapter;
    Menu menu;
    RequisitionHeader requisitionHeader;
    RealmList<RequisitionLines> requisitionLines = new RealmList<> ();
    RealmList<RequisitionLines> requisitionLinesRealmList;
    int nextId;
    Calendar reqDateCalendar;
    String req_date;
    List<AllDataList> allDataLists = new ArrayList<AllDataList> ();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate (savedInstance);
        setContentView (R.layout.create_purchaserequsition);
        ButterKnife.bind (this);

        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();

        loadLinesAdapter ();
        reqDateCalendar = Calendar.getInstance ();
        req_date = IFiveEngine.myInstance.getSimpleCalenderDate (reqDateCalendar);
        requisitionLines.add (new RequisitionLines ());
        allDataLists.addAll (realm.where (AllDataList.class).findAll ());

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
            int position = requisitionLines.size ();
            if (requisitionLines.get (position - 1).getOrdQty () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        requisitionLines.add (new RequisitionLines ());
        requisitionLineAdapter.notifyItemInserted (position);
    }

    @OnClick(R.id.requestor_name)
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


    private void loadLinesAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        requisitionLineAdapter = new RequisitionLineAdapter (this, requisitionLines, products, this);
        items_data.setAdapter (requisitionLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        requisitionLineAdapter.notifyDataSetChanged ();
        requisitionLinesRealmList = requisitionLines;
    }

    public void setQuantity(int position, int quant) {
        requisitionLines.get (position).setOrdQty (String.valueOf (quant));
    }

    public void setProductList(int pos,int poId, String name, int uomId, String uomName) {
        requisitionLines.get (pos).setProductId (String.valueOf (poId));
        requisitionLines.get (pos).setProduct (name);
        requisitionLines.get (pos).setUom (uomName);
        requisitionLines.get (pos).setUom_id (uomId);

    }

    public void setDuedate(int mPosition, String date) {
        requisitionLines.get (mPosition).setPromised_date (IFiveEngine.myInstance.formatDate (date));
    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            headerdraftSave ();
        }

    }


    @OnClick(R.id.submit_data)
    public void submitData() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            headerSave ();
        }

    }

    private void headerSave() {
        Number currentIdNum = realm.where (RequisitionHeader.class).max ("Hdrid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        requisitionHeader = new RequisitionHeader ();
        requisitionHeader.setHdrid (nextId);
        requisitionHeader.setRequestorName (requestor_name.getText ().toString ());
        requisitionHeader.setReqID (requestor_id);
        requisitionHeader.setRequestStatus ("Submit");
        requisitionHeader.setOnlineStatus ("0");
        requisitionHeader.setTypeRequisition ("Standard");
        requisitionHeader.setRequestDate (req_date);
        requisitionHeader.setRequisitionLines (requisitionLinesRealmList);
        uploadLocalPurchase (requisitionHeader);
    }

    private void headerdraftSave() {
        Number currentIdNum = realm.where (RequisitionHeader.class).max ("Hdrid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        requisitionHeader = new RequisitionHeader ();
        requisitionHeader.setHdrid (nextId);
        requisitionHeader.setRequestorName (requestor_name.getText ().toString ());
        requisitionHeader.setReqID (requestor_id);
        requisitionHeader.setOnlineStatus ("0");
        requisitionHeader.setTypeRequisition ("Standard");
        requisitionHeader.setRequestStatus ("Draft");
        requisitionHeader.setRequestDate (req_date);
        requisitionHeader.setRequisitionLines (requisitionLinesRealmList);
        uploadLocalPurchase (requisitionHeader);

    }

    private void uploadLocalPurchase(final RequisitionHeader requisitionHeader) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        RequisitionHeader allSalesOrder = realm.copyToRealmOrUpdate (requisitionHeader);
                        realm.commitTransaction ();
                        Intent intent = new Intent (CreateRequisitionActivity.this, SubDashboard.class);
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
        String Name =  allDataLists.get (0).getSupplierList ().get (position).getSupplierName ();
        int id =  allDataLists.get (0).getSupplierList ().get (position).getSupplierTblId ();
        requestor_name.setText (Name);
        requestor_name.setError (null);
        requestor_id = id;
        chartAlertDialog.dismiss ();
    }


}
