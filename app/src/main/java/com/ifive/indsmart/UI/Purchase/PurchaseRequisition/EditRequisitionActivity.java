package com.ifive.indsmart.UI.Purchase.PurchaseRequisition;

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

import com.ifive.indsmart.CommanAdapter.SupplierListAdapter;
import com.ifive.indsmart.Connectivity.AllDataList;
import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter.EditRequisitionLineAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionLines;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import java.util.ArrayList;
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

public class EditRequisitionActivity extends BaseActivity implements SupplierListAdapter.onItemClickListner {
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
    Menu menu;
    RequisitionHeader requisitionHeader;
    RealmList<RequisitionLines> linerequisition = new RealmList<> ();
    RealmList<RequisitionLines> requisitionLinesRealmList;
    int hdrid;

    String enq_date,type;
    EditRequisitionLineAdapter editRequisitionLineAdapter;
    List<AllDataList> allDataLists = new ArrayList<> ();

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
        Intent intent = getIntent ();
        hdrid = Integer.parseInt (intent.getStringExtra ("headerid"));
        type = intent.getStringExtra ("type");
        loadRequisitionItemAdapter ();
        if(type.equals ("edit")){
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    submitData();
                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    draftdata ();
                }
            });

        }else{
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    copysubmitData();
                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    copydraftdata ();
                }
            });
        }

        allDataLists.addAll (realm.where (AllDataList.class)
                .findAll ());
    }

    private void loadRequisitionItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        RealmResults<RequisitionHeader> results;
        results = realm.where (RequisitionHeader.class)
                .equalTo ("Hdrid", hdrid)
                .findAll ();
        requestor_name.setText (results.get (0).getRequestorName ());
        enq_date=results.get (0).getRequestDate ();
        linerequisition.addAll (results.get (0).getRequisitionLines ());
        editRequisitionLineAdapter = new EditRequisitionLineAdapter (this, linerequisition, products, this);
        items_data.setAdapter (editRequisitionLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        editRequisitionLineAdapter.notifyDataSetChanged ();
        requisitionLinesRealmList = linerequisition;

    }

    public void setQuantity(int position, int quant) {
        linerequisition.get (position).setOrdQty (String.valueOf (quant));
    }

    public void setProductList(int pos, int poId, String name, int uomId, String uomName) {
        linerequisition.get (pos).setProductId (String.valueOf (poId));
        linerequisition.get (pos).setProduct (name);
        linerequisition.get (pos).setUom (uomName);
        linerequisition.get (pos).setUom_id (uomId);

    }


    public void setDuedate(int mPosition, String date) {
        linerequisition.get (mPosition).setPromised_date (IFiveEngine.myInstance.formatDate (date));
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
            int position = linerequisition.size ();
            if (linerequisition.get (position - 1).getOrdQty () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        linerequisition.add (new RequisitionLines ());
        editRequisitionLineAdapter.notifyItemInserted (position);
    }

    public void copysubmitData() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            copyheaderSave ();
        }

    }
    public void copydraftdata() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            copyheaderdraftSave ();
        }

    }    public void submitData() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            headerSave ();
        }

    }
    public void draftdata() {
        if (requestor_name.getText ().toString ().equals ("")) {
            requestor_name.setError ("Required");

        } else {
            headerdraftSave ();
        }

    }
    private void copyheaderSave() {
        int nextId;
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
        requisitionHeader.setRequestDate (enq_date);
        requisitionHeader.setRequisitionLines (requisitionLinesRealmList);
        copyuploadLocalPurchase (requisitionHeader);
    }

    private void headerSave() {
        requisitionHeader = new RequisitionHeader ();
        requisitionHeader.setHdrid (hdrid);
        requisitionHeader.setRequestorName (requestor_name.getText ().toString ());
        requisitionHeader.setReqID (requestor_id);
        requisitionHeader.setRequestStatus ("Submit");
        requisitionHeader.setOnlineStatus ("0");
        requisitionHeader.setTypeRequisition ("Standard");
        requisitionHeader.setRequestDate (enq_date);
        requisitionHeader.setRequisitionLines (requisitionLinesRealmList);
        uploadLocalPurchase (requisitionHeader);
    }

    private void copyheaderdraftSave() {
        int nextId;
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
        requisitionHeader.setRequestStatus ("Draft");
        requisitionHeader.setRequestDate (enq_date);
        requisitionHeader.setOnlineStatus ("0");
        requisitionHeader.setTypeRequisition ("Standard");
        requisitionHeader.setRequisitionLines (requisitionLinesRealmList);
        copyuploadLocalPurchase (requisitionHeader);

    }
    private void headerdraftSave() {
        requisitionHeader = new RequisitionHeader ();
        requisitionHeader.setHdrid (hdrid);
        requisitionHeader.setRequestorName (requestor_name.getText ().toString ());
        requisitionHeader.setReqID (requestor_id);
        requisitionHeader.setRequestStatus ("Draft");
        requisitionHeader.setRequestDate (enq_date);
        requisitionHeader.setOnlineStatus ("0");
        requisitionHeader.setTypeRequisition ("Standard");
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
                        Intent intent = new Intent (EditRequisitionActivity.this, SubDashboard.class);
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
    private void copyuploadLocalPurchase(final RequisitionHeader requisitionHeader) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        RequisitionHeader allSalesOrder = realm.copyToRealm (requisitionHeader);
                        realm.commitTransaction ();
                        Intent intent = new Intent (EditRequisitionActivity.this, SubDashboard.class);
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
