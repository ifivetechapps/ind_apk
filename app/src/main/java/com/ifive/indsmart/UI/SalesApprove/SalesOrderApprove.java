package com.ifive.indsmart.UI.SalesApprove;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ifive.indsmart.Connectivity.CommanResponse;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalesOrderApprove extends Fragment {
    View view;
    Unbinder unbinder;
    @BindView(R.id.so_list)
    RecyclerView so_list;
    @BindView(R.id.nodataval)
    FrameLayout nodataval;
    @BindView(R.id.ff11)
    FrameLayout ff11;
    SaleApproveAdapter saleApproveAdapter;
    LinearLayoutManager manager;
    Realm realm;
    RealmResults<SaleItemList> results;
    ProgressDialog pDialog;
    SaleItemListApprove saleItemList;
    CommanResponse commanResponse = new CommanResponse();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.so_approve, container, false);
        pDialog = IFiveEngine.getProgDialog(getContext());

        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Realm.init(getContext());
        }
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        // getValues();
        loadData();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadData() {
        if (IFiveEngine.isNetworkAvailable(getContext())) {
            pDialog.show();
            saleItemList = new SaleItemListApprove();
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<SaleItemListApprove> callEnqueue = userAPICall.saleOrderList(sessionManager.getToken(getContext()));
            callEnqueue.enqueue(new Callback<SaleItemListApprove>() {
                @Override
                public void onResponse(Call<SaleItemListApprove> call, Response<SaleItemListApprove> response) {
                    saleItemList = response.body();
                    if (saleItemList.getSoOrderList() != null) {
                        ff11.setVisibility(View.VISIBLE);
                        nodataval.setVisibility(View.GONE);
                        loadAdapter();
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<SaleItemListApprove> call, Throwable t) {

                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getValues() {

        results = realm.where(SaleItemList.class)
                .equalTo("status", "Submitted")
                .notEqualTo("approvalstatus", "APPROVED")
                .notEqualTo("approvalstatus", "REJECTED")
                .findAll();
        if (results.size() != 0) {
            ff11.setVisibility(View.VISIBLE);
            nodataval.setVisibility(View.GONE);
            loadAdapter();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadAdapter() {
        saleApproveAdapter = new SaleApproveAdapter(this, saleItemList.getSoOrderList(), this);
        manager = new LinearLayoutManager(getContext());
        so_list.setLayoutManager(manager);
        so_list.setAdapter(saleApproveAdapter);
        so_list.setItemAnimator(new DefaultItemAnimator());
        so_list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void changeStatus(final String status, int position, final int id) {


        if (IFiveEngine.isNetworkAvailable(getContext())) {
            pDialog.show();
            saleItemList = new SaleItemListApprove();
            SessionManager sessionManager = new SessionManager();
            ApproveRequest approveRequest = new ApproveRequest();
            approveRequest.setId(id);
            approveRequest.setStatus(status);
            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<CommanResponse> callEnqueue = userAPICall.soApprove(sessionManager.getToken(getContext()), approveRequest);

            callEnqueue.enqueue(new Callback<CommanResponse>() {
                @Override
                public void onResponse(Call<CommanResponse> call, Response<CommanResponse> response) {
                    commanResponse = response.body();
                    if (commanResponse != null) {
                        loadData();
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<CommanResponse> call, Throwable t) {
                    pDialog.dismiss();
                }
            });
      /*  realm.executeTransaction(new Realm.Transaction() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void execute(Realm realm) {
                SaleItemList obj = realm.where(SaleItemList.class).equalTo("SalesOrderid", id).findFirst();
                obj.setApprovalstatus(rejected);
                Intent intent = new Intent(getContext(), Dashboard.class);
                startActivity(intent);

            }
        });*/


        }
    }
}
