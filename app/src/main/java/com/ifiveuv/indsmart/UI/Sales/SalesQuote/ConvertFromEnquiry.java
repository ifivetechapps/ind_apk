package com.ifiveuv.indsmart.UI.Sales.SalesQuote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.Sales.OnlineModel.OnlineEnquiryItemModel;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.ConvertFromEnquiryAdapter;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ConvertFromEnquiry extends BaseActivity {

    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    ActionBar actionBar;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_quote);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        RealmResults<OnlineEnquiryItemModel> results;
        results = realm.where(OnlineEnquiryItemModel.class)
                .equalTo ("approveStatus","APPROVED")
                .findAll();
        if (results.size() != 0) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            ConvertFromEnquiryAdapter adapter = new ConvertFromEnquiryAdapter (results, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            salesorder_list.setLayoutManager(linearLayoutManager);
            salesorder_list.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(ConvertFromEnquiry.this, SubDashboard.class);
        it.putExtra("type", "Sales");
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}

