package com.ifive.indsmart.UI.Purchase.PurchaseEnquiry;

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
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ifive.indsmart.Connectivity.EnquiryResponse;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Adapter.ListEnquiryAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

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

public class ListEnquiryActivity extends BaseActivity {
    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.nodata)
    TextView nodata;
    List<PurchaseEnquiryData> enquiryItemLists;
    private FloatingActionButton createStandard, createLabour, fromenquiry;
    private FloatingActionMenu fam;
    ActionBar actionBar;
    Realm realm;
    int status,typeid;
    ProgressDialog progressDialog;
    PurchaseEnquiryData purchaseEnquiryData;
    EnquiryResponse enquiryResponse;
    SessionManager sessionManager;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchaseenquiry);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();

        fromenquiry = (FloatingActionButton) findViewById (R.id.from_enquiry);
        createStandard = (FloatingActionButton) findViewById (R.id.create_standard);
        createLabour = (FloatingActionButton) findViewById (R.id.create_labour);

        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);

        createStandard.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());
        fromenquiry.setOnClickListener (onButtonClick ());
        sessionManager=new SessionManager ();
        progressDialog = IFiveEngine.getProgDialog (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<PurchaseEnquiryData> results;
        results = realm.where (PurchaseEnquiryData.class)
                .findAll ();

        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            ListEnquiryAdapter adapter = new ListEnquiryAdapter (this,results, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

            salesorder_list.setLayoutManager (linearLayoutManager);

            salesorder_list.setAdapter (adapter);
        }

    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (view == createStandard) {
                    Intent i = new Intent (ListEnquiryActivity.this, CreateEnquiryActivity.class);
                    i.putExtra ("type", "standard");
                    i.putExtra ("source", "enquiry");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (ListEnquiryActivity.this, CreateEnquiryActivity.class);
                    i.putExtra ("type", "labour");
                    i.putExtra ("source", "enquiry");
                    startActivity (i);
                } else if (view == fromenquiry) {
                    Intent i = new Intent (ListEnquiryActivity.this, RequisitionList.class);
                    startActivity (i);
                } else {
                    Toast.makeText (ListEnquiryActivity.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ListEnquiryActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
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
        RealmResults<PurchaseEnquiryData> results;
        results = realm.where (PurchaseEnquiryData.class)
                .notEqualTo ("onlineStatus", "1")
                .and ()
                .equalTo ("status", "Submit")
                .findAll ();
        checkData (results);
    }

    private void checkData(RealmResults<PurchaseEnquiryData> results) {

        int count = results.size ();
        if (results.size () != 0) {
            for (int i = 0; i < results.size (); i++) {
                count--;
            }
            onlineSyncMethodAll (results.get (count).getEnquiryId ());

        } else {
            Intent intent = new Intent (ListEnquiryActivity.this, ListEnquiryActivity.class);
            startActivity (intent);
            Toast.makeText (this, "No data to Sync", Toast.LENGTH_SHORT).show ();


        }


    }
    public void onlineSyncMethodAll(final int enquiryId) {
        Toast.makeText (ListEnquiryActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (enquiryId));
        enquiryItemLists = new ArrayList<> ();
        enquiryItemLists.addAll (realm.copyFromRealm (realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId)
                .findAll ()));
        if (enquiryItemLists.get (0).getSupplierSitestatus ().equals ("Submit")) {
            status = 2;
        }
        if (enquiryItemLists.get (0).getEnquiryType ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        purchaseEnquiryData = new PurchaseEnquiryData ();
        purchaseEnquiryData.setEnquiryId (enquiryItemLists.get (0).getEnquiryId ());
        purchaseEnquiryData.setSupplierSitestatus (String.valueOf (status));
        purchaseEnquiryData.setEnquiryType (String.valueOf (typeid));
        purchaseEnquiryData.setSupplierId (enquiryItemLists.get (0).getSupplierId ());
        purchaseEnquiryData.setEnquiryDate (IFiveEngine.myInstance.formatDate (enquiryItemLists.get (0).getEnquiryDate ()));
        purchaseEnquiryData.setSupplierName (enquiryItemLists.get (0).getSupplierName ());
        purchaseEnquiryData.setSupplierSiteName (enquiryItemLists.get (0).getSupplierSiteName ());
        purchaseEnquiryData.setSource (enquiryItemLists.get (0).getSource ());
        purchaseEnquiryData.setEnquiryItemLists (enquiryItemLists.get (0).getEnquiryItemLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendPurachseEnquirySingleData (sessionManager.getToken (this), purchaseEnquiryData);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (enquiryId));
                            PurchaseEnquiryData purchaseEnquiryData = realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId).findFirst ();
                            purchaseEnquiryData.setOnlineStatus ("1");
                            purchaseEnquiryData.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (ListEnquiryActivity.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                             sendAllData ();
                            Intent intent = new Intent (ListEnquiryActivity.this, ListEnquiryActivity.class);
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
                    Toast.makeText (ListEnquiryActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }
    }
    public void onlineSyncMethodSingle(final int enquiryId) {
        Toast.makeText (ListEnquiryActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (enquiryId));
        enquiryItemLists = new ArrayList<> ();
        enquiryItemLists.addAll (realm.copyFromRealm (realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId)
                .findAll ()));
        if (enquiryItemLists.get (0).getSupplierSitestatus ().equals ("Submit")) {
            status = 2;
        }
        if (enquiryItemLists.get (0).getEnquiryType ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        purchaseEnquiryData = new PurchaseEnquiryData ();
        purchaseEnquiryData.setEnquiryId (enquiryItemLists.get (0).getEnquiryId ());
        purchaseEnquiryData.setSupplierSitestatus (String.valueOf (status));
        purchaseEnquiryData.setEnquiryType (String.valueOf (typeid));
        purchaseEnquiryData.setSupplierId (enquiryItemLists.get (0).getSupplierId ());
        purchaseEnquiryData.setSource (enquiryItemLists.get (0).getSource ());
        purchaseEnquiryData.setEnquiryDate (IFiveEngine.myInstance.formatDate (enquiryItemLists.get (0).getEnquiryDate ()));
        purchaseEnquiryData.setSupplierName (enquiryItemLists.get (0).getSupplierName ());
        purchaseEnquiryData.setSupplierSiteName (enquiryItemLists.get (0).getSupplierSiteName ());

        purchaseEnquiryData.setEnquiryItemLists (enquiryItemLists.get (0).getEnquiryItemLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendPurachseEnquirySingleData (sessionManager.getToken (this), purchaseEnquiryData);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (enquiryId));
                            PurchaseEnquiryData purchaseEnquiryData = realm.where (PurchaseEnquiryData.class).equalTo ("enquiryId", enquiryId).findFirst ();
                            purchaseEnquiryData.setOnlineStatus ("1");
                            purchaseEnquiryData.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (ListEnquiryActivity.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                            //  sendAllData ();
                            Intent intent = new Intent (ListEnquiryActivity.this, ListEnquiryActivity.class);
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
                    Toast.makeText (ListEnquiryActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }
    }
}
