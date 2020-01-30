package com.ifiveuv.indsmart.UI.Sales.SalesCreate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.CommanAdapter.CustomerListAdapter;
import com.ifiveuv.indsmart.CommanAdapter.TaxTypeAdapter;
import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Adapter.SalesLineAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesLine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateSalesActivity extends BaseActivity implements RecyclerItemTouchHelperSalesLine.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner , TaxTypeAdapter.taxonItemClickListner{


    @BindView(R.id.so_date)
    TextView so_date;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.type_of_order)
    TextView typeOfOrder;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.total_tax)
    TextView total_tax;
    @BindView(R.id.gross_amount)
    TextView gross_amount;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ActionBar actionBar;
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int nextId, cus_id = 0,tax_id;
    SaleItemList salesItemLists;
    private Menu menu;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    RealmList<SalesItemLineList> salesItemLineLists = new RealmList<> ();
    SalesLineAdapter salesAdapter;
    List<AllDataList> allDataLists = new ArrayList<AllDataList> ();
    double tax_value = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sales_order);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();


        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        pDialog = IFiveEngine.getProgDialog (this);
        sessionManager = new SessionManager ();
        sodateCalendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance ();
        Intent intent = getIntent ();
        typeOfOrder.setText (intent.getStringExtra ("type"));
        so_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (sodateCalendar));
        tax.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                loadTaxName ();

            }
        });
        loadCustomerName ();
        allDataLists.addAll (realm.where (AllDataList.class).findAll ());
        salesItemLineLists.add (new SalesItemLineList ());
        loadItemAdapter ();

    }
    private void loadTaxName() {


        View addItemView = LayoutInflater.from (this)
                .inflate (R.layout.autosearch_recycler, null);
        chartDialog = new AlertDialog.Builder (this);
        chartDialog.setView (addItemView);
        chartAlertDialog = chartDialog.show ();

        chartAlertDialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
        RecyclerView townsDataList = addItemView.findViewById (R.id.items_data_list);
        final EditText search_type = addItemView.findViewById (R.id.search_type);
        TextView textTitle = addItemView.findViewById (R.id.text_title);
        textTitle.setText ("Customer");

        final TaxTypeAdapter itemShowAdapter = new TaxTypeAdapter (this, allDataLists.get (0).getTaxType (), this);

        townsDataList.setAdapter (itemShowAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        townsDataList.setLayoutManager (mLayoutManager);
        townsDataList.setItemAnimator (new DefaultItemAnimator ());

    }
    private void loadItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        salesAdapter = new SalesLineAdapter (this, salesItemLineLists, products, this);
        itemRecyclerView.setAdapter (salesAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        salesAdapter.notifyDataSetChanged ();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesLine (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);
    }


    @OnClick(R.id.customer_Name)
    public void loadCustomerName() {

        View addItemView = LayoutInflater.from (this)
                .inflate (R.layout.autosearch_recycler, null);
        chartDialog = new AlertDialog.Builder (this);
        chartDialog.setView (addItemView);
        chartAlertDialog = chartDialog.show ();

        chartAlertDialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
        RecyclerView townsDataList = addItemView.findViewById (R.id.items_data_list);
        final EditText search_type = addItemView.findViewById (R.id.search_type);
        TextView textTitle = addItemView.findViewById (R.id.text_title);
        textTitle.setText ("Customer");

        final CustomerListAdapter itemShowAdapter = new CustomerListAdapter (this, allDataLists.get (0).getCustomerLists (), this);

        townsDataList.setAdapter (itemShowAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        townsDataList.setLayoutManager (mLayoutManager);
        townsDataList.setItemAnimator (new DefaultItemAnimator ());
        search_type.addTextChangedListener (new TextWatcher () {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_type.getText ().toString ().toLowerCase (Locale.getDefault ());
                itemShowAdapter.filter (text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    @OnClick(R.id.submit_data)
    public void submitdata() {
        int position = salesItemLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (salesItemLineLists.get (position - 1).getLineTotal ().equals ("")) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }

    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = salesItemLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (salesItemLineLists.get (position - 1).getLineTotal ()==null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }

    private void headerdraftSave() {


        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemLists = new SaleItemList ();
        salesItemLists.setSalesOrderid (nextId);
        salesItemLists.setSodate (so_date.getText ().toString ());
        salesItemLists.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemLists.setCus_id (cus_id);
        salesItemLists.setDel_date (delivery_date.getText ().toString ());
        salesItemLists.setStatus ("Opened");
        salesItemLists.setOnlinestatus ("0");
        salesItemLists.setTaxType (tax.getText().toString());
        salesItemLists.setTaxTypeID (String.valueOf (tax_id));
        salesItemLists.setTaxValue (String.valueOf (tax_value));
        salesItemLists.setTotalTax (total_tax.getText().toString());
        salesItemLists.setNetPrice (gross_amount.getText().toString());
        salesItemLists.setTypeOfOrder (typeOfOrder.getText ().toString ());
        salesItemLists.setTotalPrice (total_price.getText ().toString ());
        salesItemLists.setSalesItemLineLists (salesItemLineLists);
        uploadLocalPurchase (salesItemLists);
    }


    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (CreateSalesActivity.this, new DatePickerDialog.OnDateSetListener () {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                deldateCalendar.set (Calendar.YEAR, year);
                deldateCalendar.set (Calendar.MONTH, monthOfYear);
                deldateCalendar.set (Calendar.DAY_OF_MONTH, dayOfMonth);
                delivery_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (deldateCalendar));

            }
        }, deldateCalendar.get (Calendar.YEAR), deldateCalendar.get (Calendar.MONTH), deldateCalendar.get (Calendar.DAY_OF_MONTH));
        dialog.show ();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater ().inflate (R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId ();
        if (id == R.id.add_item) {
            int position = salesItemLineLists.size ();
            insertItem (position);
        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        salesItemLineLists.add (new SalesItemLineList ());
        salesAdapter.notifyItemInserted (position);
    }


    public void setProductList(int pos, int pro_id, String name, int uomId, String uomName) {
        realm.beginTransaction ();
        salesItemLineLists.get (pos).setProduct (name);
        salesItemLineLists.get (pos).setProductId (String.valueOf (pro_id));
        salesItemLineLists.get (pos).setUomId (uomId);
        salesItemLineLists.get (pos).setUom (uomName);
        realm.commitTransaction ();
    }

    public void setQuantity(int position, int quant) {
        salesItemLineLists.get (position).setQuantity (quant);
    }

    public void grandTotal(List<SalesItemLineList> items) {
        double grosspay = 0.0;
        double tax_total = 0.0;
        double totalPrice = 0.0;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getLineTotal ()!=null){
                totalPrice += Double.parseDouble (items.get(i).getLineTotal ());
            }
        }

        total_price.setText (String.valueOf (totalPrice));
        tax_total = (tax_value / 100) * totalPrice;
        grosspay = totalPrice + tax_total;
        total_price.setText (totalPrice + "");
        total_tax.setText (tax_total + "");
        gross_amount.setText (grosspay + "");

    }


    public void headerSave() {

        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemLists = new SaleItemList ();
        salesItemLists.setSalesOrderid (nextId);
        salesItemLists.setSodate (so_date.getText ().toString ());
        salesItemLists.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemLists.setCus_id (cus_id);
        salesItemLists.setDel_date (delivery_date.getText ().toString ());
        salesItemLists.setStatus ("Submitted");
        salesItemLists.setOnlinestatus ("0");
        salesItemLists.setTaxType (tax.getText().toString());
        salesItemLists.setTaxTypeID (String.valueOf (tax_id));
        salesItemLists.setTaxValue (String.valueOf (tax_value));
        salesItemLists.setTotalTax (total_tax.getText().toString());
        salesItemLists.setNetPrice (gross_amount.getText().toString());
        salesItemLists.setTypeOfOrder (typeOfOrder.getText ().toString ());

        salesItemLists.setTotalPrice (total_price.getText ().toString ());
        salesItemLists.setSalesItemLineLists (salesItemLineLists);
        uploadLocalPurchase (salesItemLists);
    }

    private void uploadLocalPurchase(final SaleItemList salesItemList) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        SaleItemList allSalesOrder = realm.copyToRealm (salesItemList);
                        realm.commitTransaction ();
                        Intent intent = new Intent (CreateSalesActivity.this, Dashboard.class);
                        startActivity (intent);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d ("Test", "In onError()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        realm.executeTransaction (new Realm.Transaction () {
                            @Override
                            public void execute(Realm realm) {
                            }
                        });
                    }
                });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SalesLineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = salesItemLineLists.get (viewHolder.getAdapterPosition ()).getProduct ();
            // backup of removed item for undo purpose
            final SalesItemLineList deletedItem = salesItemLineLists.get (viewHolder.getAdapterPosition ());
            final int deletedIndex = viewHolder.getAdapterPosition ();
            // remove the item from recycler view
            salesAdapter.removeItem (viewHolder.getAdapterPosition ());
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make (((Activity) this).findViewById (android.R.id.content),
                            name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction ("UNDO", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    salesAdapter.restoreItem (deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor (Color.YELLOW);
            snackbar.show ();
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (CreateSalesActivity.this, SubDashboard.class);
        it.putExtra ("type", "Sales");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }

    public void setUnitPrice(int mPosition, int uni) {
        salesItemLineLists.get (mPosition).setUnitPrice (String.valueOf (uni));

    }

    public void setLineTotal(int mPosition, double disper, double unit_quan, double disamt, double amount) {
        salesItemLineLists.get (mPosition).setDisPer (String.valueOf (disper));
        salesItemLineLists.get (mPosition).setOrgCost (String.valueOf (unit_quan));
        salesItemLineLists.get (mPosition).setDisAmt (String.valueOf (disamt));
        salesItemLineLists.get (mPosition).setLineTotal (String.valueOf (amount));
        grandTotal (salesItemLineLists);

    }
    @Override
    public void onItemtaxPostion(int position) {
        String Name = allDataLists.get (0).getTaxType ().get (position).getTaxType ();
        int id =  allDataLists.get (0).getTaxType ().get (position).getTaxTypeId ();
        tax_value = Double.parseDouble ( allDataLists.get (0).getTaxType ().get (position).getTaxValue ());
        tax.setText (Name);
        tax.setError (null);
        tax_id = id;
        chartAlertDialog.dismiss ();

    }

    @Override
    public void onItemPostion(int position) {
        String Name =  allDataLists.get (0).getCustomerLists ().get (position).getCusName ();
        int id =  allDataLists.get (0).getCustomerLists ().get (position).getCusNo ();
        customer_Name.setText (Name);
        customer_Name.setError (null);
        cus_id = id;
        chartAlertDialog.dismiss ();
    }
}

