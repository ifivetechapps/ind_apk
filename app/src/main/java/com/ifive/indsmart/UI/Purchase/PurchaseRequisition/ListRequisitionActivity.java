package com.ifive.indsmart.UI.Purchase.PurchaseRequisition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter.ListRequisitionAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;
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

public class ListRequisitionActivity extends BaseActivity {
    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.nodata)
    TextView nodata;
    private FloatingActionButton createStandard;
    private FloatingActionMenu fam;
    ActionBar actionBar;
    Realm realm;
    Menu menu;
    List<RequisitionHeader> requisitionHeaderList;
    ProgressDialog progressDialog;
    RequisitionHeader requisitionHeader;
    SessionManager sessionManager;
    EnquiryResponse enquiryResponse;
int status,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchaserequisition);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        createStandard = (FloatingActionButton) findViewById (R.id.create_standard);

        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);

        progressDialog = IFiveEngine.getProgDialog (this);

        createStandard.setOnClickListener (onButtonClick ());
        sessionManager = new SessionManager ();
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<RequisitionHeader> results;
        results = realm.where (RequisitionHeader.class)
                .findAll ();

        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            ListRequisitionAdapter adapter = new ListRequisitionAdapter (results, this,this);
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
                    Intent i = new Intent (ListRequisitionActivity.this, CreateRequisitionActivity.class);
                    startActivity (i);
                } else {
                    Toast.makeText (ListRequisitionActivity.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ListRequisitionActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }

    public void onlineSyncMethodSingle(final Integer reqid) {
        Toast.makeText (ListRequisitionActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (reqid));
        requisitionHeaderList = new ArrayList<> ();
        requisitionHeaderList.addAll (realm.copyFromRealm (realm.where (RequisitionHeader.class).equalTo ("Hdrid", reqid)
                .findAll ()));
        if (requisitionHeaderList.get (0).getRequestStatus ().equals ("Submit")) {
            status = 2;
        }if (requisitionHeaderList.get (0).getTypeRequisition ().equals ("Standard")) {
            type = 2;
        }

        requisitionHeader = new RequisitionHeader ();
        requisitionHeader.setRequestorName (requisitionHeaderList.get (0).getRequestorName ());
        requisitionHeader.setRequestStatus (String.valueOf (status));
        requisitionHeader.setTypeRequisition (String.valueOf (type));
        requisitionHeader.setReqID (requisitionHeaderList.get (0).getReqID ());
        requisitionHeader.setRequestDate (IFiveEngine.myInstance.formatDate (requisitionHeaderList.get (0).getRequestDate ()));
        requisitionHeader.setRequisitionLines (requisitionHeaderList.get (0).getRequisitionLines ());
        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<EnquiryResponse> callEnqueue = userAPICall.sendPurchaseReqSingleData (sessionManager.getToken (this), requisitionHeader);
            callEnqueue.enqueue (new Callback<EnquiryResponse> () {
                @Override
                public void onResponse(Call<EnquiryResponse> call, Response<EnquiryResponse> response) {

                    enquiryResponse = new EnquiryResponse ();
                    enquiryResponse = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {

                            Log.d ("onlineSyncMethodinside", String.valueOf (reqid));
                            RequisitionHeader saleItemList = realm.where (RequisitionHeader.class).equalTo ("Hdrid", reqid).findFirst ();
                            saleItemList.setOnlineStatus ("1");
                            saleItemList.setOnlineReqId (enquiryResponse.getEnquiryId ());
                            Toast.makeText (ListRequisitionActivity.this, enquiryResponse.getMessage (), Toast.LENGTH_SHORT).show ();
                            //  sendAllData ();
                            Intent intent = new Intent (ListRequisitionActivity.this, ListRequisitionActivity.class);
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
                    Toast.makeText (ListRequisitionActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }

}
