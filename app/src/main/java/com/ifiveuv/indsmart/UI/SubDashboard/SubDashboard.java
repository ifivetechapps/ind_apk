package com.ifiveuv.indsmart.UI.SubDashboard;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.DashboardItemsList;
import com.ifiveuv.indsmart.UI.Masters.CreateCustomerActivity;
import com.ifiveuv.indsmart.UI.Masters.CreateSupplierActivity;
import com.ifiveuv.indsmart.UI.Masters.CreateUomActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.ApproveListRequisitionActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.CreateRequisitionActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.ListRequisitionActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.AllSalesList;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryList;
import com.ifiveuv.indsmart.UI.Sales.SalesInvoice.SalesInvoiceActivity;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.AllQuoteList;
import com.ifiveuv.indsmart.UI.SalesApprove.SalesApproveActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubDashboard extends BaseActivity {

    @BindView(R.id.dashboard_menu)
    RecyclerView dashboardMenu;
    List<DashboardItemsList> dashboardItemsList;
    SubDashboardAdapter dashboardListAdapter;
    ActionBar actionBar;
    String typeName;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.dashboard_activity);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        sessionManager = new SessionManager ();
        typeName = getIntent ().getExtras ().getString ("type");
        Log.d ("aaaa", "" + typeName);
        getMenuItems ();
    }


    private void getMenuItems() {
        if (typeName.equals ("Sales")) {
            dashboardItemsList = new ArrayList<> ();
            dashboardItemsList.add (setMenuItem (SalesEnquiryList.class, "Sales Enquiry", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (AllQuoteList.class, "Sales Quote", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add (setMenuItem (AllSalesList.class, "Sales Create", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add(setMenuItem(SalesInvoiceActivity.class, "Sales Invoice", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add(setMenuItem(SalesApproveActivity.class, "Sales Approve", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));

            setMenuRecycler ();
        } else if (typeName.equals ("Purchase")) {
            dashboardItemsList = new ArrayList<> ();
            dashboardItemsList.add(setMenuItem(CreateRequisitionActivity.class, "Create Requisition", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add(setMenuItem(ListRequisitionActivity.class, "Requisition List", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));
            dashboardItemsList.add(setMenuItem(ApproveListRequisitionActivity.class, "Requisition Approval List", R.drawable.ic_keyboard_arrow_right, null, R.color.colorPrimaryDark));


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
