package com.ifive.indsmart.UI.Purchase.PurchaseEnquiry;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Adapter.ViewEnquiryLineAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.PurchaseEnquiryData;

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
    @BindView(R.id.copy_data)
    Button copy_data;
    @BindView(R.id.submit_data)
    Button submit_data;
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

    @OnClick(R.id.submit_data)
    public void backButton() {
        Intent intent = new Intent (ViewEnquiryActivity.this, ListEnquiryActivity.class);
        startActivity (intent);
    }

    @OnClick(R.id.copy_data)
    public void copydata() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.confirm_copy);

        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText("Are You Sure To Copy This Order");

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerSave();
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void headerSave() {
        Intent intent=new Intent (ViewEnquiryActivity.this,EditEnquiryActivity.class);
        intent.putExtra("headerid",  String.valueOf(hdrid));
        intent.putExtra("typeof", "copy");
        startActivity (intent);
    }

}
