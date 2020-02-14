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
import com.ifive.indsmart.UI.Masters.Model.CustomerList;

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
    LoginResponse responseMsg;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_customer);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        pDialog = IFiveEngine.getProgDialog(this);

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
        if (cusName.getText().toString().trim() == "") {
            cusName.setError("Required");
        } else if (cusAdd.getText().toString().trim() == "") {
            cusAdd.setError("Required");
        } else if (cusNum.getText().toString().trim() == "") {
            cusNum.setError("Required");
        } else if (cusEmail.getText().toString().trim() == "") {
            cusEmail.setError("Required");
        } else {
            groupsave();
        }
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

        saveCustomer(customerList);

        //   uploadLocalPurchase(customerList);
    }

    private void saveCustomer(CustomerList customerList) {
        if (IFiveEngine.isNetworkAvailable(this)) {
            pDialog.show();
            responseMsg = new LoginResponse();
            pDialog = IFiveEngine.getProgDialog(this);
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<LoginResponse> callEnqueue = userAPICall.customersave(sessionManager.getToken(this), customerList);
            callEnqueue.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    responseMsg = response.body();
                    if (responseMsg.getMsg().equals("Success")) {
                        Intent login = new Intent(CreateCustomerActivity.this, Dashboard.class);
                        startActivity(login);
                        finish();
                    } else {
                        Toast.makeText(CreateCustomerActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
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

