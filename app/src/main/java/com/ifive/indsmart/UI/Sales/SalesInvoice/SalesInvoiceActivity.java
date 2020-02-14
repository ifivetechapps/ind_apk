package com.ifive.indsmart.UI.Sales.SalesInvoice;

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
import com.ifive.indsmart.Connectivity.EnquiryResponse;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Sales.SalesCreate.CreateSalesActivity;
import com.ifive.indsmart.UI.Sales.SalesInvoice.Adapter.InvoiceListAdapter;
import com.ifive.indsmart.UI.Sales.SalesInvoice.Model.InvoiceItemList;
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

public class SalesInvoiceActivity extends BaseActivity {

    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    ActionBar actionBar;
    Menu menu;
    Realm realm;
    InvoiceItemList invoiceItemList;
    List<InvoiceItemList> invoiceItemLists;
    private FloatingActionMenu fam;
    ProgressDialog progressDialog;
    EnquiryResponse enquiryResponse;
    private FloatingActionButton fromSo, fromDispatch;
    int status, typeid;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sales_invoice);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();

        fromSo = findViewById (R.id.invoice_from_so);
        fromDispatch = findViewById (R.id.invoice_from_dispatch);
        fam = findViewById (R.id.fab_menu);
        fromSo.setOnClickListener (onButtonClick ());
        fromDispatch.setOnClickListener (onButtonClick ());
        sessionManager = new SessionManager ();
        progressDialog = IFiveEngine.getProgDialog (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<InvoiceItemList> results;
        results = realm.where (InvoiceItemList.class)
                .findAll ();
        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            InvoiceListAdapter adapter = new InvoiceListAdapter (this, results, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

            salesorder_list.setLayoutManager (linearLayoutManager);

            salesorder_list.setAdapter (adapter);
        }

    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (view == fromSo) {
                    Intent i = new Intent (SalesInvoiceActivity.this, SalesOrdertoInvoice.class);
                    i.putExtra ("type", "SALESORDER");
                    startActivity (i);
                } else if (view == fromDispatch) {
                    Intent i = new Intent (SalesInvoiceActivity.this, CreateSalesActivity.class);
                    i.putExtra ("type", "DISPATCH");
                    startActivity (i);
                    fam.close (true);
                } else {
                    Toast.makeText (SalesInvoiceActivity.this, "Please Select ", Toast.LENGTH_SHORT).show ();
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
        RealmResults<InvoiceItemList> results;
        results = realm.where (InvoiceItemList.class)
                .notEqualTo ("onlinestatus", "1")
                .and ()
                .equalTo ("status", "Submitted")
                .findAll ();
        checkData (results);


    }

    private void checkData(RealmResults<InvoiceItemList> results) {

        int count = results.size ();
        if (results.size () != 0) {
            for (int i = 0; i < results.size (); i++) {
                count--;
            }
            onlineSyncMethodAll (results.get (count).getInvoiceid ());

        } else {
            Intent intent = new Intent (SalesInvoiceActivity.this, SalesInvoiceActivity.class);
            startActivity (intent);
            Toast.makeText (this, "No data to Sync", Toast.LENGTH_SHORT).show ();


        }


    }

    public void onlineSyncMethodAll(final Integer soID) {
        Toast.makeText (SalesInvoiceActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (soID));
        invoiceItemLists = new ArrayList<> ();
        invoiceItemLists.addAll (realm.copyFromRealm (realm.where (InvoiceItemList.class).equalTo ("invoiceid", soID)
                .findAll ()));
        if (invoiceItemLists.get (0).getStatus ().equals ("Submitted")) {
            status = 2;
        }
        if (invoiceItemLists.get (0).getTypeOfOrder ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        invoiceItemList = new InvoiceItemList ();
        invoiceItemList.setInvoiceid (invoiceItemLists.get (0).getInvoiceid ());
        invoiceItemList.setSo_number (Integer.parseInt (invoiceItemLists.get (0).getOnlineId ()));
        invoiceItemList.setStatus (String.valueOf (status));
        invoiceItemList.setTypeOfOrder (String.valueOf (typeid));
        invoiceItemList.setCus_id (invoiceItemLists.get (0).getCus_id ());
        invoiceItemList.setInvDate (IFiveEngine.myInstance.formatDate (invoiceItemLists.get (0).getInvDate ()));
        invoiceItemList.setDel_date (IFiveEngine.myInstance.formatDate (invoiceItemLists.get (0).getDel_date ()));
        invoiceItemList.setTotalPrice (invoiceItemLists.get (0).getTotalPrice ());
        invoiceItemList.setDescription (invoiceItemLists.get (0).getDescription ());
        invoiceItemList.setGrossTotal (invoiceItemLists.get (0).getGrossTotal ());
        invoiceItemList.setFreightpay (invoiceItemLists.get (0).getFreightpay ());
        invoiceItemList.setTaxType (invoiceItemLists.get (0).getTaxType ());
        invoiceItemList.setTaxValue (invoiceItemLists.get (0).getTaxValue ());
        invoiceItemList.setTaxAmt (invoiceItemLists.get (0).getTaxAmt ());
        invoiceItemList.setFreightcost (invoiceItemLists.get (0).getFreightcost ());
        invoiceItemList.setDeliveryaddress (invoiceItemLists.get (0).getDeliveryaddress ());
        invoiceItemList.setInvoiceItemLinelists (invoiceItemLists.get (0).getInvoiceItemLinelists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendSalesInvoiceSingleData (sessionManager.getToken (this), invoiceItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (soID));
                            InvoiceItemList saleItemList = realm.where (InvoiceItemList.class).equalTo ("invoiceid", soID).findFirst ();
                            saleItemList.setOnlineStatus ("1");
                            saleItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (SalesInvoiceActivity.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                            sendAllData ();
                        }
                    });



                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<EnquiryResponse> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Toast.makeText (SalesInvoiceActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    public void onlineSyncMethodSingle(final Integer soID) {
        Toast.makeText (SalesInvoiceActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (soID));
        invoiceItemLists = new ArrayList<> ();
        invoiceItemLists.addAll (realm.copyFromRealm (realm.where (InvoiceItemList.class).equalTo ("invoiceid", soID)
                .findAll ()));
        if (invoiceItemLists.get (0).getStatus ().equals ("Submitted")) {
            status = 2;
        }
        if (invoiceItemLists.get (0).getTypeOfOrder ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        invoiceItemList = new InvoiceItemList ();
        invoiceItemList.setInvoiceid (invoiceItemLists.get (0).getInvoiceid ());
        invoiceItemList.setSo_number (invoiceItemLists.get (0).getSo_number ());
        invoiceItemList.setStatus (String.valueOf (status));
        invoiceItemList.setTypeOfOrder (String.valueOf (typeid));
        invoiceItemList.setCus_id (invoiceItemLists.get (0).getCus_id ());
        invoiceItemList.setInvDate (IFiveEngine.myInstance.formatDate (invoiceItemLists.get (0).getInvDate ()));
        invoiceItemList.setDel_date (IFiveEngine.myInstance.formatDate (invoiceItemLists.get (0).getDel_date ()));
        invoiceItemList.setTotalPrice (invoiceItemLists.get (0).getTotalPrice ());
        invoiceItemList.setDescription (invoiceItemLists.get (0).getDescription ());
        invoiceItemList.setGrossTotal (invoiceItemLists.get (0).getGrossTotal ());
        invoiceItemList.setFreightpay (invoiceItemLists.get (0).getFreightpay ());
        invoiceItemList.setTaxType (invoiceItemLists.get (0).getTaxType ());
        invoiceItemList.setTaxValue (invoiceItemLists.get (0).getTaxValue ());
        invoiceItemList.setTaxAmt (invoiceItemLists.get (0).getTaxAmt ());
        invoiceItemList.setFreightcost (invoiceItemLists.get (0).getFreightcost ());
        invoiceItemList.setDeliveryaddress (invoiceItemLists.get (0).getDeliveryaddress ());
        invoiceItemList.setInvoiceItemLinelists (invoiceItemLists.get (0).getInvoiceItemLinelists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendSalesInvoiceSingleData (sessionManager.getToken (this), invoiceItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (soID));
                            InvoiceItemList saleItemList = realm.where (InvoiceItemList.class).equalTo ("invoiceid", soID).findFirst ();
                            saleItemList.setOnlineStatus ("1");
                            saleItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (SalesInvoiceActivity.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                           // sendAllData ();
                            Intent intent = new Intent (SalesInvoiceActivity.this, SalesInvoiceActivity.class);
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
                    Toast.makeText (SalesInvoiceActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (SalesInvoiceActivity.this, SubDashboard.class);
        it.putExtra ("type", "Sales");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
