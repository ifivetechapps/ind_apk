package com.ifive.indsmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.UI.DashBoard.Dashboard;
import com.ifive.indsmart.UI.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        sessionManager = new SessionManager ();
    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (sessionManager.getToken (this) == null) {

            startActivity (new Intent (MainActivity.this, LoginActivity.class));
        } else {
            startActivity (new Intent (MainActivity.this, Dashboard.class));

        }
    }
}
