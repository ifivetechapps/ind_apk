package com.ifive.indsmart.UI.Purchase.PurchaseQuote;


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

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter.ListRequisitionToQuoteAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class QuoteFromRequisitionActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchaserequisition);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        createStandard = (FloatingActionButton) findViewById (R.id.create_standard);

        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);
        fam.setVisibility (View.GONE);
        Log.d("lkjhkljgk","sdjaksdj");
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<RequisitionHeader> results;
        results = realm.where (RequisitionHeader.class)
                .equalTo ("requestStatus", "Approved")
                .findAll ();

        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            ListRequisitionToQuoteAdapter adapter = new ListRequisitionToQuoteAdapter (this,results, this );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);

            salesorder_list.setLayoutManager (linearLayoutManager);

            salesorder_list.setAdapter (adapter);
        }

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (QuoteFromRequisitionActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
