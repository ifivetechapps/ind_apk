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
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Adapter.EnquiryToSalesLineAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.EnquiryToQuoteLineAdapter;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperConvertEnquiryToQuote;

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

public class ConvertFromEnquiryToSalesActivity extends BaseActivity implements RecyclerItemTouchHelperConvertEnquiryToQuote.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner , TaxTypeAdapter.taxonItemClickListner   {
    int hdrid;
    EnquiryToSalesLineAdapter salesAdapter;
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
    String onlineSE;
    ActionBar actionBar;
    RealmList<EnquiryLineList> enquiryLineLists = new RealmList<>();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int nextId, cus_id = 0,tax_id;
    SaleItemList salesItemLists;
    RealmResults<EnquiryItemModel> enquiryItemModels;
    private Menu menu;
    AlertDialog.Builder chartDialog;

    AlertDialog chartAlertDialog;

    ProgressDialog pDialog;
    List<AllDataList> allDataLists = new ArrayList<AllDataList> ();
    SessionManager sessionManager;
    double tax_value = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        hdrid = Integer.parseInt(intent.getStringExtra("hdrid"));

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        pDialog = IFiveEngine.getProgDialog (this);
        sessionManager = new SessionManager ();
        sodateCalendar = Calendar.getInstance();
        deldateCalendar = Calendar.getInstance();
        allDataLists.addAll (realm.where (AllDataList.class).findAll ());
        so_date.setText(IFiveEngine.myInstance.getSimpleCalenderDate(sodateCalendar));
        //loadData();
        tax.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                loadTaxName ();

            }
        });
        loadCustomerName();

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
//    private void loadData() {
//        enquiryItemModels = realm.where(EnquiryItemModel.class)
//                .equalTo("enquiryId", hdrid)
//                .findAll();
//        delivery_date.setText(enquiryItemModels.get(0).getDeliveryDate ());
//        so_date.setText(enquiryItemModels.get(0).getEnquiryDate ());
//        so_status.setText(enquiryItemModels.get(0).getEnquiryStatus ());
//
//        customer_Name.setText(enquiryItemModels.get(0).getEnquiryCustomerName ());
//        typeOfOrder.setText(enquiryItemModels.get(0).getEnquiryType ());
//        cus_id = enquiryItemModels.get(0).getEnquiryCustomerId ();
//        onlineSE = enquiryItemModels.get(0).getEnqOnlineId ();
//        enquiryLineLists.addAll(enquiryItemModels.get(0).getEnquiryLineLists());
//        List<Products> products = new ArrayList<Products>();
//        products.addAll(realm.where(Products.class).findAll());
//        salesAdapter = new EnquiryToSalesLineAdapter (this, enquiryLineLists, products, this);
//        itemRecyclerView.setAdapter(salesAdapter);
//        mLayoutManager = new LinearLayoutManager(this);
//        itemRecyclerView.setLayoutManager(mLayoutManager);
//        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        salesAdapter.notifyDataSetChanged();
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperConvertEnquiryToQuote(0, ItemTouchHelper.LEFT, this);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(itemRecyclerView);
//
//    }
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

    @OnClick(R.id.submit_data)
    public void submitdata() {
        int position = enquiryLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (enquiryLineLists.get (position - 1).getLineTotal ().equals ("")) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }


    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = enquiryLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (enquiryLineLists.get (position - 1).getLineTotal ().equals ("")) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave();
        }



    }

    private void headerdraftSave() {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                EnquiryItemModel enquiryItemModel = realm.where(EnquiryItemModel.class).equalTo("enquiryId", hdrid).findFirst();
//                enquiryItemModel.setEnquiryStatus ("converted");
//            }
//        });

        Number currentIdNum = realm.where(SaleItemList.class).max("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        salesItemLists = new SaleItemList();
        salesItemLists.setSalesOrderid (nextId);
        salesItemLists.setSeId (hdrid);
        salesItemLists.setOnlineseId (onlineSE);
        salesItemLists.setSodate (so_date.getText().toString());
        salesItemLists.setCus_name (customer_Name.getText().toString().trim());
        salesItemLists.setCus_id (cus_id);
        salesItemLists.setTaxType (tax.getText().toString());
        salesItemLists.setTaxTypeID (String.valueOf (tax_id));
        salesItemLists.setTaxValue (String.valueOf (tax_value));
        salesItemLists.setTotalTax (total_tax.getText().toString());
        salesItemLists.setNetPrice (gross_amount.getText().toString());
        salesItemLists.setDel_date (delivery_date.getText().toString());
        salesItemLists.setStatus ("Opened");
        salesItemLists.setOnlinestatus ("0");
        salesItemLists.setTypeOfOrder (typeOfOrder.getText().toString());
        salesItemLists.setTotalPrice(total_price.getText().toString());
        uploadLocalPurchase(salesItemLists, nextId);
    }


    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog(ConvertFromEnquiryToSalesActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                deldateCalendar.set(Calendar.YEAR, year);
                deldateCalendar.set(Calendar.MONTH, monthOfYear);
                deldateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                delivery_date.setText(IFiveEngine.myInstance.getSimpleCalenderDate(deldateCalendar));

            }
        }, deldateCalendar.get(Calendar.YEAR), deldateCalendar.get(Calendar.MONTH), deldateCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_item) {
            int position = enquiryLineLists.size();
            insertItem(position);
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertItem(int position) {
        enquiryLineLists.add(new EnquiryLineList());
        salesAdapter.notifyItemInserted(position);
    }


    public void setProductList(int pos,int pro_id, String name, int uomId, String uomName) {
        realm.beginTransaction();
        enquiryLineLists.get(pos).setEnquiryProduct (name);
        enquiryLineLists.get(pos).setProductId (String.valueOf (pro_id));
        enquiryLineLists.get(pos).setEnquiryUomId (uomId);
        enquiryLineLists.get(pos).setEnquiryUom (uomName);
        realm.commitTransaction();
    }

    public void setQuantity(int position, int quant) {
       enquiryLineLists.get(position).setEnquiryRequiredQuantity (String.valueOf(quant));
    }

    public void grandTotal(List<EnquiryLineList> items) {
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
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                EnquiryItemModel enquiryItemModel = realm.where(EnquiryItemModel.class).equalTo("enquiryId", hdrid).findFirst();
//                enquiryItemModel.setEnquiryStatus ("converted");
//            }
//        });

        Number currentIdNum = realm.where(SaleItemList.class).max("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        salesItemLists = new SaleItemList();
        salesItemLists.setSalesOrderid (nextId);
        salesItemLists.setSeId (hdrid);
        salesItemLists.setOnlineseId (onlineSE);
        salesItemLists.setSodate (so_date.getText().toString());
        salesItemLists.setTaxType (tax.getText().toString());
        salesItemLists.setTaxTypeID (String.valueOf (tax_id));
        salesItemLists.setTaxValue (String.valueOf (tax_value));
        salesItemLists.setTotalTax (total_tax.getText().toString());
        salesItemLists.setNetPrice (total_price.getText().toString());
        salesItemLists.setTotalPrice(gross_amount.getText().toString());
        salesItemLists.setCus_name (customer_Name.getText().toString().trim());
        salesItemLists.setCus_id (cus_id);
        salesItemLists.setDel_date (delivery_date.getText().toString());
        salesItemLists.setStatus ("Submitted");
        salesItemLists.setOnlinestatus ("0");
        salesItemLists.setTypeOfOrder (typeOfOrder.getText().toString());

        uploadLocalPurchase(salesItemLists, nextId);
    }

    private void uploadLocalPurchase(final SaleItemList salesItemList, final int nextid) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        SaleItemList allSalesOrder = realm.copyToRealm(salesItemList);
                        realm.commitTransaction();
                        uploadLine(nextid);
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

    private void uploadLine(int nextid) {

        realm.beginTransaction();
        for (int i = 0; i < enquiryLineLists.size(); i++) {
            SalesItemLineList salesItemLineList = realm.createObject(SalesItemLineList.class);
            salesItemLineList.setProductPosition(enquiryLineLists.get(i).getEnquiryProductPosition ());
            salesItemLineList.setProduct(enquiryLineLists.get(i).getEnquiryProduct ());
            salesItemLineList.setProductId (enquiryLineLists.get(i).getProductId ());

            salesItemLineList.setUomId(enquiryLineLists.get(i).getEnquiryUomId ());
            salesItemLineList.setUom(enquiryLineLists.get(i).getEnquiryUom ());

            salesItemLineList.setUnitPrice(enquiryLineLists.get(i).getUnitPrice());
            salesItemLineList.setQuantity (Integer.valueOf (enquiryLineLists.get(i).getEnquiryRequiredQuantity ()));
            salesItemLineList.setDisPer (enquiryLineLists.get(i).getDiscountPercent ());
            salesItemLineList.setDisAmt (enquiryLineLists.get(i).getDiscountAmount ());
            salesItemLineList.setOrgCost (enquiryLineLists.get(i).getOriginalCost ());
            salesItemLineList.setLineTotal (enquiryLineLists.get(i).getLineTotal ());

            SaleItemList saleItemList = realm.where(SaleItemList.class).equalTo("SalesOrderid", nextid).findFirst();
            saleItemList.getSalesItemLineLists ().add(salesItemLineList);
        }

        realm.commitTransaction();
        Intent intent = new Intent(ConvertFromEnquiryToSalesActivity.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EnquiryToQuoteLineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = enquiryLineLists.get(viewHolder.getAdapterPosition()).getEnquiryProduct ();
            // backup of removed item for undo purpose
            final EnquiryLineList deletedItem = enquiryLineLists.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            // remove the item from recycler view
            salesAdapter.removeItem(viewHolder.getAdapterPosition());
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(((Activity) this).findViewById(android.R.id.content),
                            name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    salesAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
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
    public void onItemtaxPostion(int position) {
        String Name = allDataLists.get (0).getTaxType ().get (position).getTaxType ();
        int id = allDataLists.get (0).getTaxType ().get (position).getTaxTypeId ();
        tax_value = Double.parseDouble (allDataLists.get (0).getTaxType ().get (position).getTaxValue ());
        tax.setText (Name);
        tax.setError (null);
        tax_id = id;
        chartAlertDialog.dismiss ();

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(ConvertFromEnquiryToSalesActivity.this, SubDashboard.class);
        it.putExtra("type", "Sales");
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    public void setUnitPrice(int mPosition, int uni) {
        enquiryLineLists.get (mPosition).setUnitPrice (String.valueOf (uni));

    }

    public void setLineTotal(int mPosition, double disper, double unit_quan, double disamt, double amount) {
        enquiryLineLists.get (mPosition).setDiscountPercent (String.valueOf (disper));
        enquiryLineLists.get (mPosition).setOriginalCost (String.valueOf (unit_quan));
        enquiryLineLists.get (mPosition).setDiscountAmount (String.valueOf (disamt));
        enquiryLineLists.get (mPosition).setLineTotal (String.valueOf (amount));
        grandTotal(enquiryLineLists);

    }
}

