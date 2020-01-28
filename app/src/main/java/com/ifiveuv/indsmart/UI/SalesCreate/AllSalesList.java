package com.ifiveuv.indsmart.UI.SalesCreate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ifiveuv.indsmart.Connectivity.EnquiryResponse;
import com.ifiveuv.indsmart.Engine.RetroFitEngine;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Connectivity.UserAPICall;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.SalesCreate.Adapter.AllSalesListAdapter;
import com.ifiveuv.indsmart.UI.SalesCreate.Model.SaleItemList;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSalesList extends BaseActivity {
    ActionBar actionBar;
    SessionManager sessionManager;
    Realm realm;
    @BindView(R.id.salesorder_list)
    RecyclerView poorderlist;
    ProgressDialog progressDialog;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    private FloatingActionMenu fam;
    private FloatingActionButton createStandard, createLabour, fromenquiry,fromquote;
    Menu menu;
    List<SaleItemList> saleItemLists;
    int typeid, status;
    SaleItemList saleItemList;
    EnquiryResponse enquiryResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sales_list);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        createStandard = findViewById (R.id.create_standard);
        createLabour = findViewById (R.id.create_labour);
        fromenquiry = findViewById (R.id.from_enquiry);
        fromquote = findViewById (R.id.from_quote);
        fam = findViewById (R.id.fab_menu);
        createStandard.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());
        fromenquiry.setOnClickListener (onButtonClick ());
        fromquote.setOnClickListener (onButtonClick ());
        sessionManager = new SessionManager ();
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        progressDialog = IFiveEngine.getProgDialog (this);
        RealmResults<SaleItemList> results;
        results = realm.where (SaleItemList.class)
                .findAll ();
        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            AllSalesListAdapter adapter = new AllSalesListAdapter (this, results, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);
            poorderlist.setLayoutManager (linearLayoutManager);
            poorderlist.setAdapter (adapter);
        }


    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (view == createStandard) {
                    Intent i = new Intent (AllSalesList.this, CreateSalesActivity.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (AllSalesList.this, CreateSalesActivity.class);
                    i.putExtra ("type", "labour");
                    startActivity (i);
                    fam.close (true);
                } else if (view == fromenquiry) {
                    Intent i = new Intent (AllSalesList.this, SalesConvertFromEnquiry.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                    fam.close (true);
                } else if (view == fromquote) {
                    Intent i = new Intent (AllSalesList.this, SalesConvertFromQuote.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                    fam.close (true);
                } else {
                    Toast.makeText (AllSalesList.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater ().inflate (R.menu.sync_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId ();
        Log.d ("LAALALAL", String.valueOf (id));
        Log.d ("LAALALAL123", String.valueOf (R.id.sync_item_black));
        if (id == R.id.sync_item_black) {
            sendAllData ();
        }
        return super.onOptionsItemSelected (item);
    }

    private void sendAllData() {
        RealmResults<SaleItemList> results;
        results = realm.where (SaleItemList.class)
                .notEqualTo ("onlinestatus", "1")
                .and ()
                .equalTo ("status", "Submitted")
                .findAll ();
        checkData (results);
    }

    private void checkData(RealmResults<SaleItemList> results) {

        int count = results.size ();
        if (results.size () != 0) {
            for (int i = 0; i < results.size (); i++) {
                count--;
            }
            onlineSyncMethodAll (results.get (count).getSalesOrderid ());

        } else {
            Intent intent = new Intent (AllSalesList.this, AllSalesList.class);
            startActivity (intent);
            Toast.makeText (this, "No data to Sync", Toast.LENGTH_SHORT).show ();


        }


    }

    public void onlineSyncMethodAll(final Integer soID) {
        Toast.makeText (AllSalesList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (soID));
        saleItemLists = new ArrayList<> ();
        saleItemLists.addAll (realm.copyFromRealm (realm.where (SaleItemList.class).equalTo ("SalesOrderid", soID)
                .findAll ()));
        if (saleItemLists.get (0).getStatus ().equals ("Submitted")) {
            status = 2;
        }
        if (saleItemLists.get (0).getTypeOfOrder ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        saleItemList = new SaleItemList ();
        saleItemList.setSalesOrderid (saleItemLists.get (0).getSalesOrderid ());
        saleItemList.setStatus (String.valueOf (status));
        saleItemList.setTypeOfOrder (String.valueOf (typeid));
        saleItemList.setCus_id (saleItemLists.get (0).getCus_id ());
        saleItemList.setSodate (IFiveEngine.myInstance.formatDate (saleItemLists.get (0).getSodate ()));
        saleItemList.setDel_date (IFiveEngine.myInstance.formatDate (saleItemLists.get (0).getDel_date ()));
        saleItemList.setTotalPrice (saleItemLists.get (0).getTotalPrice ());
        saleItemList.setNetPrice (saleItemLists.get (0).getNetPrice ());
        saleItemList.setTaxType (saleItemLists.get (0).getTaxType ());
        saleItemList.setTaxValue (saleItemLists.get (0).getTaxValue ());
        saleItemList.setTotalTax (saleItemLists.get (0).getTotalTax ());
        saleItemList.setOnlineseId (saleItemLists.get (0).getOnlineseId ());
        saleItemList.setOnlinesqId (saleItemLists.get (0).getOnlinesqId ());
        saleItemList.setSalesItemLineLists (saleItemLists.get (0).getSalesItemLineLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendSalesSingleData (sessionManager.getToken (this), saleItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (soID));
                            SaleItemList saleItemList = realm.where (SaleItemList.class).equalTo ("SalesOrderid", soID).findFirst ();
                            saleItemList.setOnlinestatus ("1");
                            saleItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (AllSalesList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                           sendAllData ();
                            Intent intent = new Intent (AllSalesList.this, AllSalesList.class);
                            startActivity (intent);

                        }
                    });



                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<EnquiryResponse> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Toast.makeText (AllSalesList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    public void onlineSyncMethodSingle(final Integer soID) {
        Toast.makeText (AllSalesList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (soID));
        saleItemLists = new ArrayList<> ();
        saleItemLists.addAll (realm.copyFromRealm (realm.where (SaleItemList.class).equalTo ("SalesOrderid", soID)
                .findAll ()));
        if (saleItemLists.get (0).getStatus ().equals ("Submitted")) {
            status = 2;
        }
        if (saleItemLists.get (0).getTypeOfOrder ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        saleItemList = new SaleItemList ();
        saleItemList.setSalesOrderid (saleItemLists.get (0).getSalesOrderid ());
        saleItemList.setStatus (String.valueOf (status));
        saleItemList.setTypeOfOrder (String.valueOf (typeid));
        saleItemList.setCus_id (saleItemLists.get (0).getCus_id ());
        saleItemList.setSodate (IFiveEngine.myInstance.formatDate (saleItemLists.get (0).getSodate ()));
        saleItemList.setDel_date (IFiveEngine.myInstance.formatDate (saleItemLists.get (0).getDel_date ()));
        saleItemList.setTotalPrice (saleItemLists.get (0).getTotalPrice ());
        saleItemList.setNetPrice (saleItemLists.get (0).getNetPrice ());
        saleItemList.setTaxType (saleItemLists.get (0).getTaxType ());
        saleItemList.setTaxValue (saleItemLists.get (0).getTaxValue ());
        saleItemList.setTotalTax (saleItemLists.get (0).getTotalTax ());
        saleItemList.setOnlineseId (saleItemLists.get (0).getOnlineseId ());
        saleItemList.setOnlinesqId (saleItemLists.get (0).getOnlinesqId ());
        saleItemList.setSalesItemLineLists (saleItemLists.get (0).getSalesItemLineLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendSalesSingleData (sessionManager.getToken (this), saleItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (soID));
                            SaleItemList saleItemList = realm.where (SaleItemList.class).equalTo ("SalesOrderid", soID).findFirst ();
                            saleItemList.setOnlinestatus ("1");
                            saleItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (AllSalesList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                          //  sendAllData ();
                            Intent intent = new Intent (AllSalesList.this, AllSalesList.class);
                            startActivity (intent);
                        }
                    });



                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<EnquiryResponse> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Toast.makeText (AllSalesList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    @Override
    public void onBackPressed() {
        Intent it = new Intent(AllSalesList.this, Dashboard.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

}
