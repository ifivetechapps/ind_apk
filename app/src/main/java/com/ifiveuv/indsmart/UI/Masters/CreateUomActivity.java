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
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateUomActivity extends BaseActivity {
    @BindView(R.id.uom_name)
    EditText uom_name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.submit_data)
    Button submit_data;
    Realm realm;
    ActionBar actionBar;
    UomModel uomModel;
    int nextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_uom);
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
        Number currentIdNum = realm.where(UomModel.class).max("uom_id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        uomModel = new UomModel();
        uomModel.setUom_id(nextId);
        uomModel.setUom_name(uom_name.getText().toString().trim());
        uomModel.setDescription(description.getText().toString().trim());
        uploadLocalPurchase(uomModel);
    }

    private void uploadLocalPurchase(final UomModel uomModel) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        UomModel purchaseItemList = realm.copyToRealm(uomModel);
                        realm.commitTransaction();
                        Intent intent = new Intent(CreateUomActivity.this, Dashboard.class);
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


