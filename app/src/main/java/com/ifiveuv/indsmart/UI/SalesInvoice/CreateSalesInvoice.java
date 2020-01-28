package com.ifiveuv.indsmart.UI.SalesInvoice;


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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.CommanAdapter.CustomerListAdapter;
import com.ifiveuv.indsmart.CommanAdapter.TaxTypeAdapter;
import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.RetroFitEngine;
import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.Connectivity.Shipping_address;
import com.ifiveuv.indsmart.Connectivity.Tax_type;
import com.ifiveuv.indsmart.Connectivity.UserAPICall;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.Masters.Model.AllCustomerList;
import com.ifiveuv.indsmart.UI.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.SalesInvoice.Adapter.SalesInvoiceLineAdapter;
import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemLinelist;
import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemList;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesInvoiceLines;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class CreateSalesInvoice extends BaseActivity implements RecyclerItemTouchHelperSalesInvoiceLines.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner, TaxTypeAdapter.taxonItemClickListner  {
    @BindView(R.id.so_id)
    TextView so_id;
    @BindView(R.id.soinv_date)
    TextView soinv_date;
    @BindView(R.id.invcustomer_Name)
    TextView invcustomer_Name;
    @BindView(R.id.invfreight_pay)
    Spinner invfreight_pay;
    @BindView(R.id.invdelivery_date)
    TextView invdelivery_date;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.gross_amount)
    TextView gross_amount;
    @BindView(R.id.total_tax)
    TextView total_tax;
    @BindView(R.id.delivery_address)
    EditText delivery_address;
    @BindView(R.id.freightcost)
    EditText freightcost;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.loadcost)
    LinearLayout loadcost;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String customerNames, invfreightpay;
    ActionBar actionBar;
    Object invfreight;
    Calendar soinvdateCalendar, invdeldateCalendar;
    Realm realm;
    int hdrid, nextId;
    RealmList<SalesItemLineList> salesItemLinesall = new RealmList<>();
    SalesInvoiceLineAdapter salesAdapter;
    InvoiceItemList invoiceItemList;
    int cus_id = 0,tax_id;
    double tax_value;
    private Menu menu;
    RealmList<SaleItemList> salesItemLists;
    Shipping_address shipping_addresses;
    AllDataList allDataList;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    AllCustomerList customerLists;
    double totalPrice = 0;
    double grosspay = 0;
    double freight_cost = 0;
    double tax_total = 0;
    double val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_sales_invoice);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        hdrid = Integer.parseInt(intent.getStringExtra("hdrid"));
        so_id.setText("SO" + hdrid);
        pDialog = IFiveEngine.getProgDialog (this);
        sessionManager = new SessionManager ();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        Log.d("hvfd", String.valueOf(hdrid));
        soinvdateCalendar = Calendar.getInstance();
        invdeldateCalendar = Calendar.getInstance();
        loadSpinnerCustomer();
        invcustomer_Name.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                callCustomerApi ();

            }
        });
        tax.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                loadTaxName ();

            }
        });

        loadData();

    }
    private void callCustomerApi() {
        if (IFiveEngine.isNetworkAvailable (this)) {
            pDialog.show ();

            UserAPICall userAPICall = RetroFitEngine.getRetrofit ().create (UserAPICall.class);
            Call<AllCustomerList> callEnqueue = userAPICall.customerList (sessionManager.getToken (this));
            callEnqueue.enqueue (new Callback<AllCustomerList> () {
                @Override
                public void onResponse(Call<AllCustomerList> call, Response<AllCustomerList> response) {
                    customerLists = response.body ();
                    loadCustomerName ();
                    pDialog.dismiss ();
                }

                @Override
                public void onFailure(Call<AllCustomerList> call, Throwable t) {
                    if ((pDialog != null) && pDialog.isShowing ())
                        pDialog.dismiss ();
                }
            });
        } else {
            IFiveEngine.myInstance.snackbarNoInternet (this);
        }
    }

    private void loadCustomerName() {

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

        final CustomerListAdapter itemShowAdapter = new CustomerListAdapter (this, customerLists.getCustomerLists (),this);

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

    private void loadTaxName() {
        allDataList = realm.where(AllDataList.class).findFirst();

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

        final TaxTypeAdapter itemShowAdapter = new TaxTypeAdapter (this, allDataList.getTax_types (),this);

        townsDataList.setAdapter (itemShowAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        townsDataList.setLayoutManager (mLayoutManager);
        townsDataList.setItemAnimator (new DefaultItemAnimator ());

    }


    private void loadData() {
        salesItemLists = new RealmList<>();
        salesItemLists.addAll(realm.copyFromRealm(realm.where(SaleItemList.class)
                .equalTo("SalesOrderid", hdrid)
                .findAll()));

        invcustomer_Name.setText(salesItemLists.get(0).getCus_name());
        salesItemLinesall.addAll(salesItemLists.get(0).getSalesItemLineLists ());
        if(salesItemLists.get(0).getCus_id()!=0){
            cus_id = salesItemLists.get(0).getCus_id();
            shipping_addresses = realm.where(Shipping_address.class).equalTo("customerId", cus_id).findFirst();
            delivery_address.setText (shipping_addresses.getAddressLaneOne ()+","+shipping_addresses.getAddressLaneTwo ()+",\n"+
                    shipping_addresses.getShipState ()+",\n"+shipping_addresses.getShipCity ()+","+shipping_addresses.getShipPincode ());

        }

        salesAdapter = new SalesInvoiceLineAdapter(this, salesItemLinesall, this);
        itemRecyclerView.setAdapter(salesAdapter);
        itemRecyclerView.setItemViewCacheSize(salesItemLinesall.size());
        mLayoutManager = new LinearLayoutManager(this);
        salesAdapter.notifyDataSetChanged();
        itemRecyclerView.setLayoutManager(mLayoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesInvoiceLines(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(itemRecyclerView);
    }

    @OnClick(R.id.soinv_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog(CreateSalesInvoice.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                soinvdateCalendar.set(Calendar.YEAR, year);
                soinvdateCalendar.set(Calendar.MONTH, monthOfYear);
                soinvdateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                soinv_date.setText(IFiveEngine.myInstance.getSimpleCalenderDate(soinvdateCalendar));

            }
        }, soinvdateCalendar.get(Calendar.YEAR), soinvdateCalendar.get(Calendar.MONTH), soinvdateCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.invdelivery_date)
    public void indeDate() {
        DatePickerDialog dialog = new DatePickerDialog(CreateSalesInvoice.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                invdeldateCalendar.set(Calendar.YEAR, year);
                invdeldateCalendar.set(Calendar.MONTH, monthOfYear);
                invdeldateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                invdelivery_date.setText(IFiveEngine.myInstance.getSimpleCalenderDate(invdeldateCalendar));

            }
        }, invdeldateCalendar.get(Calendar.YEAR), invdeldateCalendar.get(Calendar.MONTH), invdeldateCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnItemSelected(R.id.invfreight_pay)
    public void customerspinner(Spinner spinner, int position) {
        invfreight = spinner.getItemAtPosition(position).toString();
        if (invfreight.toString().equals("PAY")) {

            invfreightpay = "PAY";
            loadcost();
        }
        if (invfreight.toString().equals("TO PAY")) {

            invfreightpay = "TO PAY";
            loadcosts();
        }


    }

    private void loadcosts() {
        loadcost.setVisibility(View.GONE);
        freightcost.setText("0");
    }

    private void loadcost() {
        loadcost.setVisibility(View.VISIBLE);
    }

    private void loadSpinnerCustomer() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.freight, R.layout.spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        invfreight_pay.setAdapter(adapter);
    }


    public void setAmount(int mPosition, double quantity) {
        salesItemLinesall.get(mPosition).setInvoiceQty (String.valueOf(quantity));



    }

    public void grandTotal(List<SalesItemLineList> items) {


        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInvoiceAmt () == null) {
                val = 0;
                totalPrice += val;
            } else {
                val = Double.parseDouble(items.get(i).getInvoiceTotal ());
                totalPrice += val;
            }


        }
        if(freightcost.getText ().toString ().equals ("")){
            tax_total=(tax_value/100)*totalPrice;
            grosspay=totalPrice+tax_total;
            total_price.setText(totalPrice + "");
            total_tax.setText(tax_total + "");
            gross_amount.setText (grosspay+"");
        }else{
            freight_cost= Double.parseDouble (freightcost.getText ().toString ());
            totalPrice+=freight_cost;
            tax_total=(tax_value/100)*totalPrice;
            grosspay=totalPrice+tax_total;
            total_price.setText(totalPrice + "");
            total_tax.setText(tax_total + "");
            gross_amount.setText (grosspay+"");
        }

    }

    @OnClick(R.id.submit_data)
    public void submitdata() {
        if (total_price.getText().toString().equals("")) {
            total_price.setError("Required");
        } else if (soinv_date.getText().toString().equals("--Select Date--")) {
            soinv_date.setError("Required");
        } else if (invdelivery_date.getText().toString().equals("-- Select Date --")) {
            invdelivery_date.setError("Required");
        } else if (invfreight_pay.getSelectedItem().toString().equals("--Select--")) {
            Toast.makeText(this, "Select Freight Pay", Toast.LENGTH_SHORT).show();
        } else if (delivery_address.getText().toString().equals("")) {
            delivery_address.setError("Required");
        } else {
            headerSave();
        }

    }

    public void headerSave() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("SalesOrderid", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });

        Number currentIdNum = realm.where(InvoiceItemList.class).max("invoiceid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        invoiceItemList = new InvoiceItemList();
        invoiceItemList.setInvoiceid(nextId);
        invoiceItemList.setSo_number (hdrid);
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString().trim());
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightcost(freightcost.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setTypeOfOrder(salesItemLists.get(0).getTypeOfOrder());
        invoiceItemList.setGrossTotal (gross_amount.getText ().toString ().trim ());
        invoiceItemList.setOnlineStatus ("0");
        invoiceItemList.setStatus("Submitted");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setTaxAmt (total_tax.getText().toString());
        invoiceItemList.setTaxType (tax.getText().toString());
        invoiceItemList.setTaxValue (String.valueOf (tax_value));

        uploadLocalPurchase(invoiceItemList, nextId);
    }

    private void uploadLocalPurchase(final InvoiceItemList invoiceItemList, final int nextid) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        InvoiceItemList allSalesOrder = realm.copyToRealm(invoiceItemList);
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
        for (int i = 0; i < salesItemLinesall.size(); i++) {
            InvoiceItemLinelist invoiceItemLinelist = realm.createObject(InvoiceItemLinelist.class);
            invoiceItemLinelist.setProduct(salesItemLinesall.get(i).getProduct());
            invoiceItemLinelist.setProductId (salesItemLinesall.get(i).getProductId ());
            invoiceItemLinelist.setUomID (salesItemLinesall.get(i).getUomId());
            invoiceItemLinelist.setOrdqty(String.valueOf(salesItemLinesall.get(i).getQuantity()));
            invoiceItemLinelist.setInvqty(salesItemLinesall.get(i).getInvoiceQty ());
            invoiceItemLinelist.setInvoiceDisPer (salesItemLinesall.get(i).getInvoiceDisPer ());
            invoiceItemLinelist.setInvoiceDis (salesItemLinesall.get(i).getInvoiceDis ());
            invoiceItemLinelist.setInvoiceAmt (salesItemLinesall.get(i).getInvoiceAmt ());
            invoiceItemLinelist.setInvoiceTotal (salesItemLinesall.get(i).getInvoiceTotal ());
            invoiceItemLinelist.setUnitSellingPrice (salesItemLinesall.get(i).getUnitPrice ());
            InvoiceItemList salesItemList = realm.where(InvoiceItemList.class).equalTo("invoiceid", nextid).findFirst();
            salesItemList.getInvoiceItemLinelists().add(invoiceItemLinelist);
        }

        realm.commitTransaction();
        Intent intent = new Intent(CreateSalesInvoice.this, Dashboard.class);
        startActivity(intent);
    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        if (total_price.getText().toString().equals("")) {
            total_price.setError("Required");
        } else if (soinv_date.getText().toString().equals("--Select Date--")) {
            soinv_date.setError("Required");
        } else if (invdelivery_date.getText().toString().equals("-- Select Date --")) {
            invdelivery_date.setError("Required");
        } else if (invfreight_pay.getSelectedItem().toString().equals("--Select--")) {
            Toast.makeText(this, "Select Freight Pay", Toast.LENGTH_SHORT).show();
        } else if (delivery_address.getText().toString().equals("")) {
            delivery_address.setError("Required");
        } else {
            headerdraftSave();
        }

    }

    private void headerdraftSave() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("SalesOrderid", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });

        Number currentIdNum = realm.where(InvoiceItemList.class).max("invoiceid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        invoiceItemList.setInvoiceid(nextId);
        invoiceItemList.setSo_number (hdrid);
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString().trim());
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightcost(freightcost.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setTypeOfOrder(salesItemLists.get(0).getTypeOfOrder());
        invoiceItemList.setGrossTotal (gross_amount.getText ().toString ().trim ());
        invoiceItemList.setOnlineStatus ("0");
        invoiceItemList.setStatus("Opened");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setTaxAmt (total_tax.getText().toString());
        invoiceItemList.setTaxType (tax.getText().toString());
        invoiceItemList.setTaxValue (String.valueOf (tax_value));

        uploadLocalPurchase(invoiceItemList, nextId);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SalesInvoiceLineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = salesItemLinesall.get(viewHolder.getAdapterPosition()).getProduct();
            // backup of removed item for undo purpose
            final SalesItemLineList deletedItem = salesItemLinesall.get(viewHolder.getAdapterPosition());
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
        String Name = customerLists.getCustomerLists ().get (position).getCusName ();
        int id = customerLists.getCustomerLists ().get (position).getCusNo ();
        invcustomer_Name.setText (Name);
        invcustomer_Name.setError (null);
        cus_id = id;
        shipping_addresses = realm.where(Shipping_address.class).equalTo("customerId", cus_id).findFirst();
        delivery_address.setText (shipping_addresses.getAddressLaneOne ()+","+shipping_addresses.getAddressLaneTwo ()+",\n"+
                shipping_addresses.getShipState ()+",\n"+shipping_addresses.getShipCity ()+","+shipping_addresses.getShipPincode ());

        chartAlertDialog.dismiss ();
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(CreateSalesInvoice.this, Dashboard.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    public void setDiscount(int mPosition, double discountper, double total, double disamt, double total_amount) {
        salesItemLinesall.get(mPosition).setInvoiceDisPer (String.valueOf(discountper));
        salesItemLinesall.get(mPosition).setInvoiceAmt (String.valueOf(total));
        salesItemLinesall.get(mPosition).setInvoiceDis (String.valueOf(disamt));
        salesItemLinesall.get(mPosition).setInvoiceTotal (String.valueOf(total_amount));
        grandTotal(salesItemLinesall);
    }

    @Override
    public void onItemtaxPostion(int position) {
        String Name = allDataList.getTax_types ().get (position).getTaxType ();
        int id = allDataList.getTax_types ().get (position).getTaxTypeId ();
        tax_value= Double.parseDouble (allDataList.getTax_types ().get (position).getTaxValue ());
        tax.setText (Name);
        tax.setError (null);
        tax_id = id;
        chartAlertDialog.dismiss ();

    }
}
