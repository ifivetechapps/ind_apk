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
import com.ifiveuv.indsmart.UI.Masters.Model.SupplierList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateSupplierActivity extends BaseActivity {
    @BindView(R.id.sup_name)
    EditText supName;
    @BindView(R.id.sup_add)
    EditText supAdd;
    @BindView(R.id.sup_num)
    EditText supNum;
    @BindView(R.id.submit_data)
    Button submit_data;
    Realm realm;
    ActionBar actionBar;
    SupplierList supplierList;
    int nextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_supplier);
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
        Number currentIdNum = realm.where(SupplierList.class).max("supId");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        supplierList = new SupplierList();
        supplierList.setEmpId (nextId);
        supplierList.setEmpName (supName.getText().toString().trim());
        supplierList.setEmpAdd (supAdd.getText().toString().trim());
        supplierList.setEmpNum(supNum.getText().toString().trim());
        uploadLocalPurchase(supplierList);
    }

    private void uploadLocalPurchase(final SupplierList supplierList) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        SupplierList supplierList1 = realm.copyToRealm(supplierList);
                        realm.commitTransaction();
                        Intent intent = new Intent(CreateSupplierActivity.this, Dashboard.class);
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

