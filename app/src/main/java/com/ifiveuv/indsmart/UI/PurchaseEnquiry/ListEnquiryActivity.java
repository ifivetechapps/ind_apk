package com.ifiveuv.indsmart.UI.PurchaseEnquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Adapter.ListEnquiryAdapter;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ListEnquiryActivity extends BaseActivity {
    @BindView(R.id.salesorder_list)
    RecyclerView salesorder_list;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.nodata)
    TextView nodata;
    private FloatingActionButton createStandard, createLabour, fromenquiry;
    private FloatingActionMenu fam;
    ActionBar actionBar;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_purchaseenquiry);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();

        fromenquiry = (FloatingActionButton) findViewById (R.id.from_enquiry);
        createStandard = (FloatingActionButton) findViewById (R.id.create_standard);
        createLabour = (FloatingActionButton) findViewById (R.id.create_labour);

        fam = (FloatingActionMenu) findViewById (R.id.fab_menu);

        createStandard.setOnClickListener (onButtonClick ());
        createLabour.setOnClickListener (onButtonClick ());
        fromenquiry.setOnClickListener (onButtonClick ());


        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        RealmResults<PurchaseEnquiryData> results;
        results = realm.where (PurchaseEnquiryData.class)
                .findAll ();

        if (results.size () != 0) {
            ll1.setVisibility (View.VISIBLE);
            ll2.setVisibility (View.GONE);
            ListEnquiryAdapter adapter = new ListEnquiryAdapter (results, this);
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
                    Intent i = new Intent (ListEnquiryActivity.this, CreateEnquiryActivity.class);
                    i.putExtra ("type", "standard");
                    i.putExtra ("source", "enquiry");
                    startActivity (i);
                } else if (view == createLabour) {
                    Intent i = new Intent (ListEnquiryActivity.this, CreateEnquiryActivity.class);
                    i.putExtra ("type", "labour");
                    i.putExtra ("source", "enquiry");
                    startActivity (i);
                } else if (view == fromenquiry) {
                    Intent i = new Intent (ListEnquiryActivity.this, RequisitionList.class);
                    startActivity (i);
                } else {
                    Toast.makeText (ListEnquiryActivity.this, "Please Select ", Toast.LENGTH_SHORT).show ();
                }
                fam.close (true);
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ListEnquiryActivity.this, SubDashboard.class);
        it.putExtra ("type", "Purchase");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
