package com.ifiveuv.indsmart.UI.Sales.SalesQuote;

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
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Connectivity.UserAPICall;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.Engine.RetroFitEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.AllQuoteListAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllQuoteList extends BaseActivity {
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
    private FloatingActionButton createStandard, createLabour, fromenquiry;
    Menu menu;
    List<QuoteItemList> quoteItemModels;
    int typeid, status;
    QuoteItemList quoteItemList;
    EnquiryResponse enquiryResponse;
    RealmList<QuoteItemLineList> quoteItemLineList = new RealmList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_quote_list);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        createStandard = findViewById (R.id.create_standard);
        createLabour = findViewById (R.id.create_labour);
        fromenquiry = findViewById (R.id.from_enquiry);
        fam = findViewById (R.id.fab_menu);
        createStandard.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());
        fromenquiry.setOnClickListener (onButtonClick ());
        sessionManager = new SessionManager ();
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        progressDialog = IFiveEngine.getProgDialog (this);

        RealmResults<QuoteItemList> results;
        results = realm.where (QuoteItemList.class)
                .findAll ();
        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            AllQuoteListAdapter adapter = new AllQuoteListAdapter (this, results, this);
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
                    Intent i = new Intent (AllQuoteList.this, CreateSalesQuote.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (AllQuoteList.this, CreateSalesQuote.class);
                    i.putExtra ("type", "labour");
                    startActivity (i);
                    fam.close (true);
                } else if (view == fromenquiry) {
                    Intent i = new Intent (AllQuoteList.this, ConvertFromEnquiry.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                    fam.close (true);
                } else {
                    Toast.makeText (AllQuoteList.this, "Please Select ", Toast.LENGTH_SHORT).show ();
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
        RealmResults<QuoteItemList> results;
        results = realm.where (QuoteItemList.class)
                .notEqualTo ("onlineStatus", "1")
                .and ()
                .equalTo ("qstatus", "Submitted")
                .findAll ();
        checkData (results);


    }

    private void checkData(RealmResults<QuoteItemList> results) {

        int count = results.size ();
        if (results.size () != 0) {
            for (int i = 0; i < results.size (); i++) {
                count--;
            }
            onlineSyncMethodAll (results.get (count).getQuoteItemlist ());

        } else {
            Intent intent = new Intent (AllQuoteList.this, AllQuoteList.class);
            startActivity (intent);
            Toast.makeText (this, "No data to Sync", Toast.LENGTH_SHORT).show ();


        }


    }

    public void onlineSyncMethodAll(final Integer quiteId) {
        Toast.makeText (AllQuoteList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (quiteId));
        quoteItemModels = new ArrayList<> ();
        quoteItemModels.addAll (realm.copyFromRealm (realm.where (QuoteItemList.class).equalTo ("id", quiteId)
                .findAll ()));
        if (quoteItemModels.get (0).getQstatus ().equals ("Submitted")) {
            status = 2;
        }
        if (quoteItemModels.get (0).getQuoteType ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        quoteItemList = new QuoteItemList ();
        quoteItemList.setQuoteItemlist (quoteItemModels.get (0).getQuoteItemlist ());
        quoteItemList.setQstatus (String.valueOf (status));
        quoteItemList.setQuoteType (String.valueOf (typeid));
        quoteItemList.setTax_value (quoteItemModels.get (0).getTax_value ());
        quoteItemList.setTaxTypeid (quoteItemModels.get (0).getTaxTypeid ());
        quoteItemList.setQcus_id (quoteItemModels.get (0).getQcus_id ());
        quoteItemList.setQodate (IFiveEngine.myInstance.formatDate (quoteItemModels.get (0).getQodate ()));
        quoteItemList.setQdel_date (IFiveEngine.myInstance.formatDate (quoteItemModels.get (0).getQdel_date ()));
        quoteItemList.setTotalPrice (quoteItemModels.get (0).getTotalPrice ());
        quoteItemList.setNetrice (quoteItemModels.get (0).getNetrice ());
        quoteItemList.setTaxTotal (quoteItemModels.get (0).getTaxTotal ());
        quoteItemList.setOnlineEnquiryId (quoteItemModels.get (0).getOnlineEnquiryId ());

        quoteItemLineList.addAll (realm.copyFromRealm (realm.where (QuoteItemLineList.class)
                .equalTo ("quoteHdrId", quiteId)
                .findAll ()));
        quoteItemList.setQuoteItemLines (quoteItemLineList);
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendQuoteSingleData (sessionManager.getToken (this), quoteItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (quiteId));
                            QuoteItemList quoteItemList = realm.where (QuoteItemList.class).equalTo ("id", quiteId).findFirst ();
                            quoteItemList.setOnlineStatus ("1");
                            quoteItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (AllQuoteList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                           sendAllData ();
                            Intent intent = new Intent (AllQuoteList.this, AllQuoteList.class);
                            startActivity (intent);
                        }
                    });
                    enquiryResponse.getEnquiryId ();


                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<EnquiryResponse> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Toast.makeText (AllQuoteList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    public void onlineSyncMethodSingle(final Integer quiteId) {
        Toast.makeText (AllQuoteList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (quiteId));
        quoteItemModels = new ArrayList<> ();
        quoteItemModels.addAll (realm.copyFromRealm (realm.where (QuoteItemList.class).equalTo ("id", quiteId)
                .findAll ()));
        if (quoteItemModels.get (0).getQstatus ().equals ("Submitted")) {
            status = 2;
        }
        if (quoteItemModels.get (0).getQuoteType ().equals ("standard")) {
            typeid = 1;
        } else {
            typeid = 2;
        }
        quoteItemList = new QuoteItemList ();
        quoteItemList.setQuoteItemlist (quoteItemModels.get (0).getQuoteItemlist ());
        quoteItemList.setQstatus (String.valueOf (status));
        quoteItemList.setQuoteType (String.valueOf (typeid));
        quoteItemList.setNetrice (quoteItemModels.get (0).getNetrice ());
        quoteItemList.setTaxTypeid (quoteItemModels.get (0).getTaxTypeid ());
        quoteItemList.setTax_value (quoteItemModels.get (0).getTax_value ());
        quoteItemList.setTaxTotal (quoteItemModels.get (0).getTaxTotal ());
        quoteItemList.setQcus_id (quoteItemModels.get (0).getQcus_id ());
        quoteItemList.setQodate (IFiveEngine.myInstance.formatDate (quoteItemModels.get (0).getQodate ()));
        quoteItemList.setQdel_date (IFiveEngine.myInstance.formatDate (quoteItemModels.get (0).getQdel_date ()));
        quoteItemList.setTotalPrice (quoteItemModels.get (0).getTotalPrice ());
        quoteItemList.setOnlineEnquiryId (quoteItemModels.get (0).getOnlineEnquiryId ());

        quoteItemLineList.addAll (realm.copyFromRealm (realm.where (QuoteItemLineList.class)
                .equalTo ("quoteHdrId", quiteId)
                .findAll ()));
        quoteItemList.setQuoteItemLines (quoteItemLineList);
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendQuoteSingleData (sessionManager.getToken (this), quoteItemList);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (quiteId));
                            QuoteItemList quoteItemList = realm.where (QuoteItemList.class).equalTo ("id", quiteId).findFirst ();
                            quoteItemList.setOnlineStatus ("1");
                            quoteItemList.setOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (AllQuoteList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                           // sendAllData ();
                            Intent intent = new Intent (AllQuoteList.this, AllQuoteList.class);
                            startActivity (intent);
                        }
                    });
                    enquiryResponse.getEnquiryId ();


                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<EnquiryResponse> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Toast.makeText (AllQuoteList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    @Override
    public void onBackPressed() {
        Intent it = new Intent(AllQuoteList.this, Dashboard.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

}
