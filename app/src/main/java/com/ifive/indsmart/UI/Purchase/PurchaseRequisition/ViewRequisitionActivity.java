package com.ifive.indsmart.UI.Purchase.PurchaseRequisition;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter.ViewRequisitionLineAdapter;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionLines;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ViewRequisitionActivity extends BaseActivity {
    @BindView(R.id.requestor_name)
    TextView requestor_name;
    @BindView(R.id.items_data)
    RecyclerView items_data;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    Realm realm;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    RecyclerView.LayoutManager mLayoutManager;
    int requestor_id;
    Menu menu;
    RequisitionHeader requisitionHeader;
    RealmList<RequisitionLines> linerequisition = new RealmList<> ();
    RealmList<RequisitionLines> requisitionLinesRealmList;
    int hdrid;

    String enq_date;
    ViewRequisitionLineAdapter editRequisitionLineAdapter;
    List<SupplierList> supplierLists = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate (savedInstance);
        setContentView (R.layout.create_purchaserequsition);
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



            Drawable img = getApplicationContext ().getResources().getDrawable( R.drawable.ic_back );
            img.setBounds( 0, 0, 60, 60 );
            submit_data.setCompoundDrawables( img, null, null, null );
            submit_data.setText ("Back");
            Drawable img1 = getApplicationContext ().getResources().getDrawable( R.drawable.ic_pen );
            img.setBounds( 0, 0, 60, 60 );
            draft_data.setCompoundDrawables( img, null, null, null );
            draft_data.setText ("Copy");


        loadRequisitionItemAdapter ();

    }

    private void loadRequisitionItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        RealmResults<RequisitionHeader> results;
        results = realm.where (RequisitionHeader.class)
                .equalTo ("Hdrid", hdrid)
                .findAll ();
        requestor_name.setText (results.get (0).getRequestorName ());
        linerequisition.addAll (results.get (0).getRequisitionLines ());
        editRequisitionLineAdapter = new ViewRequisitionLineAdapter (this, linerequisition, products, this);
        items_data.setAdapter (editRequisitionLineAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        items_data.setLayoutManager (mLayoutManager);
        items_data.setItemAnimator (new DefaultItemAnimator ());
        editRequisitionLineAdapter.notifyDataSetChanged ();
        requisitionLinesRealmList = linerequisition;

    }

    @OnClick(R.id.submit_data)
    public void submitData() {

            Intent intent = new Intent (ViewRequisitionActivity.this, ListRequisitionActivity.class);
            startActivity (intent);

    }

    @OnClick(R.id.draft_data)
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
        Intent intent=new Intent (ViewRequisitionActivity.this,EditRequisitionActivity.class);
        intent.putExtra("headerid",  String.valueOf(hdrid));
        intent.putExtra("type", "copy");
        startActivity (intent);
    }


}
