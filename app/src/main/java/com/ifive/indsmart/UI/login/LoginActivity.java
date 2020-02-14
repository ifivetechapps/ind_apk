package com.ifive.indsmart.UI.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.ifive.indsmart.Connectivity.LoginRequest;
import com.ifive.indsmart.Connectivity.LoginResponse;
import com.ifive.indsmart.Engine.RetroFitEngine;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Connectivity.UserAPICall;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.DashBoard.Dashboard;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private LocationManager locationManager;
    @BindView(R.id.emailID)
    EditText emailID;
    @BindView(R.id.password)
    EditText password;
    SessionManager sessionManager;
    ProgressDialog pDialog;
    private String deviceNumber="1";
    LoginResponse responseMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        sessionManager = new SessionManager();
        pDialog = IFiveEngine.getProgDialog (this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.lite_grey));
        }

        //
    }


    @OnClick(R.id.login_button)
    public void openMain(){
if(IFiveEngine.myInstance.isEmpty(emailID.getText().toString())) {
            emailID.setError("Username is required!");
        }else if(IFiveEngine.myInstance.isEmpty(password.getText().toString())) {
            password.setError("Wrong Password!");
        }else{
            LoginRequest loginRequest = new LoginRequest ();
            loginRequest.setUsername(emailID.getText().toString());
            loginRequest.setPassword(password.getText().toString());
            loginAPI(loginRequest);
        }
    }


    private void loginAPI(LoginRequest loginRequest) {
        if(IFiveEngine.isNetworkAvailable (this)) {
            pDialog.show();
            responseMsg = new LoginResponse();

            loginRequest.setFcmToken(FirebaseInstanceId.getInstance().getToken());

            UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
            Call<LoginResponse> callEnqueue = userAPICall.login(loginRequest);
            callEnqueue.enqueue(new Callback<LoginResponse> () {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    responseMsg = response.body();
                    Log.e("viswa_check", response.body()+"");
                    if (responseMsg != null) {
                        if (responseMsg.getMsg ().equals("Success")) {
                            sessionManager.setPreferences(LoginActivity.this, responseMsg.getResult ());
                            Intent login = new Intent(LoginActivity.this, Dashboard.class);
                            startActivity(login);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Invalid Username Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if ((pDialog != null) && pDialog.isShowing())
                        pDialog.dismiss();

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if ((pDialog != null) && pDialog.isShowing())
                        pDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Please check the ID or Password", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
          //  IFiveEngine.myInstance.snackbarNoInternet(this);
        }
    }





    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
