package com.ifiveuv.indsmart;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ifiveuv.indsmart.Connectivity.LoginRequest;
import com.ifiveuv.indsmart.Connectivity.LoginResponse;
import com.ifiveuv.indsmart.Connectivity.RetroFitEngine;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Connectivity.UserAPICall;
import com.ifiveuv.indsmart.Engine.IFiveEngine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SessionManager sessionManager = new SessionManager();
        if (sessionManager.getToken(this) != null) {
            if(IFiveEngine.isNetworkAvailable (this)) {
                LoginRequest loginRequest= new LoginRequest();
                loginRequest.setFcmToken(refreshedToken);
                UserAPICall userAPICall = RetroFitEngine.getRetrofit().create(UserAPICall.class);
                Call<LoginResponse> callEnqueue = userAPICall.updateFCMToken(sessionManager.getToken(this),loginRequest);
                callEnqueue.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                    }
                });
            }else{
                sessionManager.setFCMToken(this,refreshedToken);
            }

        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}