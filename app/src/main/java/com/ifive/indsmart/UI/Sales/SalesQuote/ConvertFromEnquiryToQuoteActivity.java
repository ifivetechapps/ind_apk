package com.ifive.indsmart.UI.Sales.SalesQuote;

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

import com.ifive.indsmart.CommanAdapter.CustomerListAdapter;
import com.ifive.indsmart.Connectivity.AllDataList;
import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Masters.Model.CustomerList;
import com.ifive.indsmart.UI.Sales.OnlineModel.OnlineEnquiryItemModel;
import com.ifive.indsmart.UI.Sales.OnlineModel.OnlineEnquiryLineList;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifive.indsmart.UI.Sales.SalesQuote.Adapter.EnquiryToQuoteLineAdapter;
import com.ifive.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifive.indsmart.UI.Sales.SalesQuote.Model.QuoteItemList;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;
import com.ifive.indsmart.Utils.RecyclerItemTouchHelperConvertEnquiryToQuote;

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

public class ConvertFromEnquiryToQuoteActivity extends BaseActivity implements RecyclerItemTouchHelperConvertEnquiryToQuote.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner {
    int hdrid;
    EnquiryToQuoteLineAdapter salesAdapter;
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
    @BindView(R.id.gross_amount)
    TextView gross_amount;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String enq_online_id;
    ActionBar actionBar;
    RealmList<OnlineEnquiryLineList> enquiryLineLists = new RealmList<> ();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int nextId, cus_id = 0, tax_id;
    QuoteItemList salesItemLists;

    RealmResults<OnlineEnquiryItemModel> onlineEnquiryItemModels;
    private Menu menu;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    double tax_value = 0.0;

    SessionManager sessionManager;
    List<AllDataList>allDataList=new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_salesquote_order);
        ButterKnife.bind (this);
        actionBar = getSupportActionBar ();
        Intent intent = getIntent ();
        hdrid = Integer.parseInt (intent.getStringExtra ("hdrid"));

        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        sessionManager = new SessionManager ();
        pDialog = IFiveEngine.getProgDialog (this);
        allDataList.addAll (realm.where (AllDataList.class).findAll ());
        sodateCalendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance ();
        so_date.setText (IFiveEngine.myInstance.getSimpleCalenderDate (sodateCalendar));
        loadData ();


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

        final CustomerListAdapter itemShowAdapter = new CustomerListAdapter (this, allDataList.get (0).getCustomerLists (), this);

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
        onlineEnquiryItemModels = realm.where (OnlineEnquiryItemModel.class)
                .equalTo ("salesEnquiryHdrId", hdrid)
                .findAll ();

        delivery_date.setText (onlineEnquiryItemModels.get (0).getDeliveryDate ());
        so_date.setText (onlineEnquiryItemModels.get (0).getSalesEnquiryDate ());
        so_status.setText ("Opened");
         cus_id=onlineEnquiryItemModels.get (0).getCustomerId ();
        CustomerList cusotmer_names=realm.where (CustomerList.class).equalTo ("cusNo",cus_id).findFirst ();
        customer_Name.setText (cusotmer_names.getCusName ());
        int type_id=onlineEnquiryItemModels.get (0).getTypeId ();
        if(type_id==1){
            typeOfOrder.setText ("Standard");
        }else{
            typeOfOrder.setText ("Labour");
        }
        enq_online_id = onlineEnquiryItemModels.get (0).getSalesEnquiryNo ();
        enquiryLineLists.addAll (realm.copyFromRealm (realm.where (OnlineEnquiryLineList.class)
                .equalTo ("salesEnquiryHdrId", hdrid)
                .findAll ()));
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        salesAdapter = new EnquiryToQuoteLineAdapter (this, enquiryLineLists, products, this);
        itemRecyclerView.setAdapter (salesAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        salesAdapter.notifyDataSetChanged ();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperConvertEnquiryToQuote (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);

    }

    @OnClick(R.id.submit_data)
    public void submitdata() {
        int position = enquiryLineLists.size ();
        if (delivery_date.getText ().toString ().equals ("-- Select Date --")) {
            delivery_date.setError ("Required");
        } else if (enquiryLineLists.get (position - 1).getLineTotal () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }

    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = enquiryLineLists.size ();
        if (delivery_date.getText ().toString ().equals ("-- Select Date --")) {
            delivery_date.setError ("Required");
        } else if (enquiryLineLists.get (position - 1).getLineTotal () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }

    private void headerdraftSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                EnquiryItemModel enquiryItemModel = realm.where (EnquiryItemModel.class).equalTo ("enquiryId", hdrid).findFirst ();
                enquiryItemModel.setEnquiryStatus ("converted");
            }
        });
        Number currentIdNum = realm.where (QuoteItemList.class).max ("id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemLists = new QuoteItemList ();
        salesItemLists.setQuoteItemlist (nextId);
        salesItemLists.setOnlineEnquiryId (enq_online_id);
        salesItemLists.setEnquiryId (String.valueOf (hdrid));
        salesItemLists.setQodate (so_date.getText ().toString ());
        salesItemLists.setQcus_name (customer_Name.getText ().toString ().trim ());
        salesItemLists.setQcus_id (String.valueOf (cus_id));
        salesItemLists.setQdel_date (delivery_date.getText ().toString ());
        salesItemLists.setOnlineStatus ("0");
        salesItemLists.setQstatus ("Opened");
        salesItemLists.setQuoteType (typeOfOrder.getText ().toString ());


        salesItemLists.setTotalPrice (gross_amount.getText ().toString ());
        uploadLocalPurchase (salesItemLists, nextId);
    }


    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (ConvertFromEnquiryToQuoteActivity.this, new DatePickerDialog.OnDateSetListener () {

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
            int position = enquiryLineLists.size ();
            insertItem (position);
        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        enquiryLineLists.add (new OnlineEnquiryLineList ());
        salesAdapter.notifyItemInserted (position);
    }


    public void setProductList(int pos, int pro_id, String name, int uomId, String uomName) {

        enquiryLineLists.get (pos).setProductName  (pro_id);
        enquiryLineLists.get (pos).setUomId (uomId);

    }

    public void setQuantity(int position, int quant) {
        enquiryLineLists.get (position).setOrderQty (quant);
    }

    public void grandTotal(List<OnlineEnquiryLineList> items) {

        double grosspay = 0.0;

        for (int i = 0; i < items.size (); i++) {
            if (items.get (i).getLineTotal () != null) {
                grosspay += Double.parseDouble (items.get (i).getLineTotal ());
            }
        }



        gross_amount.setText (grosspay + "");
    }


    public void headerSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                OnlineEnquiryItemModel enquiryItemModel = realm.where (OnlineEnquiryItemModel.class).equalTo ("salesEnquiryHdrId", hdrid).findFirst ();
                enquiryItemModel.setApproveStatus ("converted");
            }
        });

        Number currentIdNum = realm.where (QuoteItemList.class).max ("id");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        salesItemLists = new QuoteItemList ();
        salesItemLists.setQuoteItemlist (nextId);
        salesItemLists.setOnlineEnquiryId (enq_online_id);
        salesItemLists.setEnquiryId (String.valueOf (hdrid));
        salesItemLists.setQodate (so_date.getText ().toString ());
        salesItemLists.setQcus_name (customer_Name.getText ().toString ().trim ());
        salesItemLists.setQcus_id (String.valueOf (cus_id));
        salesItemLists.setQdel_date (delivery_date.getText ().toString ());
        salesItemLists.setQstatus ("Submitted");
        salesItemLists.setOnlineStatus ("0");

        salesItemLists.setQuoteType (typeOfOrder.getText ().toString ());
        salesItemLists.setTotalPrice (gross_amount.getText ().toString ());
        uploadLocalPurchase (salesItemLists, nextId);
    }

    private void uploadLocalPurchase(final QuoteItemList salesItemList, final int nextid) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        QuoteItemList allSalesOrder = realm.copyToRealm (salesItemList);
                        realm.commitTransaction ();
                        lineSave (nextid);
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
                for (int j = 0; j < enquiryLineLists.size (); j++) {

                    Number currentIdNum = realm.where (QuoteItemLineList.class).max ("quoteLineId");
                    if (currentIdNum == null) {
                        nextId_line = 1;
                    } else {
                        nextId_line = currentIdNum.intValue () + 1;
                    }
                    QuoteItemLineList quoteItemLineList=new QuoteItemLineList ();
                    quoteItemLineList.setQuoteLineId (nextId_line);
                    quoteItemLineList.setQuoteHdrId (hdrid);
                    quoteItemLineList.setProductId (enquiryLineLists.get (j).getProductName ());
                    quoteItemLineList.setUomId (enquiryLineLists.get (j).getUomId ());
                    quoteItemLineList.setUnitPrice (enquiryLineLists.get (j).getUnitPrice ());
                    quoteItemLineList.setQuote_taxAmt (enquiryLineLists.get (j).getTaxAmt ());
                    quoteItemLineList.setQuoteTaxId (enquiryLineLists.get (j).getTax ());
                    quoteItemLineList.setLineTotal (enquiryLineLists.get (j).getLineTotal ());
                    quoteItemLineList.setDisPer (enquiryLineLists.get (j).getDiscountPercent ());
                    quoteItemLineList.setDisAmt (enquiryLineLists.get (j).getDiscountAmount ());
                    quoteItemLineList.setMrp (enquiryLineLists.get (j).getOriginalCost ());
                    quoteItemLineList.setQuantity (String.valueOf (enquiryLineLists.get (j).getOrderQty ()));
                    realm.insert (quoteItemLineList);

                }
                Intent intent = new Intent (ConvertFromEnquiryToQuoteActivity.this, SubDashboard.class);
                intent.putExtra ("type", "Sales");
                startActivity (intent);

            }
        });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EnquiryToQuoteLineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            Products products=realm.where (Products.class).equalTo ("pro_id", enquiryLineLists.get (viewHolder.getAdapterPosition ()).getProductName ()).findFirst ();

            String name =products.getProduct_name ();
            // backup of removed item for undo purpose
            final OnlineEnquiryLineList deletedItem = enquiryLineLists.get (viewHolder.getAdapterPosition ());
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
        Intent it = new Intent (ConvertFromEnquiryToQuoteActivity.this, SubDashboard.class);
        it.putExtra ("type", "Sales");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }

    public void setUnitPrice(int mPosition, double uni) {
        enquiryLineLists.get (mPosition).setUnitPrice (String.valueOf (uni));

    }

    public void setDiscount(int mPosition, double discountper, double total, double disamt, double total_amount,int tax_id) {
        enquiryLineLists.get (mPosition).setDiscountPercent (String.valueOf (discountper));
        enquiryLineLists.get (mPosition).setOriginalCost (String.valueOf (total));
        enquiryLineLists.get (mPosition).setDiscountAmount (String.valueOf (disamt));
        enquiryLineLists.get (mPosition).setLineTotal (String.valueOf (total_amount));
        enquiryLineLists.get (mPosition).setTax (tax_id);
        grandTotal (enquiryLineLists);
    }

    @Override
    public void onItemPostion(int position) {
        String Name = allDataList.get (0).getCustomerLists ().get (position).getCusName ();
        int id = allDataList.get (0).getCustomerLists ().get (position).getCusNo ();
        customer_Name.setText (Name);
        customer_Name.setError (null);
        cus_id = id;
        chartAlertDialog.dismiss ();
    }

}

