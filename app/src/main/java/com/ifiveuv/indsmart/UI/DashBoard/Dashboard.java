package com.ifiveuv.indsmart.UI.DashBoard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Connectivity.UserAPICall;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.Engine.RetroFitEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.CreateRequisitionActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryList;
import com.ifiveuv.indsmart.UI.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class Dashboard extends BaseActivity {

    private static final String TAG = Dashboard.class.getSimpleName ();
    @BindView(R.id.dashboard_menu)
    RecyclerView dashboardMenu;
    List<DashboardItemsList> dashboardItemsList;
    DashboardListAdapter dashboardListAdapter;
    Calendar myCalendar, endCalendar;
    boolean doubleBackToExitPressedOnce = false;
    ActionBar actionBar;
    Realm realm;
    AllDataList allDataList;
    private SessionManager sessionManager;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.dashboard_activity);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        sessionManager = new SessionManager ();
        pDialog = IFiveEngine.getProgDialog (this);
        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        getMenuItems ();
        loadData ();
    }


            private void getMenuItems() {
        dashboardItemsList = new ArrayList<> ();
        dashboardItemsList.add (setMenuItem (SalesEnquiryList.class, "Sales", R.drawable.sale, null, R.color.colorPrimaryDark));
        dashboardItemsList.add (setMenuItem (CreateRequisitionActivity.class, "Purchase", R.drawable.purchase, null, R.color.colorPrimaryDark));
        setMenuRecycler ();
    }

    public void loadData() {
        RealmResults<AllDataList> p = realm.where (AllDataList.class).findAll ();
        if (p.size () == 0) {
            realm.beginTransaction ();
            p.deleteAllFromRealm ();
            realm.commitTransaction ();
            if (IFiveEngine.isNetworkAvailable (this)) {
                pDialog.show ();

                UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
                Call<AllDataList> callEnqueue = userAPICall.allDataList (sessionManager.getToken (this));
                callEnqueue.enqueue (new Callback<AllDataList> () {
                    @Override
                    public void onResponse(Call<AllDataList> call, Response<AllDataList> response) {
                        uploadToRealmDB (response.body ());

                        if ((pDialog != null) && pDialog.isShowing ())
                            pDialog.dismiss ();
                    }

                    @Override
                    public void onFailure(Call<AllDataList> call, Throwable t) {
                        if ((pDialog != null) && pDialog.isShowing ())
                            pDialog.dismiss ();
                        Log.d ("throwing Error",t.getMessage ());
                        Toast.makeText (Dashboard.this, "Please check the ID or Password", Toast.LENGTH_SHORT).show ();
                    }
                });
            } else {
                //  IFiveEngine.myInstance.snackbarNoInternet(this);
            }
        }


    }
    private void uploadToRealmDB(final AllDataList body) {

        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        AllDataList allDatasResponse = realm.copyToRealm(body);
                        realm.commitTransaction();

                        Toast.makeText(Dashboard.this, "Sync Completed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", "In onError()");
                        // openMainActivity();
                        Toast.makeText(Dashboard.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();

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

    private DashboardItemsList setMenuItem(Class<?> className, String menuName,
                                           int iconPath, List<DashboardItemsList> subMenuItems, int colorID) {
        DashboardItemsList menuItemsList = new DashboardItemsList();
        menuItemsList.setaClass(className);
        menuItemsList.setIconID(iconPath);
        menuItemsList.setMenuName(menuName);
        menuItemsList.setColorID(colorID);
        menuItemsList.setSubMenuItemsList(subMenuItems);
        return menuItemsList;
    }

    private void setMenuRecycler() {
        dashboardListAdapter = new DashboardListAdapter(this, dashboardItemsList);
        int numberOfColumns = 2;
        dashboardMenu.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        dashboardMenu.setAdapter(dashboardListAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume ();
        if (sessionManager.getToken (this) == null) {

            startActivity (new Intent (Dashboard.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Log.d(TAG, "Dashboard");
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
