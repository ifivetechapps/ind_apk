package com.ifive.indsmart.UI.Purchase.PurchaseRequisition;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ifive.indsmart.Connectivity.Message;
import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.SendApprovalId;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionApprovalList;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionApprover;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter.ViewRequisitionLineAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionLines;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveRequisitionActivity extends BaseActivity {
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
    SendApprovalId sendApprovalId;
    RequisitionHeader requisitionHeader;
    RealmList<RequisitionLines> linerequisition = new RealmList<> ();
    RealmList<RequisitionLines> requisitionLinesRealmList;
    int hdrid;
    Message message;
    String enq_date;
    ViewRequisitionLineAdapter editRequisitionLineAdapter;
    List<SupplierList> supplierLists = new ArrayList<> ();
    String headerId;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    PurchaseRequisitionApprover purchaseRequisitionApprover;

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
        sessionManager=new SessionManager ();
        hdrid = Integer.parseInt (intent.getStringExtra ("headerid"));
        progressDialog = IFiveEngine.getProgDialog (this);
        submit_data.setText ("Approve");
        draft_data.setText ("Reject");
        Drawable img = getApplicationContext ().getResources ().getDrawable (R.drawable.ic_reject);
        img.setBounds (0, 0, 60, 60);
        draft_data.setCompoundDrawables (img, null, null, null);
        loadRequisitionItemAdapter();


    }

    private void loadRequisitionItemAdapter() {

        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        RealmResults<PurchaseRequisitionApprovalList> results;
        results = realm.where (PurchaseRequisitionApprovalList.class)
                .equalTo ("Hdrid", hdrid)
                .findAll ();
        headerId= String.valueOf (results.get (0).getPurchaseReqId ());
        requestor_name.setText (results.get (0).getSupplierName ());



    }

    @OnClick(R.id.submit_data)
    public void submitData() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                RequisitionHeader obj = realm.where (RequisitionHeader.class).equalTo ("Hdrid", hdrid).findFirst ();
                obj.setRequestStatus ("Approved");


            }
        });
        onlineApprove(headerId,"3");
    }

    @OnClick(R.id.draft_data)
    public void rejectData() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                RequisitionHeader obj = realm.where (RequisitionHeader.class).equalTo ("Hdrid", hdrid).findFirst ();
                obj.setRequestStatus ("Rejected");


            }
        });
        onlineApprove(headerId,"4");

    }

    public void onlineApprove(final String reqid,String value) {
        Toast.makeText (ApproveRequisitionActivity.this, "calling method", Toast.LENGTH_SHORT).show ();
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
                            Toast.makeText (ApproveRequisitionActivity.this, message.getMessage (), Toast.LENGTH_SHORT).show ();
                            Intent intent = new Intent (ApproveRequisitionActivity.this, ListRequisitionActivity.class);
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
                    Toast.makeText (ApproveRequisitionActivity.this, "Not Updated", Toast.LENGTH_SHORT).show ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }

    }

}
