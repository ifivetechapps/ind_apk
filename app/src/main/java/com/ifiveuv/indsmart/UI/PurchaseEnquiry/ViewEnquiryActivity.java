package com.ifiveuv.indsmart.UI.PurchaseEnquiry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Adapter.ViewEnquiryLineAdapter;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model.PurchaseEnquiryData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ViewEnquiryActivity extends BaseActivity {
    Realm realm;
    ViewEnquiryLineAdapter viewEnquiryLineAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    int hdrid;
    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.supplier_name)
    TextView supplier_name;
    @BindView(R.id.supplier_site_name)
    TextView supplier_site_name;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate (savedInstance);
        setContentView (R.layout.view_purchaseenquiry);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();

        Intent intent = getIntent ();
        hdrid = Integer.parseInt (intent.getStringExtra ("headerid"));
        RealmResults<PurchaseEnquiryData> purchaseEnquiryData;
        purchaseEnquiryData = realm.where (PurchaseEnquiryData.class)
                .equalTo ("enquiryId", hdrid)
                .findAll ();
        supplier_name.setText (purchaseEnquiryData.get (0).getSupplierName ());
        supplier_site_name.setText (purchaseEnquiryData.get (0).getSupplierSiteName ());
        RealmList<EnquiryItemList> enquiryItemLists = new RealmList<> ();
        enquiryItemLists.addAll (purchaseEnquiryData.get (0).getEnquiryItemLists ());
        viewEnquiryLineAdapter = new ViewEnquiryLineAdapter (this, enquiryItemLists, this);
        items_data.setAdapter (viewEnquiryLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        viewEnquiryLineAdapter.notifyDataSetChanged ();
    }

    @OnClick(R.id.draft_data)
    public void backButton() {
        Intent intent = new Intent (ViewEnquiryActivity.this, ListEnquiryActivity.class);
        startActivity (intent);
    }
}
