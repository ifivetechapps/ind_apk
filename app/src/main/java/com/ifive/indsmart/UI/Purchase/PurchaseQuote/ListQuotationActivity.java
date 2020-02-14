package com.ifive.indsmart.UI.Purchase.PurchaseQuote;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter.QuoteListRequisitionAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Model.QuotationHeader;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ListQuotationActivity extends BaseActivity {
    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.nodata)
    TextView nodata;
    private FloatingActionButton createStandard, fromRequisition, fromEnquiry, createLabour;
    private FloatingActionMenu fam;
    ActionBar actionBar;
    Realm realm;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchasequote_list);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        createStandard = (FloatingActionButton) findViewById (R.id.create_standard);
        fromRequisition = (FloatingActionButton) findViewById (R.id.from_requisition);
        fromEnquiry = (FloatingActionButton) findViewById (R.id.from_enquiry);
        createLabour = (FloatingActionButton) findViewById (R.id.create_labour);

        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);

        createStandard.setOnClickListener (onButtonClick ());
        fromRequisition.setOnClickListener (onButtonClick ());
        fromEnquiry.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<QuotationHeader> results;
        results = realm.where (QuotationHeader.class)
                .findAll ();

        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            QuoteListRequisitionAdapter adapter = new QuoteListRequisitionAdapter (results, this);
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
                    Intent i = new Intent (ListQuotationActivity.this, CreateQuoteActivity.class);
                    i.putExtra ("type", "standard");
                    i.putExtra ("source", "quotation");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (ListQuotationActivity.this, CreateQuoteActivity.class);
                    i.putExtra ("type", "labour");
                    i.putExtra ("source", "quotation");
                    startActivity (i);
                } else if (view == fromRequisition) {
                    Intent i = new Intent (ListQuotationActivity.this, QuoteFromRequisitionActivity.class);
                    i.putExtra ("type", "standard");
                    i.putExtra ("source", "requisition");
                    startActivity (i);
                } else if (view == fromEnquiry) {
                    Intent i = new Intent (ListQuotationActivity.this, QuoteFromEnquiryActivity.class);
                    i.putExtra ("type", "standard");
                    i.putExtra ("source", "enquiry");
                    startActivity (i);
                } else {
                    Toast.makeText (ListQuotationActivity.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ListQuotationActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
