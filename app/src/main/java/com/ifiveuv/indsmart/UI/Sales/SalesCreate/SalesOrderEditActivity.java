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
import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Adapter.SalesLineEditAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.QuoteEditAdapter;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesQuoteEdit;

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
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SalesOrderEditActivity extends BaseActivity implements RecyclerItemTouchHelperSalesQuoteEdit.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner{
    @BindView(R.id.so_date)
    TextView so_date;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.type_of_order)
    TextView typeOfOrder;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.gross_amount)
    TextView gross_amount;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ActionBar actionBar;
    RealmList<SalesItemLineList> salesItemLineList = new RealmList<> ();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int hdrid, cus_id = 0;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    SaleItemList salesItemList;
    private Menu menu;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    SalesLineEditAdapter salesAdapter;
    String typeof;
    int nextId;
    List<AllDataList> allDataLists = new ArrayList<AllDataList> ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.edit_sales_order);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        pDialog = IFiveEngine.getProgDialog (this);
        sessionManager = new SessionManager ();

        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        Intent intent = getIntent ();
        hdrid = Integer.parseInt (intent.getStringExtra ("hdrid"));
        typeof = intent.getStringExtra ("typeof");
        sodateCalendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance ();

        allDataLists.addAll (realm.where (AllDataList.class).findAll ());

        so_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (sodateCalendar));
        if (typeof.equals ("edit")) {
            loadData ();
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {

                    int position = salesItemLineList.size ();

                    if (customer_Name.getText ().toString ().equals ("")) {
                        customer_Name.setError ("Required");
                    } else if (delivery_date.getText ().toString ().equals ("")) {
                        delivery_date.setError ("Required");
                    } else if (salesItemLineList.get (position - 1).getLineTotal ().equals ("")) {

                        Toast.makeText (SalesOrderEditActivity.this, "Please Enter the above row", Toast.LENGTH_SHORT).show ();
                    } else {
                        editheaderSave();
                    }



                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {

                    int position = salesItemLineList.size ();

                    if (customer_Name.getText ().toString ().equals ("")) {
                        customer_Name.setError ("Required");
                    } else if (delivery_date.getText ().toString ().equals ("")) {
                        delivery_date.setError ("Required");
                    } else if (salesItemLineList.get (position - 1).getLineTotal ().equals ("")) {

                        Toast.makeText (SalesOrderEditActivity.this, "Please Enter the above row", Toast.LENGTH_SHORT).show ();
                    } else {
                        editDraftSave ();
                    }


                }
            });

        } else {
            loadData ();
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    int position = salesItemLineList.size ();

                    if (customer_Name.getText ().toString ().equals ("")) {
                        customer_Name.setError ("Required");
                    } else if (delivery_date.getText ().toString ().equals ("")) {
                        delivery_date.setError ("Required");
                    } else if (salesItemLineList.get (position - 1).getLineTotal ().equals ("")) {

                        Toast.makeText (SalesOrderEditActivity.this, "Please Enter the above row", Toast.LENGTH_SHORT).show ();
                    } else {
                        copyheaderSave ();
                    }

                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    int position = salesItemLineList.size ();

                    if (customer_Name.getText ().toString ().equals ("")) {
                        customer_Name.setError ("Required");
                    } else if (delivery_date.getText ().toString ().equals ("")) {
                        delivery_date.setError ("Required");
                    } else if (salesItemLineList.get (position - 1).getLineTotal ().equals ("")) {

                        Toast.makeText (SalesOrderEditActivity.this, "Please Enter the above row", Toast.LENGTH_SHORT).show ();
                    } else {
                        copyDraftSave ();
                    }

                }
            });
        }

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


    private void loadData() {
        RealmResults<SaleItemList> salesItemLists;
        salesItemLists = realm.where (SaleItemList.class)
                .equalTo ("SalesOrderid", hdrid)
                .findAll ();
        delivery_date.setText (salesItemLists.get (0).getDel_date ());
        so_date.setText (salesItemLists.get (0).getSodate ());
        so_status.setText (salesItemLists.get (0).getStatus ());

        typeOfOrder.setText (salesItemLists.get (0).getTypeOfOrder ());
        customer_Name.setText (salesItemLists.get (0).getCus_name ());
        cus_id = salesItemLists.get (0).getCus_id ();

        gross_amount.setText (salesItemLists.get (0).getTotalPrice ());
        salesItemLineList.addAll (realm.copyFromRealm (realm.where (SalesItemLineList.class)
                .equalTo ("salesHdrid", hdrid)
                .findAll ()));

        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        salesAdapter = new SalesLineEditAdapter (this, salesItemLineList, products, this);
        itemRecyclerView.setAdapter (salesAdapter);
        itemRecyclerView.setItemViewCacheSize (salesItemLineList.size ());
        mLayoutManager = new LinearLayoutManager (this);
        salesAdapter.notifyDataSetChanged ();
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesQuoteEdit (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);
    }


    private void editheaderSave() {
        salesItemList = new SaleItemList ();
        salesItemList.setSalesOrderid (hdrid);
        salesItemList.setSodate (so_date.getText ().toString ());
        salesItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemList.setCus_id (cus_id);
        salesItemList.setDel_date (delivery_date.getText ().toString ());
        salesItemList.setStatus ("Submitted");
        salesItemList.setOnlinestatus ("0");
        salesItemList.setNetPrice (gross_amount.getText().toString());
        salesItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());
        uploadLocalPurchase (salesItemList);

    }

    private void copyheaderSave() {
        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemList = new SaleItemList ();
        salesItemList.setSalesOrderid (nextId);
        salesItemList.setSodate (so_date.getText ().toString ());
        salesItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemList.setCus_id (cus_id);
        salesItemList.setDel_date (delivery_date.getText ().toString ());
        salesItemList.setStatus ("Submitted");
        salesItemList.setOnlinestatus ("0");
        salesItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());


        salesItemList.setNetPrice (gross_amount.getText().toString());
        copyuploadLocalPurchase (salesItemList,nextId);

    }

    private void editDraftSave() {
        salesItemList = new SaleItemList ();
        salesItemList.setSalesOrderid (hdrid);
        salesItemList.setSodate (so_date.getText ().toString ());
        salesItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemList.setCus_id (cus_id);
        salesItemList.setDel_date (delivery_date.getText ().toString ());
        salesItemList.setStatus ("Opened");
        salesItemList.setOnlinestatus ("0");
        salesItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());

        salesItemList.setNetPrice (gross_amount.getText().toString());

        uploadLocalPurchase (salesItemList);

    }

    private void copyDraftSave() {
        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemList = new SaleItemList ();
        salesItemList.setSalesOrderid (nextId);
        salesItemList.setSodate (so_date.getText ().toString ());
        salesItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        salesItemList.setCus_id (cus_id);
        salesItemList.setDel_date (delivery_date.getText ().toString ());
        salesItemList.setStatus ("Submitted");
        salesItemList.setOnlinestatus ("0");

        salesItemList.setNetPrice (gross_amount.getText().toString());
        salesItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());


        copyuploadLocalPurchase (salesItemList,nextId);

    }

    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (SalesOrderEditActivity.this, new DatePickerDialog.OnDateSetListener () {

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
            int position = salesItemLineList.size ();
            Log.d ("postion value", String.valueOf (position));
            insertItem (position);
        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(final int position) {
        salesItemLineList.add (new SalesItemLineList ());
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                int nextId_line;
                Number currentIdNum = realm.where (SalesItemLineList.class).max ("saleId");
                if (currentIdNum == null) {
                    nextId_line = 1;
                } else {
                    nextId_line = currentIdNum.intValue () + 1;
                }
                salesItemLineList.get (position).setSaleId (nextId_line);
            }
        });
        salesAdapter.notifyItemInserted (position);
    }


    public void setProductList(int pos, int pro_id, String name, int uomId, String uomName,int tax_id) {
        realm.beginTransaction ();
        salesItemLineList.get (pos).setProduct (name);
        salesItemLineList.get (pos).setProductId (String.valueOf (pro_id));
        salesItemLineList.get (pos).setUomId (uomId);
        salesItemLineList.get (pos).setUom (uomName);
        salesItemLineList.get (pos).setTaxId (tax_id);
        realm.commitTransaction ();
    }

    public void setQuantity(int position, int quant) {
        salesItemLineList.get (position).setQuantity (quant);
    }

    public void setUnitPrice(int mPosition, int uni) {
        salesItemLineList.get (mPosition).setUnitPrice (String.valueOf (uni));

    }

    public void setLineTotal(int mPosition, double disper, double unit_quan, double disamt, double amount) {
        salesItemLineList.get (mPosition).setDisPer (String.valueOf (disper));
        salesItemLineList.get (mPosition).setOrgCost (String.valueOf (unit_quan));
        salesItemLineList.get (mPosition).setDisAmt (String.valueOf (disamt));
        salesItemLineList.get (mPosition).setLineTotal (String.valueOf (amount));
        grandTotal (salesItemLineList);

    }

    public void grandTotal(List<SalesItemLineList> items) {
        double grosspay = 0.0;

        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getLineTotal ()!=null){
                grosspay += Double.parseDouble (items.get(i).getLineTotal ());
            }
        }

        gross_amount.setText (grosspay + "");
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
                        SaleItemList allSalesOrder = realm.copyToRealmOrUpdate (salesItemList);
                        realm.commitTransaction ();
                        lineSave();
                        Intent intent = new Intent (SalesOrderEditActivity.this, SubDashboard.class);
                        intent.putExtra ("type", "Sales");
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

    private void lineSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                for (int j = 0; j < salesItemLineList.size (); j++) {

                    SalesItemLineList salesItemLineLists=new SalesItemLineList ();
                    salesItemLineLists.setSaleId (salesItemLineList.get (j).getSaleId ());
                    salesItemLineLists.setSalesHdrid (hdrid);
                    salesItemLineLists.setTaxId (salesItemLineList.get (j).getTaxId ());
                    salesItemLineLists.setProductPosition (salesItemLineList.get (j).getProductPosition ());
                    salesItemLineLists.setProduct (salesItemLineList.get (j).getProduct ());
                    salesItemLineLists.setProductId (salesItemLineList.get (j).getProductId ());
                    salesItemLineLists.setUomId (salesItemLineList.get (j).getUomId ());
                    salesItemLineLists.setUom (salesItemLineList.get (j).getUom ());
                    salesItemLineLists.setUnitPrice (salesItemLineList.get (j).getUnitPrice ());
                    salesItemLineLists.setQuantity (Integer.valueOf (salesItemLineList.get (j).getQuantity ()));
                    salesItemLineLists.setDisPer (salesItemLineList.get (j).getDisPer ());
                    salesItemLineLists.setDisAmt (salesItemLineList.get (j).getDisAmt ());
                    salesItemLineLists.setOrgCost (salesItemLineList.get (j).getOrgCost ());
                    salesItemLineLists.setLineTotal (salesItemLineList.get (j).getLineTotal ());
                    realm.copyToRealmOrUpdate (salesItemLineLists);
                }
            }
        });
    }

    private void copyuploadLocalPurchase(final SaleItemList salesItemList,final int nextId) {
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
                        lineSave(nextId);
                        Intent intent = new Intent (SalesOrderEditActivity
                                .this, SubDashboard.class);
                        intent.putExtra ("type", "Sales");
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
    private void lineSave(final int nextId) {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                for (int j = 0; j < salesItemLineList.size (); j++) {

                    SalesItemLineList salesItemLineLists=new SalesItemLineList ();
                    salesItemLineLists.setSaleId (salesItemLineList.get (j).getSaleId ());
                    salesItemLineLists.setSalesHdrid (nextId);
                    salesItemLineLists.setProductPosition (salesItemLineList.get (j).getProductPosition ());
                    salesItemLineLists.setProduct (salesItemLineList.get (j).getProduct ());
                    salesItemLineLists.setProductId (salesItemLineList.get (j).getProductId ());
                    salesItemLineLists.setUomId (salesItemLineList.get (j).getUomId ());
                    salesItemLineLists.setTaxId (salesItemLineList.get (j).getTaxId ());
                    salesItemLineLists.setUom (salesItemLineList.get (j).getUom ());
                    salesItemLineLists.setUnitPrice (salesItemLineList.get (j).getUnitPrice ());
                    salesItemLineLists.setQuantity (Integer.valueOf (salesItemLineList.get (j).getQuantity ()));
                    salesItemLineLists.setDisPer (salesItemLineList.get (j).getDisPer ());
                    salesItemLineLists.setDisAmt (salesItemLineList.get (j).getDisAmt ());
                    salesItemLineLists.setOrgCost (salesItemLineList.get (j).getOrgCost ());
                    salesItemLineLists.setLineTotal (salesItemLineList.get (j).getLineTotal ());
                    realm.insert (salesItemLineLists);
                }
            }
        });
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QuoteEditAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = salesItemLineList.get (viewHolder.getAdapterPosition ()).getProduct ();
            // backup of removed item for undo purpose
            final SalesItemLineList deletedItem = salesItemLineList.get (viewHolder.getAdapterPosition ());
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
    public void onItemPostion(int position) {
        String Name = allDataLists.get (0).getCustomerLists ().get (position).getCusName ();
        int id = allDataLists.get (0).getCustomerLists ().get (position).getCusNo ();
        customer_Name.setText (Name);
        customer_Name.setError (null);
        cus_id = id;
        chartAlertDialog.dismiss ();
    }


    @Override
    public void onBackPressed() {
        Intent it = new Intent (SalesOrderEditActivity.this, Dashboard.class);
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
