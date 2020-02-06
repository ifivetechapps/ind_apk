package com.ifiveuv.indsmart.UI.Sales.SalesQuote;

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
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter.QuoteItemAdapter;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesQuoteLines;

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

public class CreateSalesQuote extends BaseActivity implements RecyclerItemTouchHelperSalesQuoteLines.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner {
    private Menu menu;
    QuoteItemAdapter salesAdapter;
    @BindView(R.id.so_date)
    TextView so_date;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.gross_amount)
    TextView gross_amount;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    int cusId;
    String typeQuote;

    ActionBar actionBar;
    RealmList<QuoteItemLineList> quoteItemLineLists = new RealmList<> ();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int nextId;
    AllDataList customerLists;
    QuoteItemList quoteItemLists;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    List<AllDataList> allDataLists = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.create_sales_quote);
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
        allDataLists.addAll(realm.where (AllDataList.class).findAll ());
        typeQuote = intent.getStringExtra ("type");
        so_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (sodateCalendar));
        quoteItemLineLists.add (new QuoteItemLineList ());
        loadItemAdapter ();



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
        int position = quoteItemLineLists.size ();
        if (delivery_date.getText ().toString ().equals ("-- Select Date --")) {
            delivery_date.setError ("Required");
        } else if (quoteItemLineLists.get (position - 1).getLineTotal () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }

    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = quoteItemLineLists.size ();
        if (delivery_date.getText ().toString ().equals ("-- Select Date --")) {
            delivery_date.setError ("Required");
        } else if (quoteItemLineLists.get (position - 1).getLineTotal () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }

    private void headerdraftSave() {
        Number currentIdNum = realm.where (QuoteItemList.class).max ("id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        quoteItemLists = new QuoteItemList ();
        quoteItemLists.setQuoteItemlist (nextId);
        quoteItemLists.setEnquiryId ("0");
        quoteItemLists.setQodate (so_date.getText ().toString ());
        quoteItemLists.setQcus_id (String.valueOf (cusId));
        quoteItemLists.setQuoteType (typeQuote);
        quoteItemLists.setQcus_name (customer_Name.getText ().toString ());
        quoteItemLists.setQdel_date (delivery_date.getText ().toString ());
        quoteItemLists.setOnlineStatus ("0");
        quoteItemLists.setQstatus ("Opened");
        quoteItemLists.setTotalPrice (gross_amount.getText ().toString ());

        uploadLocalPurchase (quoteItemLists,nextId);
    }


    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (CreateSalesQuote.this, new DatePickerDialog.OnDateSetListener () {

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
            int position = quoteItemLineLists.size ();
            if (quoteItemLineLists.get (position - 1).getQuantity () == null) {
                Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        quoteItemLineLists.add (new QuoteItemLineList ());
        salesAdapter.notifyItemInserted (position);
    }


    private void loadItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        salesAdapter = new QuoteItemAdapter (this, quoteItemLineLists, products, this);
        itemRecyclerView.setAdapter (salesAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        salesAdapter.notifyDataSetChanged ();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesQuoteLines (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);
    }

    public void setProductList(int pos, int pro_id, String name, int uomId, String uomName,int taxId,String taxName) {
        realm.beginTransaction ();
        quoteItemLineLists.get (pos).setProduct (name);
        quoteItemLineLists.get (pos).setProductId (String.valueOf (pro_id));
        quoteItemLineLists.get (pos).setUomId (uomId);
        quoteItemLineLists.get (pos).setUom (uomName);
        quoteItemLineLists.get (pos).setQuote_tax (taxName);
        quoteItemLineLists.get (pos).setQuoteTaxId (taxId);
        realm.commitTransaction ();
    }

    public void setQuantity(int position, int quant) {
        quoteItemLineLists.get (position).setQuantity (String.valueOf (quant));
    }

    public void setUnitPrice(int mPosition, double uni) {
        quoteItemLineLists.get (mPosition).setUnitPrice (String.valueOf (uni));
    }

    public void setDiscount(int mPosition, double discountper, double total, double disamt, double total_amount) {
        quoteItemLineLists.get (mPosition).setDisPer (String.valueOf (discountper));
        quoteItemLineLists.get (mPosition).setMrp (String.valueOf (total));
        quoteItemLineLists.get (mPosition).setDisAmt (String.valueOf (disamt));
        quoteItemLineLists.get (mPosition).setLineTotal (String.valueOf (total_amount));
        grandTotal (quoteItemLineLists);
    }

    public void grandTotal(List<QuoteItemLineList> items) {

        double grosspay = 0.0;

        for (int i = 0; i < items.size (); i++) {
            if (items.get (i).getLineTotal () != null) {
                grosspay += Double.parseDouble (items.get (i).getLineTotal ());
            }
        }

        gross_amount.setText (grosspay + "");

    }

    public void headerSave() {
        Number currentIdNum = realm.where (QuoteItemList.class).max ("id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        quoteItemLists = new QuoteItemList ();
        quoteItemLists.setQuoteItemlist (nextId);
        quoteItemLists.setEnquiryId ("0");
        quoteItemLists.setQodate (so_date.getText ().toString ());
        quoteItemLists.setQcus_id (String.valueOf (cusId));
        quoteItemLists.setQuoteType (typeQuote);
        quoteItemLists.setQcus_name (customer_Name.getText ().toString ());
        quoteItemLists.setQdel_date (delivery_date.getText ().toString ());
        quoteItemLists.setOnlineStatus ("0");
        quoteItemLists.setQstatus ("Submitted");
        quoteItemLists.setTotalPrice (gross_amount.getText ().toString ());
        uploadLocalPurchase (quoteItemLists,nextId);
    }

    private void uploadLocalPurchase(final QuoteItemList quoteItemLists, final int nextId) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        QuoteItemList allSalesOrder = realm.copyToRealm (quoteItemLists);
                        realm.commitTransaction ();
                        lineSave(nextId);
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
    public void lineSave(final int hdrid){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int nextId_line ;
                for (int j = 0; j < quoteItemLineLists.size (); j++) {

                    Number currentIdNum = realm.where (QuoteItemLineList.class).max ("quoteLineId");
                    if (currentIdNum == null) {
                        nextId_line = 1;
                    } else {
                        nextId_line = currentIdNum.intValue () + 1;
                    }
                    QuoteItemLineList quoteItemLineList=new QuoteItemLineList ();
                    quoteItemLineList.setQuoteLineId (nextId_line);
                    quoteItemLineList.setQuoteHdrId (hdrid);
                    quoteItemLineList.setProductPosition (quoteItemLineLists.get (j).getProductPosition ());
                    quoteItemLineList.setProduct (quoteItemLineLists.get (j).getProduct ());
                    quoteItemLineList.setProductId (quoteItemLineLists.get (j).getProductId ());
                    quoteItemLineList.setUomId (quoteItemLineLists.get (j).getUomId ());
                    quoteItemLineList.setUnitPrice (quoteItemLineLists.get (j).getUnitPrice ());
                    quoteItemLineList.setUom (quoteItemLineLists.get (j).getUom ());
                    quoteItemLineList.setLineTotal (quoteItemLineLists.get (j).getLineTotal ());
                    quoteItemLineList.setDisPer (quoteItemLineLists.get (j).getDisPer ());
                    quoteItemLineList.setDisAmt (quoteItemLineLists.get (j).getDisAmt ());
                    quoteItemLineList.setMrp (quoteItemLineLists.get (j).getMrp ());
                    quoteItemLineList.setQuantity (quoteItemLineLists.get (j).getQuantity ());
                    realm.insert (quoteItemLineList);
                    Intent intent = new Intent (CreateSalesQuote.this, SubDashboard.class);
                    intent.putExtra ("type", "Sales");
                    startActivity (intent);

                }
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QuoteItemAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = quoteItemLineLists.get (viewHolder.getAdapterPosition ()).getProduct ();
            // backup of removed item for undo purpose
            final QuoteItemLineList deletedItem = quoteItemLineLists.get (viewHolder.getAdapterPosition ());
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
        cusId = id;
        chartAlertDialog.dismiss ();
    }


    @Override
    public void onBackPressed() {
        Intent it = new Intent (CreateSalesQuote.this, SubDashboard.class);
        it.putExtra ("type", "Sales");

        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}
