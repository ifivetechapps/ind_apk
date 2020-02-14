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
import com.ifive.indsmart.UI.Masters.Model.UomModel;

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
    LoginResponse responseMsg;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_uom);
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
        if (uom_name.getText().toString().trim() == "") {
            uom_name.setError("Required");
        } else if (description.getText().toString().trim() == "") {
            description.setError("Required");
        } else {
            groupsave();
        }
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

        UomSave uomSave = new UomSave();
        uomSave.setUomname(uom_name.getText().toString().trim());
        uomSave.setDescription(description.getText().toString().trim());
        saveUom(uomSave);

        //uploadLocalPurchase(uomModel);
    }

    private void saveUom(UomSave uomSave) {
        if (IFiveEngine.isNetworkAvailable(this)) {
            pDialog.show();
            responseMsg = new LoginResponse();
            SessionManager sessionManager = new SessionManager();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<LoginResponse> callEnqueue = userAPICall.uomsave(sessionManager.getToken(this), uomSave);
            callEnqueue.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    responseMsg = response.body();
                    if (responseMsg.getMsg().equals("Success")) {
                        Intent login = new Intent(CreateUomActivity.this, Dashboard.class);
                        startActivity(login);
                        finish();
                    } else {
                        Toast.makeText(CreateUomActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });
        }
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


