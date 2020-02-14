package com.ifive.indsmart.UI.logout;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.login.LoginActivity;


public class LogoutActivity extends BaseActivity {

    SessionManager sessionManager;
    TextView logoutProcessing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        getSupportActionBar().hide();
        sessionManager = new SessionManager();
        logoutProcessing = findViewById(R.id.logout_processing);
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(9000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = logoutProcessing.getWidth();
                final float translationX = width * progress;
                logoutProcessing.setTranslationX(translationX);
            }
        });
        animator.start();
        logOut();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void logOut(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logging Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (IFiveEngine.isNetworkAvailable (LogoutActivity.this)) {
                            sessionOut();
                        } else {
                          //  IFiveEngine.myInstance.snackbarNoInternet(LogoutActivity.this);
                        }
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                })
                .show();
    }



    private void sessionOut() {
        sessionManager.logoutSession(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }




}
