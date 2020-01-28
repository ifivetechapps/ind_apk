package com.ifiveuv.indsmart.UI.SalesEnquiry;

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
import com.ifiveuv.indsmart.UI.SalesEnquiry.Adapter.SalesEnquiryviewAdapter;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryLineList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SalesEnquiryViewActivity extends BaseActivity {
    @BindView(R.id.enquiry_number)
    TextView enquiry_number;
    @BindView(R.id.enquiry_date)
    TextView enquiry_date;
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.remarks)
    TextView remarks;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.enquiry_source)
    TextView enquiry_source;
    @BindView(R.id.back_button)
    Button back_button;
    @BindView(R.id.copy_button)
    Button copy_button;
    @BindView(R.id.item_data_list)
    RecyclerView item_data_list;
    int hdrid;
    RecyclerView.LayoutManager mLayoutManager;
    Realm realm;
    RealmList<EnquiryLineList> enquiryLineLists = new RealmList<>();
    SalesEnquiryviewAdapter salesAdapter;
    int nextId;
    EnquiryItemModel enquiryItemModel;
    RealmResults<EnquiryItemModel> enquiryItemModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sale_enquiry);
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
        loadData();
    }

    private void loadData() {

        enquiryItemModels = realm.where(EnquiryItemModel.class)
                .equalTo("enquiryId", hdrid)
                .findAll();
        enquiry_number.setText("SE" + enquiryItemModels.get(0).getEnquiryId ());
        enquiry_date.setText(enquiryItemModels.get(0).getEnquiryDate ());
        customer_Name.setText(enquiryItemModels.get(0).getEnquiryCustomerName ());
        remarks.setText(enquiryItemModels.get(0).getEnquiryRemarks ());
        so_status.setText(enquiryItemModels.get(0).getEnquiryStatus ());
        enquiry_source.setText(enquiryItemModels.get(0).getEnquiryType ());
        enquiryLineLists.addAll(enquiryItemModels.get(0).getEnquiryLineLists());
        salesAdapter = new SalesEnquiryviewAdapter(this, enquiryLineLists, this);
        item_data_list.setAdapter(salesAdapter);
        item_data_list.setItemViewCacheSize(enquiryLineLists.size());
        mLayoutManager = new LinearLayoutManager(this);
        salesAdapter.notifyDataSetChanged();
        item_data_list.setLayoutManager(mLayoutManager);
        item_data_list.setItemAnimator(new DefaultItemAnimator());

    }

    @OnClick(R.id.back_button)
    public void submitdata() {
        Intent intent = new Intent(SalesEnquiryViewActivity.this, SalesEnquiryList.class);
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

        Intent intent=new Intent (SalesEnquiryViewActivity.this,SalesEnquiryEditActivity.class);
        intent.putExtra("hdrid",  String.valueOf(hdrid));
        intent.putExtra("typeof", "copy");
        startActivity (intent);

    }


}
