package com.ifive.indsmart.UI.Purchase.PurchaseQuote;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter.ViewQuoteLineAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Model.QuotationHeader;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Model.QuotationLines;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ViewQuotationActivity extends BaseActivity {
    Realm realm;
    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.supplier_name)
    TextView supplier_name;
    @BindView(R.id.supplier_site)
    TextView supplier_site;
    @BindView(R.id.freight_address)
    TextView freight_address;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.tax_total)
    TextView tax_total;
    @BindView(R.id.grand_total)
    TextView grand_total;
    @BindView(R.id.submit_data)
    Button submit_data;
    int qo_id;
    RealmList<QuotationLines> quoteItemLineLists = new RealmList<> ();
    ViewQuoteLineAdapter viewQuoteLineAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.view_quote);
        ButterKnife.bind (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        Intent intent = getIntent ();
        qo_id = Integer.parseInt (intent.getStringExtra ("headerid"));
        loadItemAdapter ();
    }

    private void loadItemAdapter() {
        RealmResults<QuotationHeader> results;
        results = realm.where (QuotationHeader.class)
                .equalTo ("quote_id", qo_id)
                .findAll ();
        supplier_name.setText (results.get (0).getSupplierName ());
        supplier_site.setText (results.get (0).getSupplierSite ());
        freight_address.setText (results.get (0).getFreight_carrier ());
        delivery_date.setText (results.get (0).getDeliveryDate ());
        tax_total.setText (results.get (0).getGrandTax ());
        grand_total.setText (results.get (0).getGrandTotal ());
        quoteItemLineLists.addAll (results.get (0).getQuotationLines ());
        viewQuoteLineAdapter = new ViewQuoteLineAdapter (this, quoteItemLineLists, this);
        items_data.setAdapter (viewQuoteLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        viewQuoteLineAdapter.notifyDataSetChanged ();


    }

    @OnClick(R.id.submit_data)
    public void submitData() {

        Intent intent = new Intent (ViewQuotationActivity.this, ListQuotationActivity.class);
        startActivity (intent);

    }
}
