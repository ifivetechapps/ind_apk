package com.ifiveuv.indsmart.UI.Masters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Masters.Model.CustomerList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateCustomerActivity extends BaseActivity {
    @BindView(R.id.cus_name)
    EditText cusName;
    @BindView(R.id.cus_add)
    EditText cusAdd;
    @BindView(R.id.cus_num)
    EditText cusNum;
    @BindView(R.id.cus_email)
    EditText cusEmail;
    @BindView(R.id.submit_data)
    Button submit_data;
    Realm realm;
    ActionBar actionBar;
    CustomerList customerList;
    int nextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_customer);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.submit_data)
    public void submitdata() {
        groupsave();
    }

    private void groupsave() {
        Number currentIdNum = realm.where(CustomerList.class).max("cusNo");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        customerList = new CustomerList();
        customerList.setCusNo(nextId);
        customerList.setCusName(cusName.getText().toString().trim());
        customerList.setCusAdd(cusAdd.getText().toString().trim());
        customerList.setCusMobile(cusNum.getText().toString().trim());
        customerList.setCusEmail(cusEmail.getText().toString().trim());
        uploadLocalPurchase(customerList);
    }

    private void uploadLocalPurchase(final CustomerList customerList1) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        CustomerList customerList = realm.copyToRealm(customerList1);
                        realm.commitTransaction();
                        Intent intent = new Intent(CreateCustomerActivity.this, Dashboard.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "In onError()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                            }
                        });
                    }
                });
    }
}

