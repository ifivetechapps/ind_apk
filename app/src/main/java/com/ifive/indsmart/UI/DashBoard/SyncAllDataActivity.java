package com.ifive.indsmart.UI.DashBoard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ifive.indsmart.Connectivity.AllDataList;
import com.ifive.indsmart.Connectivity.ProductCategory;
import com.ifive.indsmart.Connectivity.ProductGroup;
import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.Shipping_address;
import com.ifive.indsmart.Connectivity.Tax_type;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Masters.Model.CustomerList;
import com.ifive.indsmart.UI.Masters.Model.InventoryType;
import com.ifive.indsmart.UI.Masters.Model.SubInventory;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;
import com.ifive.indsmart.UI.Masters.Model.UomModel;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionHeader;
import com.ifive.indsmart.UI.Sales.OnlineModel.OnlineEnquiryItemModel;
import com.ifive.indsmart.UI.Sales.OnlineModel.OnlineEnquiryLineList;
import com.ifive.indsmart.UI.Sales.OnlineModel.SalesEnquiryOrderAll;
import com.ifive.indsmart.UI.Sales.OnlineModel.SalesOrderAll;
import com.ifive.indsmart.UI.Sales.OnlineModel.SalesQuoteOrderAll;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoDetailTable;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoHeaderTable;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoHeaderquoteDetail;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoQuoteDetail;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SyncAllDataActivity extends BaseActivity {
    Realm realm;
    AllDataList allDataList;
    private SessionManager sessionManager;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sync_activity);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        sessionManager = new SessionManager ();
        pDialog = IFiveEngine.getProgDialog (this);
        RealmResults<AllDataList> allDataLists = realm.where(AllDataList.class).findAll();
        RealmResults<SupplierList> supplierLists = realm.where(SupplierList.class).findAll();
        RealmResults<CustomerList> customerLists = realm.where(CustomerList.class).findAll();
        RealmResults<Tax_type> tax_types = realm.where(Tax_type.class).findAll();
        RealmResults<Shipping_address> shipping_addresses = realm.where(Shipping_address.class).findAll();
        RealmResults<SubInventory> subInventories = realm.where(SubInventory.class).findAll();
        RealmResults<InventoryType> inventoryTypes = realm.where(InventoryType.class).findAll();
        RealmResults<UomModel> uomModels = realm.where(UomModel.class).findAll();
        RealmResults<ProductGroup>productGroups= realm.where(ProductGroup.class).findAll();
        RealmResults<ProductCategory> productCategories = realm.where(ProductCategory.class).findAll();
        RealmResults<Products> products = realm.where(Products.class).findAll();
        RealmResults<SalesEnquiryOrderAll> salesEnquiryOrderAlls = realm.where(SalesEnquiryOrderAll.class).findAll();
        RealmResults<OnlineEnquiryItemModel> onlineEnquiryItemModels = realm.where(OnlineEnquiryItemModel.class).findAll();
        RealmResults<OnlineEnquiryLineList> onlineEnquiryLineLists = realm.where(OnlineEnquiryLineList.class).findAll();
        RealmResults<SalesQuoteOrderAll> salesQuoteOrderAlls = realm.where(SalesQuoteOrderAll.class).findAll();
        RealmResults<SoHeaderquoteDetail> soHeaderquoteDetails = realm.where(SoHeaderquoteDetail.class).findAll();
        RealmResults<SoQuoteDetail> soQuoteDetails = realm.where(SoQuoteDetail.class).findAll();
        RealmResults<SalesOrderAll> salesOrderAlls = realm.where(SalesOrderAll.class).findAll();
        RealmResults<SoHeaderTable> soHeaderTables = realm.where(SoHeaderTable.class).findAll();
        RealmResults<SoDetailTable> soDetailTables = realm.where(SoDetailTable.class).findAll();
        realm.beginTransaction();
        allDataLists.deleteAllFromRealm ();
        supplierLists.deleteAllFromRealm ();
        customerLists.deleteAllFromRealm ();
        tax_types.deleteAllFromRealm ();
        shipping_addresses.deleteAllFromRealm ();
        subInventories.deleteAllFromRealm ();
        inventoryTypes.deleteAllFromRealm ();
        uomModels.deleteAllFromRealm ();
        productCategories.deleteAllFromRealm ();
        productGroups.deleteAllFromRealm ();
        products.deleteAllFromRealm ();
        salesEnquiryOrderAlls.deleteAllFromRealm ();
        salesQuoteOrderAlls.deleteAllFromRealm ();
        salesOrderAlls.deleteAllFromRealm ();
        onlineEnquiryItemModels.deleteAllFromRealm ();
        onlineEnquiryLineLists.deleteAllFromRealm ();
        soHeaderquoteDetails.deleteAllFromRealm ();
        soQuoteDetails.deleteAllFromRealm ();
        soHeaderTables.deleteAllFromRealm ();
        soDetailTables.deleteAllFromRealm ();
        realm.commitTransaction ();
        loadData ();
    }

    public void loadData() {

            if (IFiveEngine.isNetworkAvailable (this)) {
                pDialog.show ();
                UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
                Call<AllDataList> callEnqueue = userAPICall.allDataList (sessionManager.getToken (this));
                callEnqueue.enqueue (new Callback<AllDataList> () {
                    @Override
                    public void onResponse(Call<AllDataList> call, Response<AllDataList> response) {
                        uploadToRealmDB (response.body ());
                        if ((pDialog != null) && pDialog.isShowing ())
                            pDialog.dismiss ();
                    }

                    @Override
                    public void onFailure(Call<AllDataList> call, Throwable t) {
                        if ((pDialog != null) && pDialog.isShowing ())
                            pDialog.dismiss ();
                        Log.d ("throwing Error", t.getMessage ());
                        Toast.makeText (SyncAllDataActivity.this, "Sync incomplete", Toast.LENGTH_SHORT).show ();
                    }
                });
            } else {
                IFiveEngine.myInstance.snackbarNoInternet (this);
            }

    }

    private void uploadToRealmDB(final AllDataList body) {

        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        AllDataList allDatasResponse = realm.copyToRealm (body);
                        realm.commitTransaction ();
                        Toast.makeText (SyncAllDataActivity.this, "Master Sync Completed.", Toast.LENGTH_SHORT).show ();
                        loadingEnquiry ();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d ("Test", "In onError()");
                        // openMainActivity();
                        Toast.makeText (SyncAllDataActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show ();

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
    private void loadingEnquiry() {


            if (IFiveEngine.isNetworkAvailable(this)) {
                pDialog.show();
                UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
                Call<SalesEnquiryOrderAll> callEnqueue = userAPICall.allenquiry(sessionManager.getToken(this));
                callEnqueue.enqueue(new Callback<SalesEnquiryOrderAll> () {
                    @Override
                    public void onResponse(Call<SalesEnquiryOrderAll> call, Response<SalesEnquiryOrderAll> response) {
                        uploadToRealmDB(response.body());
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<SalesEnquiryOrderAll> call, Throwable t) {
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                        Log.d("throwing Error", t.getMessage());
                        Toast.makeText(SyncAllDataActivity.this, "Sync incomplete", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                IFiveEngine.myInstance.snackbarNoInternet(this);
            }



    }
    private void uploadToRealmDB(final SalesEnquiryOrderAll body) {

        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        SalesEnquiryOrderAll allDatasResponse = realm.copyToRealm(body);
                        realm.commitTransaction();
                        Toast.makeText(SyncAllDataActivity.this, "Enquiry Sync Completed.", Toast.LENGTH_SHORT).show();
                        loadingQuote();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "In onError()");
                        Toast.makeText(SyncAllDataActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                            }
                        });
                    }
                });

    }
    private void loadingQuote() {


            if (IFiveEngine.isNetworkAvailable(this)) {
                pDialog.show();
                UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
                Call<SalesQuoteOrderAll> callEnqueue = userAPICall.allquote(sessionManager.getToken(this));
                callEnqueue.enqueue(new Callback<SalesQuoteOrderAll> () {
                    @Override
                    public void onResponse(Call<SalesQuoteOrderAll> call, Response<SalesQuoteOrderAll> response) {
                        uploadToRealmDB(response.body());
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<SalesQuoteOrderAll> call, Throwable t) {
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                        Log.d("throwing Error", t.getMessage());
                        Toast.makeText(SyncAllDataActivity.this, "Sync incomplete", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                IFiveEngine.myInstance.snackbarNoInternet(this);
            }



    }
    private void uploadToRealmDB(final SalesQuoteOrderAll body) {

        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        SalesQuoteOrderAll allDatasResponse = realm.copyToRealm(body);
                        realm.commitTransaction();
                        Toast.makeText(SyncAllDataActivity.this, "Quote Sync Completed.", Toast.LENGTH_SHORT).show();
                        loadingSales();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "In onError()");
                        Toast.makeText(SyncAllDataActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                            }
                        });
                    }
                });

    }
    private void loadingSales() {

            if (IFiveEngine.isNetworkAvailable(this)) {
                pDialog.show();
                UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
                Call<SalesOrderAll> callEnqueue = userAPICall.allsales(sessionManager.getToken(this));
                callEnqueue.enqueue(new Callback<SalesOrderAll> () {
                    @Override
                    public void onResponse(Call<SalesOrderAll> call, Response<SalesOrderAll> response) {
                        uploadToRealmDB(response.body());
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<SalesOrderAll> call, Throwable t) {
                        if ((pDialog != null) && pDialog.isShowing())
                            pDialog.dismiss();
                        Log.d("throwing Error", t.getMessage());
                        Toast.makeText(SyncAllDataActivity.this, "Sync incomplete", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                IFiveEngine.myInstance.snackbarNoInternet(this);
            }



    }
    private void uploadToRealmDB(final SalesOrderAll body) {

        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        SalesOrderAll allDatasResponse = realm.copyToRealm(body);
                        realm.commitTransaction();
                        Toast.makeText(SyncAllDataActivity.this, "Sales Sync Completed.", Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "In onError()");
                        Toast.makeText(SyncAllDataActivity.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                            }
                        });
                    }
                });

    }

}
