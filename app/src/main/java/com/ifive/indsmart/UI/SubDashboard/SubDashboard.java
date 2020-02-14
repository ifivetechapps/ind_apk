package com.ifive.indsmart.UI.SubDashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.DashBoard.DashboardItemsList;
import com.ifive.indsmart.UI.Masters.CreateCustomerActivity;
import com.ifive.indsmart.UI.Masters.CreateSupplierActivity;
import com.ifive.indsmart.UI.Masters.CreateUomActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.ListEnquiryActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.ListQuotationActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.ApproveListRequisitionActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.CreateRequisitionActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.ListRequisitionActivity;
import com.ifive.indsmart.UI.Sales.SalesCreate.AllSalesList;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryList;
import com.ifive.indsmart.UI.Sales.SalesInvoice.SalesInvoiceActivity;
import com.ifive.indsmart.UI.Sales.SalesQuote.AllQuoteList;
import com.ifive.indsmart.UI.SalesApprove.SalesApproveActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SubDashboard extends BaseActivity {

    @BindView(R.id.dashboard_menu)
    RecyclerView dashboardMenu;
    List<DashboardItemsList> dashboardItemsList;
    SubDashboardAdapter dashboardListAdapter;
    ActionBar actionBar;
    String typeName;
    Realm realm;
    private SessionManager sessionManager;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.dashboard_activity);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        sessionManager = new SessionManager();
        pDialog = IFiveEngine.getProgDialog(this);
        typeName = getIntent ().getExtras ().getString ("type");
        Log.d ("aaaa", "" + typeName);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        getMenuItems ();

    }




    private void getMenuItems() {
        if (typeName.equals ("Sales")) {
            dashboardItemsList = new ArrayList<> ();
            dashboardItemsList.add (setMenuItem (SalesEnquiryList.class, "Sales Enquiry", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (AllQuoteList.class, "Sales Quote", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (AllSalesList.class, "Sales Create", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (SalesInvoiceActivity.class, "Sales Invoice", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (SalesApproveActivity.class, "Sales Approve", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));

            setMenuRecycler ();
        } else if (typeName.equals ("Purchase")) {
            dashboardItemsList = new ArrayList<> ();
            dashboardItemsList.add (setMenuItem (CreateRequisitionActivity.class, "Create Requisition", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (ListRequisitionActivity.class, "Requisition List", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (ApproveListRequisitionActivity.class, "Requisition Approval List", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (ListEnquiryActivity.class, "List Enquiry", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (ListQuotationActivity.class, "List Quote", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            setMenuRecycler ();
        } else if (typeName.equals ("Dispatch")) {
            dashboardItemsList = new ArrayList<> ();
            // dashboardItemsList.add(setMenuItem(PaymentReceivableActivityList.class, "Payment Receivable", R.drawable.ic_keyboard_arrow_right, null, R.color.dark));
            setMenuRecycler ();
        } else if (typeName.equals ("Inventory")) {
            dashboardItemsList = new ArrayList<> ();
            setMenuRecycler ();
        } else if (typeName.equals ("Master")) {
            dashboardItemsList = new ArrayList<> ();
            dashboardItemsList.add (setMenuItem (CreateUomActivity.class, "Create Uom", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (CreateSupplierActivity.class, "Create Supplier", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (CreateCustomerActivity.class, "Create Customer", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            setMenuRecycler ();
        } else if (typeName.equals ("Production")) {
            dashboardItemsList = new ArrayList<> ();
            setMenuRecycler ();
        }
    }

    private DashboardItemsList setMenuItem(Class<?> className, String menuName,
                                           int iconPath, List<DashboardItemsList> subMenuItems, int colorID) {
        DashboardItemsList menuItemsList = new DashboardItemsList ();
        menuItemsList.setaClass (className);
        menuItemsList.setIconID (iconPath);
        menuItemsList.setMenuName (menuName);
        menuItemsList.setColorID (colorID);
        menuItemsList.setSubMenuItemsList (subMenuItems);
        return menuItemsList;
    }

    private void setMenuRecycler() {
        dashboardListAdapter = new SubDashboardAdapter (this, dashboardItemsList);
        int numberOfColumns = 1;
        dashboardMenu.setLayoutManager (new GridLayoutManager (this, numberOfColumns));
        dashboardMenu.setAdapter (dashboardListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume ();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
