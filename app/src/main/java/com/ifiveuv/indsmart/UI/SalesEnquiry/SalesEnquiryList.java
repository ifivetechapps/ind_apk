package com.ifiveuv.indsmart.UI.SalesEnquiry;

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
import com.ifiveuv.indsmart.UI.SalesEnquiry.Adapter.SalesEnquiryAllListAdapter;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryItemModel;

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

public class SalesEnquiryList extends BaseActivity {

    ActionBar actionBar;
    SessionManager sessionManager;
    Realm realm;
    @BindView(R.id.so_order_list)
    RecyclerView poorderlist;
    ProgressDialog progressDialog;
    private FloatingActionMenu fam;
    private FloatingActionButton createStandard, createLabour;
    private List<EnquiryItemModel> enquiryItemModels;
    EnquiryItemModel enquiryItemModel;
    int typeid,status;
    EnquiryResponse enquiryResponse;
    private Menu menu;
    int enq_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.fab_activity);
        ButterKnife.bind (this);

        actionBar = getSupportActionBar ();
        createStandard = findViewById (R.id.create_standard);
        createLabour = findViewById (R.id.create_labour);
        sessionManager = new SessionManager ();
        fam = findViewById (R.id.fab_menu);
        createStandard.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());

        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        progressDialog = IFiveEngine.getProgDialog (this);

        RealmResults<EnquiryItemModel> results;
        results = realm.where (EnquiryItemModel.class)
                .findAll ();

        SalesEnquiryAllListAdapter adapter = new SalesEnquiryAllListAdapter (this, results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);
        poorderlist.setLayoutManager (linearLayoutManager);
        poorderlist.setAdapter (adapter);
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (view == createStandard) {
                    Intent i = new Intent (SalesEnquiryList.this, SalesEnquiryCreatingActivity.class);
                    i.putExtra ("type", "standard");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (SalesEnquiryList.this, SalesEnquiryCreatingActivity.class);
                    i.putExtra ("type", "labour");
                    startActivity (i);
                    fam.close (true);
                } else {
                    Toast.makeText (SalesEnquiryList.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    public void onlineSyncMethodAll(final Integer enquiryId) {
        Toast.makeText (SalesEnquiryList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d("onlineSyncMethodSingle", String.valueOf (enquiryId));
        enquiryItemModels = new ArrayList<> ();
        enquiryItemModels.addAll(realm.copyFromRealm(realm.where(EnquiryItemModel.class).equalTo ("enquiryId",enquiryId)
                .findAll()));
        if(enquiryItemModels.get (0).getEnquiryStatus ().equals ("Submitted")){
            status=2;
        } if(enquiryItemModels.get (0).getEnquiryType ().equals ("standard")){
            typeid=1;
        } else {
            typeid=2;
        }
        enquiryItemModel=new EnquiryItemModel ();
        enquiryItemModel.setEnquiryCustomerId (enquiryItemModels.get (0).getEnquiryCustomerId ());
        enquiryItemModel.setEnquiryStatus (String.valueOf (status));
        enquiryItemModel.setEnquiryType (String.valueOf (typeid));
        enquiryItemModel.setEnquiryDate (IFiveEngine.myInstance.formatDate (enquiryItemModels.get (0).getEnquiryDate ()));
        enquiryItemModel.setDeliveryDate (IFiveEngine.myInstance.formatDate (enquiryItemModels.get (0).getDeliveryDate ()));
        enquiryItemModel.setEnquiryRemarks (enquiryItemModels.get (0).getEnquiryRemarks ());
        enquiryItemModel.setEnquiryLineLists (enquiryItemModels.get (0).getEnquiryLineLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendEnquirySingleData (sessionManager.getToken (this), enquiryItemModel);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse=new EnquiryResponse ();
                    enquiryResponse=response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d("onlineSyncMethodinside", String.valueOf (enquiryId));
                            EnquiryItemModel enquiryItemModel= realm.where (EnquiryItemModel.class).equalTo ("enquiryId", enquiryId).findFirst ();
                            enquiryItemModel.setStautsOnline ("1");
                            enquiryItemModel.setEnqOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (SalesEnquiryList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                            sendAllData();
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
                    Toast.makeText (SalesEnquiryList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }
    public void onlineSyncMethodSingle(final Integer enquiryId) {
        Toast.makeText (SalesEnquiryList.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d("onlineSyncMethodSingle", String.valueOf (enquiryId));
        enquiryItemModels = new ArrayList<> ();
        enquiryItemModels.addAll(realm.copyFromRealm(realm.where(EnquiryItemModel.class).equalTo ("enquiryId",enquiryId)
                .findAll()));
        if(enquiryItemModels.get (0).getEnquiryStatus ().equals ("Submitted")){
            status=2;
        } if(enquiryItemModels.get (0).getEnquiryType ().equals ("standard")){
            typeid=1;
        } else {
            typeid=2;
        }
        enquiryItemModel=new EnquiryItemModel ();
        enquiryItemModel.setEnquiryCustomerId (enquiryItemModels.get (0).getEnquiryCustomerId ());
        enquiryItemModel.setEnquiryStatus (String.valueOf (status));
        enquiryItemModel.setEnquiryType (String.valueOf (typeid));
        enquiryItemModel.setEnquiryDate (IFiveEngine.myInstance.formatDate (enquiryItemModels.get (0).getEnquiryDate ()));
        enquiryItemModel.setDeliveryDate (IFiveEngine.myInstance.formatDate (enquiryItemModels.get (0).getDeliveryDate ()));
        enquiryItemModel.setEnquiryRemarks (enquiryItemModels.get (0).getEnquiryRemarks ());
        enquiryItemModel.setEnquiryLineLists (enquiryItemModels.get (0).getEnquiryLineLists ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendEnquirySingleData (sessionManager.getToken (this), enquiryItemModel);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse=new EnquiryResponse ();
                    enquiryResponse=response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d("onlineSyncMethodinside", String.valueOf (enquiryId));
                            EnquiryItemModel enquiryItemModel= realm.where (EnquiryItemModel.class).equalTo ("enquiryId", enquiryId).findFirst ();
                            enquiryItemModel.setStautsOnline ("1");
                            enquiryItemModel.setEnqOnlineId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (SalesEnquiryList.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                            //sendAllData();
                            Intent intent=new Intent (SalesEnquiryList.this, SalesEnquiryList.class);
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
                    Toast.makeText (SalesEnquiryList.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

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
        Log.d("LAALALAL", String.valueOf (id));
        Log.d("LAALALAL123", String.valueOf (R.id.sync_item_black));
        if (id == R.id.sync_item_black) {
            sendAllData();
        }
        return super.onOptionsItemSelected (item);
    }

    private void sendAllData() {
        RealmResults<EnquiryItemModel> results;
        results = realm.where (EnquiryItemModel.class)
                .notEqualTo ("stautsOnline","1")
                .and ()
                .equalTo ("enquiryStatus","Submitted")
                .findAll ();
        checkData(results);



        }

    private void checkData(RealmResults<EnquiryItemModel> results) {

            int count=results.size ();
            if(results.size ()!=0){
                for(int i=0;i<results.size ();i++){
                    count--;
                }
                onlineSyncMethodAll(results.get (count).getEnquiryId ());

            } else{
                Intent intent=new Intent (SalesEnquiryList.this, SalesEnquiryList.class);
                startActivity (intent);
                Toast.makeText (this, "No data to Sync", Toast.LENGTH_SHORT).show ();


        }


    }
    @Override
    public void onBackPressed() {
        Intent it = new Intent(SalesEnquiryList.this, Dashboard.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

}
