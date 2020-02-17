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

import com.github.clans.fab.FloatingActionMenu;
import com.ifive.indsmart.Connectivity.Message;
import com.ifive.indsmart.Connectivity.SendApprovalId;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionApprovalList;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionApprover;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter.ApproveListRequisitionAdapter;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveListRequisitionActivity extends BaseActivity {
    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.nodata)
    TextView nodata;
    ActionBar actionBar;
    Realm realm;
    Menu menu;
    Message message;
    private FloatingActionMenu fam;
    PurchaseRequisitionApprover purchaseRequisitionApprover;
    ProgressDialog progressDialog;
    SessionManager sessionManager;SendApprovalId sendApprovalId;
    RealmList<PurchaseRequisitionApprovalList> purchaseRequisitionApprovalList =new RealmList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchaserequisition);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        sessionManager=new SessionManager ();
        progressDialog = IFiveEngine.getProgDialog (this);
        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);
        fam.setVisibility (View.GONE);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        loadData();



    }
    private void loadData() {
        if (IFiveEngine.isNetworkAvailable(this)) {
            progressDialog.show();
            purchaseRequisitionApprover = new PurchaseRequisitionApprover ();
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<PurchaseRequisitionApprover> callEnqueue = userAPICall.allpurchasereqlist(sessionManager.getToken(this));
            callEnqueue.enqueue(new Callback<PurchaseRequisitionApprover> () {
                @Override
                public void onResponse(Call<PurchaseRequisitionApprover> call, Response<PurchaseRequisitionApprover> response) {
                    purchaseRequisitionApprover = response.body();
                    if (purchaseRequisitionApprover.getPurchaseRequisitionApprovalList () != null) {
                        realm.executeTransaction (new Realm.Transaction () {
                            @Override
                            public void execute(Realm realm) {
                                purchaseRequisitionApprovalList.addAll (purchaseRequisitionApprover.getPurchaseRequisitionApprovalList ());

                            }
                        });

                        loadAdapter();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<PurchaseRequisitionApprover> call, Throwable t) {

                }
            });
        }
    }

    private void loadAdapter() {

        if (purchaseRequisitionApprover.getPurchaseRequisitionApprovalList ().size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            ApproveListRequisitionAdapter adapter = new ApproveListRequisitionAdapter (this, purchaseRequisitionApprover.getPurchaseRequisitionApprovalList (), this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

            salesorder_list.setLayoutManager (linearLayoutManager);

            salesorder_list.setAdapter (adapter);
        }
    }
    public void onlineApprove(final String reqid,String value) {
        Toast.makeText (ApproveListRequisitionActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
        Log.d ("onlineSyncMethodSingle", String.valueOf (reqid));

        sendApprovalId = new SendApprovalId ();
        sendApprovalId.setMessage (reqid);
        sendApprovalId.setStatus_id (value);

        if (IFiveEngine.isNetworkAvailable (this)) {
            progressDialog.show ();
            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<Message> callEnqueue = userAPICall.purchaseReqApprovedData (sessionManager.getToken (this), sendApprovalId);
            callEnqueue.enqueue (new Callback<Message> () {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {

                    message = new Message ();
                    message = response.body ();

                    realm.executeTransaction (new Realm.Transaction () {
                        @Override
                        public void execute(Realm realm) {
                            Toast.makeText (ApproveListRequisitionActivity.this, message.getMessage (), Toast.LENGTH_SHORT).show ();
                            Intent intent = new Intent (ApproveListRequisitionActivity.this, ApproveListRequisitionActivity.class);
                            startActivity (intent);
                        }
                    });



                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    if ((progressDialog != null) && progressDialog.isShowing ())
                        progressDialog.dismiss ();
                    Log.d ("dvlmlkdmvdklv",t.getMessage ());
                    Toast.makeText (ApproveListRequisitionActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ApproveListRequisitionActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
