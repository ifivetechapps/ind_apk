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
import android.widget.Toast;

import com.ifive.indsmart.Connectivity.CommanResponse;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Sales.SalesInvoice.Model.InvoiceItemList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesInvoiceApprove extends Fragment {

    View view;
    Unbinder unbinder;
    @BindView(R.id.sq_list)
    RecyclerView sq_list;
    @BindView(R.id.nodataval)
    FrameLayout nodataval;
    @BindView(R.id.ff11)
    FrameLayout ff11;
    SaleInvoiceApproveAdapter saleApproveAdapter;
    LinearLayoutManager manager;
    Realm realm;
    RealmResults<InvoiceItemList> results;
    InvoiceItemApprove invoiceItemApprove;
    ProgressDialog pDialog;
    CommanResponse commanResponse = new CommanResponse();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sq_approve, container, false);
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
        loadData();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadData() {
        if (IFiveEngine.isNetworkAvailable(getContext())) {
            pDialog.show();
            invoiceItemApprove = new InvoiceItemApprove();
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<InvoiceItemApprove> callEnqueue = userAPICall.saleinvoiceList(sessionManager.getToken(getContext()));
            callEnqueue.enqueue(new Callback<InvoiceItemApprove>() {
                @Override
                public void onResponse(Call<InvoiceItemApprove> call, Response<InvoiceItemApprove> response) {
                    invoiceItemApprove = response.body();
                    if (invoiceItemApprove.getSoInvoiceList() != null) {
                        ff11.setVisibility(View.VISIBLE);
                        nodataval.setVisibility(View.GONE);
                        loadAdapter();
                        pDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<InvoiceItemApprove> call, Throwable t) {
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getValues() {

        results = realm.where(InvoiceItemList.class)
                .equalTo("status", "Submitted")
                .notEqualTo("approvalstatus", "REJECTED")
                .notEqualTo("approvalstatus", "APPROVED")
                .findAll();
        if (results.size() != 0) {
            ff11.setVisibility(View.VISIBLE);
            nodataval.setVisibility(View.GONE);
            loadAdapter();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadAdapter() {
        saleApproveAdapter = new SaleInvoiceApproveAdapter(this, invoiceItemApprove.getSoInvoiceList(), this);
        manager = new LinearLayoutManager(getContext());
        sq_list.setLayoutManager(manager);
        sq_list.setAdapter(saleApproveAdapter);
        sq_list.setItemAnimator(new DefaultItemAnimator());
        sq_list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void changeStatus(final String status, int position, final int id) {

        if (IFiveEngine.isNetworkAvailable(getContext())) {
            pDialog.show();
            SessionManager sessionManager = new SessionManager();
            ApproveRequest approveRequest = new ApproveRequest();
            approveRequest.setId(id);
            approveRequest.setStatus(status);
            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<CommanResponse> callEnqueue = userAPICall.siApprove(sessionManager.getToken(getContext()), approveRequest);

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


        }


    }

}
