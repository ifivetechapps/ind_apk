package com.ifiveuv.indsmart.UI.Sales.SalesQuote;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.SalesQuoteviewAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesQuoteViewActivity extends BaseActivity {
    @BindView(R.id.customer_name)
    TextView customer_name;
    @BindView(R.id.order_date)
    TextView order_date;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.order_no)
    TextView order_no;
    @BindView(R.id.back_button)
    Button back_button;
    @BindView(R.id.copy_button)
    Button copy_button;
    @BindView(R.id.item_data_list)
    RecyclerView item_data_list;
    int hdrid;
    RecyclerView.LayoutManager mLayoutManager;
    Realm realm;
    RealmList<QuoteItemLineList> salesItemLinesall = new RealmList<>();
    SalesQuoteviewAdapter salesAdapter;
    int nextId;
    QuoteItemList quoteItemList;
    RealmResults<QuoteItemList> salesItemLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quoteorder_view);
        ButterKnife.bind(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        hdrid = Integer.parseInt(intent.getStringExtra("hdrid"));
        Log.d("kfv", String.valueOf(hdrid));
        loadData();
    }

    private void loadData() {
        salesItemLists = realm.where(QuoteItemList.class)
                .equalTo("id", hdrid)
                .findAll();
        order_no.setText("SOQ" + salesItemLists.get(0).getQuoteItemlist());
        delivery_date.setText(salesItemLists.get(0).getQdel_date());
        order_date.setText(salesItemLists.get(0).getQodate());
        status.setText(salesItemLists.get(0).getQstatus ());
        customer_name.setText(salesItemLists.get(0).getQcus_name());
        salesItemLinesall.addAll (realm.copyFromRealm (realm.where (QuoteItemLineList.class)
                .equalTo ("quoteHdrId", hdrid)
                .findAll ()));
        salesAdapter = new SalesQuoteviewAdapter(this, salesItemLinesall, this);
        item_data_list.setAdapter(salesAdapter);
        item_data_list.setItemViewCacheSize(salesItemLinesall.size());
        mLayoutManager = new LinearLayoutManager (this);
        salesAdapter.notifyDataSetChanged();
        item_data_list.setLayoutManager(mLayoutManager);
        item_data_list.setItemAnimator(new DefaultItemAnimator ());

    }

    @OnClick(R.id.back_button)
    public void submitdata() {
        Intent intent = new Intent(SalesQuoteViewActivity.this, AllQuoteList.class);
        startActivity(intent);
    }


    @OnClick(R.id.copy_button)
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

    public void headerSave() {

        Intent intent=new Intent (SalesQuoteViewActivity.this, SalesQuoteEditActivity.class);
        intent.putExtra("hdrid",  String.valueOf(hdrid));
        intent.putExtra("typeof", "copy");
        startActivity (intent);

    }



}
