package com.ifive.indsmart.UI.Sales.SalesCreate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoHeaderquoteDetail;
import com.ifive.indsmart.UI.Sales.SalesCreate.Adapter.SalesConvertFromQuoteAdapter;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SalesConvertFromQuote extends BaseActivity {

    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    ActionBar actionBar;
    Realm realm;
    private FloatingActionMenu fam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        fam = findViewById (R.id.fab_menu);
        fam.setVisibility (View.GONE);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        RealmResults<SoHeaderquoteDetail> results;
        results = realm.where(SoHeaderquoteDetail.class)
                .equalTo ("approveStatus","APPROVED")

                .findAll();
        if (results.size() != 0) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            SalesConvertFromQuoteAdapter adapter = new SalesConvertFromQuoteAdapter (results, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            salesorder_list.setLayoutManager(linearLayoutManager);
            salesorder_list.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(SalesConvertFromQuote.this, SubDashboard.class);
        it.putExtra("type", "Sales");
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}

