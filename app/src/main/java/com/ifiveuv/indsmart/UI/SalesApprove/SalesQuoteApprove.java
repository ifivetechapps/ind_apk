package com.ifiveuv.indsmart.UI.SalesApprove;


import android.app.Fragment;
import android.content.Intent;
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

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SalesQuoteApprove extends Fragment {
    View view;
    Unbinder unbinder;
    @BindView(R.id.sq_list)
    RecyclerView sq_list;
    @BindView(R.id.ff11)
    FrameLayout ff11;
    @BindView(R.id.nodataval)
    FrameLayout nodataval;

    SaleQuoteApproveAdapter saleApproveAdapter;
    LinearLayoutManager manager;
    Realm realm;
    RealmResults<QuoteItemList> results;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sq_approve, container, false);
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
        getValues();
        loadAdapter();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getValues() {

        results = realm.where(QuoteItemList.class)
                .equalTo("qstatus", "Submitted")
                .notEqualTo("approval_status", "REJECTED")
                .notEqualTo("approval_status", "APPROVED")
                .findAll();
        if (results.size() != 0) {
            ff11.setVisibility(View.VISIBLE);
            nodataval.setVisibility(View.GONE);
            loadAdapter();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadAdapter() {
        saleApproveAdapter = new SaleQuoteApproveAdapter(this, results, this);
        manager = new LinearLayoutManager(getContext());
        sq_list.setLayoutManager(manager);
        sq_list.setAdapter(saleApproveAdapter);
        sq_list.setItemAnimator(new DefaultItemAnimator());
        sq_list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void changeStatus(final String rejected, int position, final int id) {

        realm.executeTransaction(new Realm.Transaction() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void execute(Realm realm) {
                QuoteItemList obj = realm.where(QuoteItemList.class).equalTo("id", id).findFirst();
                obj.setApproval_status(rejected);
                Intent intent = new Intent(getContext(), Dashboard.class);
                startActivity(intent);


            }
        });


    }
}
