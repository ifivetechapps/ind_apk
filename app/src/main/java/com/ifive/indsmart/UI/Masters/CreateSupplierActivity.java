package com.ifive.indsmart.UI.Masters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifive.indsmart.Connectivity.LoginResponse;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.DashBoard.Dashboard;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    LoginResponse responseMsg;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_supplier);
        ButterKnife.bind(this);
        pDialog = IFiveEngine.getProgDialog(this);
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

        if (supName.getText().toString().trim() == "") {
            supName.setError("Required");
        } else if (supAdd.getText().toString().trim() == "") {
            supAdd.setError("Required");
        } else if (supNum.getText().toString().trim() == "") {
            supNum.setError("Required");
        } else {
            groupsave();
        }
    }

    private void groupsave() {
//        Number currentIdNum = realm.where(SupplierList.class).max("supId");
//        if (currentIdNum == null) {
//            nextId = 1;
//        } else {
//            nextId = currentIdNum.intValue() + 1;
//        }
        supplierList = new SupplierList();
        supplierList.setSupplierName(supName.getText().toString().trim());
        supplierList.setSupplierAddress(supAdd.getText().toString().trim());
        supplierList.setEmpnum(supNum.getText().toString().trim());
        saveSupplier(supplierList);
        //uploadLocalPurchase(supplierList);
    }



    private void saveSupplier(SupplierList supplierList) {
        if (IFiveEngine.isNetworkAvailable(this)) {
            pDialog.show();
            responseMsg = new LoginResponse();
            pDialog = IFiveEngine.getProgDialog(this);
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<LoginResponse> callEnqueue = userAPICall.suppliersave(sessionManager.getToken(this), supplierList);
            callEnqueue.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    responseMsg = response.body();
                    if (responseMsg.getMsg().equals("Success")) {
                        Intent login = new Intent(CreateSupplierActivity.this, Dashboard.class);
                        startActivity(login);
                        finish();
                    } else {
                        Toast.makeText(CreateSupplierActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
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

