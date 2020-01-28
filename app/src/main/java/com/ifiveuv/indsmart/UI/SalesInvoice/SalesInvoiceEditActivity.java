package com.ifiveuv.indsmart.UI.SalesInvoice;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.Engine.IFiveEngine;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.DashBoard.Dashboard;
import com.ifiveuv.indsmart.UI.SalesCreate.Model.SaleItemList;
import com.ifiveuv.indsmart.UI.SalesInvoice.Adapter.SalesInvoiceEditAdapter;
import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemLinelist;
import com.ifiveuv.indsmart.UI.SalesInvoice.Model.InvoiceItemList;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesInvoiceEdit;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

public class SalesInvoiceEditActivity extends BaseActivity implements RecyclerItemTouchHelperSalesInvoiceEdit.RecyclerItemTouchHelperListener {
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
    @BindView(R.id.delivery_address)
    EditText delivery_address;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.add_address)
    ImageView add_address;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    @BindView(R.id.loadcost)
    LinearLayout loadcost;
    @BindView(R.id.freightcost)
    EditText freightcost;
    RecyclerView.LayoutManager mLayoutManager;
    String typeOf, invfreightpay;
    ArrayList<String> customer = new ArrayList<String>();
    ArrayList<String> freights = new ArrayList<String>();
    ActionBar actionBar;
    Object invfreight;
    Calendar soinvdateCalendar, invdeldateCalendar;
    Realm realm;
    int hdrid, nextId;
    RealmList<InvoiceItemLinelist> salesItemLinesall = new RealmList<>();
    SalesInvoiceEditAdapter salesAdapter;
    InvoiceItemList invoiceItemList;
    int cus_id = 0;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_sales_invoice);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        soinvdateCalendar = Calendar.getInstance();
        invdeldateCalendar = Calendar.getInstance();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        hdrid = Integer.parseInt(intent.getStringExtra("hdrid"));
        typeOf =intent.getStringExtra("typeof");

        Log.d("kfv", String.valueOf(hdrid));
        loadData();
        if(typeOf.equals ("edit")){
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    submitdata();
                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    Draftdata();
                }
            });
        }else{
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    CopyheaderSave ();
                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    CopyDraftSave();
                }
            });
        }

    }

    private void loadData() {
        RealmResults<InvoiceItemList> salesItemLists;
        salesItemLists = realm.where(InvoiceItemList.class)
                .equalTo("invoiceid", hdrid)
                .findAll();
        so_id.setText(salesItemLists.get(0).getInvoiceid () + "");
        soinv_date.setText(salesItemLists.get(0).getInvDate ());
        invdelivery_date.setText(salesItemLists.get(0).getDel_date());
        delivery_address.setText(salesItemLists.get(0).getDeliveryaddress());
        total_price.setText(salesItemLists.get(0).getTotalPrice());
        freightcost.setText(salesItemLists.get(0).getFreightcost());
        description.setText(salesItemLists.get(0).getDescription());
        invcustomer_Name.setText(salesItemLists.get(0).getCus_name());
        cus_id = salesItemLists.get(0).getCus_id();
        String freight = salesItemLists.get(0).getFreightpay();

        if (freight.equals("PAY")) {
            freights.add("PAY");
            invfreightpay = "PAY";
            loadcost();

        }
        if (freight.equals("TO PAY")) {
            freights.add("TO PAY");
            invfreightpay = "TO PAY";
            loadcosts();
        }


        ArrayAdapter<String> freightsadapter = new ArrayAdapter<String>(this, R.layout.spinner_style, freights);
        freightsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        invfreight_pay.setAdapter(freightsadapter);
        invfreight_pay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                freights.clear();

                freights.add("PAY");
                freights.add("TO PAY");

                if (position == 0) {
                    invfreightpay = "PAY";
                    loadcost();

                } else if (position == 1) {
                    invfreightpay = "TO PAY";
                    loadcosts();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        salesItemLinesall.addAll(salesItemLists.get(0).getInvoiceItemLinelists());
        List<Products> products = new ArrayList<Products>();
        products.addAll(realm.where(Products.class).findAll());
        salesAdapter = new SalesInvoiceEditAdapter(this, salesItemLinesall, products, this);
        itemRecyclerView.setAdapter(salesAdapter);
        itemRecyclerView.setItemViewCacheSize(salesItemLinesall.size());
        mLayoutManager = new LinearLayoutManager(this);
        salesAdapter.notifyDataSetChanged();
        itemRecyclerView.setLayoutManager(mLayoutManager);
        itemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesInvoiceEdit (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(itemRecyclerView);
    }

    private void loadcosts() {
        loadcost.setVisibility(View.GONE);
        freightcost.setText("0");
    }

    private void loadcost() {
        loadcost.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.soinv_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog(SalesInvoiceEditActivity.this, new DatePickerDialog.OnDateSetListener() {

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
        DatePickerDialog dialog = new DatePickerDialog(SalesInvoiceEditActivity.this, new DatePickerDialog.OnDateSetListener() {

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

    @Override
    public void onBackPressed() {
        Intent it = new Intent(SalesInvoiceEditActivity.this, Dashboard.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    public void setAmount(int mPosition, double quantity) {
        salesItemLinesall.get(mPosition).setInvqty (String.valueOf(quantity));



    }

    public void grandTotal(List<InvoiceItemLinelist> items) {

        double totalPrice = 0;
        double val;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInvoiceTotal () == null) {
                val = 0;
                totalPrice += val;
            } else {
                val = Double.parseDouble(items.get(i).getInvoiceTotal ());
                totalPrice += val;
            }


        }

        total_price.setText(totalPrice + "");
    }

    public void submitdata() {
        if (soinv_date.getText().toString().equals("")) {
            soinv_date.setError("Required");
        } else if (invcustomer_Name.getText().toString().equals("")) {
            Toast.makeText(this, "Customer name required", Toast.LENGTH_SHORT).show();
        } else if (invdelivery_date.getText().toString().equals("")) {
            invdelivery_date.setError("Required");
        } else if (invfreight_pay.getSelectedItem().equals("")) {
            Toast.makeText(this, "Freight Pay required", Toast.LENGTH_SHORT).show();
        } else if (delivery_address.getText().toString().equals("")) {
            delivery_address.setError("required");
        } else {
            headerSave();
        }

    }

    public void Draftdata() {
        if (soinv_date.getText().toString().equals("")) {
            soinv_date.setError("Required");
        } else if (invcustomer_Name.getText().toString().equals("")) {
            Toast.makeText(this, "Customer name required", Toast.LENGTH_SHORT).show();
        } else if (invdelivery_date.getText().toString().equals("")) {
            invdelivery_date.setError("Required");
        } else if (invfreight_pay.getSelectedItem().equals("")) {
            Toast.makeText(this, "Freight Pay required", Toast.LENGTH_SHORT).show();
        } else if (delivery_address.getText().toString().equals("")) {
            delivery_address.setError("required");
        } else {
            draftSave();
        }

    }

    public void headerSave() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("id", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });


        invoiceItemList = new InvoiceItemList();
        invoiceItemList.setInvoiceid(hdrid);
        invoiceItemList.setSo_number (Integer.parseInt(so_id.getText().toString()));
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setFreightcost(freightcost.getText().toString().trim());
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setStatus("Submitted");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setInvoiceItemLinelists(salesItemLinesall);

        uploadLocalPurchase(invoiceItemList);
    }
    public void CopyheaderSave() {
        Number currentIdNum = realm.where (InvoiceItemList.class).max ("invoiceid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("id", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });


        invoiceItemList = new InvoiceItemList();
        invoiceItemList.setInvoiceid(nextId);
        invoiceItemList.setSo_number (Integer.parseInt(so_id.getText().toString()));
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setFreightcost(freightcost.getText().toString().trim());
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setStatus("Submitted");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setInvoiceItemLinelists(salesItemLinesall);

        uploadLocalCopyPurchase(invoiceItemList);
    }   public void CopyDraftSave() {
        Number currentIdNum = realm.where (InvoiceItemList.class).max ("invoiceid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("id", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });


        invoiceItemList = new InvoiceItemList();
        invoiceItemList.setInvoiceid(nextId);
        invoiceItemList.setSo_number (Integer.parseInt(so_id.getText().toString()));
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setFreightcost(freightcost.getText().toString().trim());
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setStatus("Opened");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setInvoiceItemLinelists(salesItemLinesall);

        uploadLocalCopyPurchase(invoiceItemList);
    }
    public void draftSave() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SaleItemList salesItemList = realm.where(SaleItemList.class).equalTo("id", hdrid).findFirst();
                salesItemList.setStatusvalue("converted");
            }
        });


        invoiceItemList = new InvoiceItemList();
        invoiceItemList.setInvoiceid(hdrid);
        invoiceItemList.setSo_number (Integer.parseInt(so_id.getText().toString()));
        invoiceItemList.setInvDate (soinv_date.getText().toString());
        invoiceItemList.setCus_name(invcustomer_Name.getText().toString());
        invoiceItemList.setCus_id(cus_id);
        invoiceItemList.setDel_date(invdelivery_date.getText().toString());
        invoiceItemList.setFreightpay(invfreightpay);
        invoiceItemList.setFreightcost(freightcost.getText().toString().trim());
        invoiceItemList.setDeliveryaddress(delivery_address.getText().toString());
        invoiceItemList.setDescription(description.getText().toString());
        invoiceItemList.setStatus("Opened");
        invoiceItemList.setTotalPrice(total_price.getText().toString());
        invoiceItemList.setInvoiceItemLinelists(salesItemLinesall);

        uploadLocalPurchase(invoiceItemList);
    }

    private void uploadLocalPurchase(final InvoiceItemList invoiceItemList) {
        realm = Realm.getDefaultInstance();
        Observable<Integer> observable = Observable.just(1);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction();
                        InvoiceItemList allSalesOrder = realm.copyToRealmOrUpdate (invoiceItemList);
                        realm.commitTransaction();
                        Intent intent = new Intent(SalesInvoiceEditActivity.this, Dashboard.class);
                        startActivity(intent);

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
    private void uploadLocalCopyPurchase(final InvoiceItemList invoiceItemList) {
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
                        Intent intent = new Intent(SalesInvoiceEditActivity.this, Dashboard.class);
                        startActivity(intent);

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



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SalesInvoiceEditAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = salesItemLinesall.get(viewHolder.getAdapterPosition()).getProduct();
            // backup of removed item for undo purpose
            final InvoiceItemLinelist deletedItem = salesItemLinesall.get(viewHolder.getAdapterPosition());
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

    public void setDiscount(int mPosition, double discountper, double total, double disamt, double total_amount) {
        salesItemLinesall.get(mPosition).setInvoiceDisPer (String.valueOf(discountper));
        salesItemLinesall.get(mPosition).setInvoiceAmt (String.valueOf(total));
        salesItemLinesall.get(mPosition).setInvoiceDis (String.valueOf(disamt));
        salesItemLinesall.get(mPosition).setInvoiceTotal (String.valueOf(total_amount));
        grandTotal(salesItemLinesall);
    }
}
