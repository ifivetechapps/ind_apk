package com.ifive.indsmart.UI.Sales.SalesCreate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.ifive.indsmart.UI.DashBoard.Dashboard;
import com.ifive.indsmart.UI.Masters.Model.CustomerList;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoHeaderquoteDetail;
import com.ifive.indsmart.UI.Sales.OnlineModel.SoQuoteDetail;
import com.ifive.indsmart.UI.Sales.SalesCreate.Adapter.QuoteToSalesLineAdapter;
import com.ifive.indsmart.UI.Sales.SalesCreate.Model.SaleItemList;
import com.ifive.indsmart.UI.Sales.SalesCreate.Model.SalesItemLineList;
import com.ifive.indsmart.UI.Sales.SalesQuote.Adapter.EnquiryToQuoteLineAdapter;
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

public class ConvertFromQuoteToSalesActivity extends BaseActivity implements RecyclerItemTouchHelperConvertEnquiryToQuote.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner {
    int hdrid;
    QuoteToSalesLineAdapter salesAdapter;
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
    @BindView(R.id.type_of_order)
    TextView typeOfOrder;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String onlineSQ;
    ActionBar actionBar;
    RealmList<SoQuoteDetail> quoteItemLineLists = new RealmList<> ();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;


    int nextId, cus_id = 0, tax_id;
    SaleItemList saleItemList;
    RealmResults<SoHeaderquoteDetail> quoteItemLists;
    private Menu menu;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    SessionManager sessionManager;
    double tax_value = 0.0;
    List<AllDataList> allDataLists = new ArrayList<AllDataList> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sales_order);
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
        pDialog = IFiveEngine.getProgDialog (this);
        sessionManager = new SessionManager ();
        sodateCalendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance ();
        allDataLists.addAll (realm.where (AllDataList.class).findAll ());
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
        quoteItemLists = realm.where (SoHeaderquoteDetail.class)
                .equalTo ("salesQuoteId", hdrid)
                .findAll ();
        delivery_date.setText (quoteItemLists.get (0).getDeliveryDate ());
        so_date.setText (quoteItemLists.get (0).getSalesQuoteDate ());
        so_status.setText (quoteItemLists.get (0).getApproveStatus ());

        CustomerList customer=realm.where (CustomerList.class).equalTo ("cusNo",quoteItemLists.get (0).getCustomerId ()).findFirst ();

        customer_Name.setText (customer.getCusName ());

        gross_amount.setText (quoteItemLists.get (0).getGrossAmount ()+"");
        if(quoteItemLists.get (0).getTypeId ()==1){
            typeOfOrder.setText ("Standard");
        }else{
            typeOfOrder.setText ("Labour");
        }

        cus_id =quoteItemLists.get (0).getCustomerId ();
        onlineSQ = quoteItemLists.get (0).getSalesQuoteNo ();
        quoteItemLineLists.addAll (realm.copyFromRealm (realm.where (SoQuoteDetail.class)
                .equalTo ("salesQuoteId", hdrid)
                .findAll ()));

        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        salesAdapter = new QuoteToSalesLineAdapter (this, quoteItemLineLists, products, this);
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
        int position = quoteItemLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (quoteItemLineLists.get (position - 1).getTotalCost ().equals ("")) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }


    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = quoteItemLineLists.size ();

        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (quoteItemLineLists.get (position - 1).getTotalCost ().equals ("")) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }


    }

    private void headerdraftSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                SoQuoteDetail enquiryItemModel = realm.where (SoQuoteDetail.class).equalTo ("salesQuoteId", hdrid).findFirst ();
                enquiryItemModel.setStatus ("converted");
            }
        });

        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        saleItemList = new SaleItemList ();
        saleItemList.setSalesOrderid (nextId);
        saleItemList.setSqId (hdrid);
        saleItemList.setOnlinesqId (onlineSQ);
        saleItemList.setSodate (so_date.getText ().toString ());
        saleItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        saleItemList.setCus_id (cus_id);
        saleItemList.setDel_date (delivery_date.getText ().toString ());
        saleItemList.setStatus ("Opened");
        saleItemList.setOnlinestatus ("0");
        saleItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());
        saleItemList.setNetPrice (gross_amount.getText().toString());
        uploadLocalPurchase (saleItemList, nextId);
    }


    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (ConvertFromQuoteToSalesActivity.this, new DatePickerDialog.OnDateSetListener () {

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
            insertItem (position);
        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        quoteItemLineLists.add (new SoQuoteDetail ());
        salesAdapter.notifyItemInserted (position);
    }


    public void setProductList(int pos, int pro_id, String name, int uomId, String uomName) {
        realm.beginTransaction ();

        quoteItemLineLists.get (pos).setProductName (pro_id);
        quoteItemLineLists.get (pos).setUomId (uomId);

        realm.commitTransaction ();
    }

    public void setQuantity(int position, int quant) {
        quoteItemLineLists.get (position).setOrderQty (quant);
    }

    public void grandTotal(List<SoQuoteDetail> items) {
  double grosspay=0.0;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getTotalCost ()!=null){
                grosspay += Double.parseDouble (String.valueOf (items.get(i).getTotalCost ()));
            }
        }


        gross_amount.setText (grosspay + "");
    }


    public void headerSave() {
        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                SoQuoteDetail enquiryItemModel = realm.where (SoQuoteDetail.class).equalTo ("salesQuoteId", hdrid).findFirst ();
                enquiryItemModel.setStatus ("converted");
            }
        });
        Number currentIdNum = realm.where (SaleItemList.class).max ("SalesOrderid");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        saleItemList = new SaleItemList ();
        saleItemList.setSalesOrderid (nextId);
        saleItemList.setSqId (hdrid);
        saleItemList.setOnlinesqId (onlineSQ);
        saleItemList.setSodate (so_date.getText ().toString ());
        saleItemList.setCus_name (customer_Name.getText ().toString ().trim ());
        saleItemList.setCus_id (cus_id);
        saleItemList.setDel_date (delivery_date.getText ().toString ());
        saleItemList.setStatus ("Submitted");
        saleItemList.setOnlinestatus ("0");
        saleItemList.setTypeOfOrder (typeOfOrder.getText ().toString ());
        saleItemList.setTaxTypeID (String.valueOf (tax_id));
        saleItemList.setTaxValue (String.valueOf (tax_value));
        saleItemList.setNetPrice (gross_amount.getText().toString());
        uploadLocalPurchase (saleItemList, nextId);
    }

    private void uploadLocalPurchase(final SaleItemList salesItemList, final int nextid) {
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
                        uploadLine (nextid);
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

    private void uploadLine(final int nextid) {

        realm.executeTransaction (new Realm.Transaction () {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < quoteItemLineLists.size (); i++) {
                    int nextId_line;
                    Number currentIdNum = realm.where (SalesItemLineList.class).max ("saleId");                    if (currentIdNum == null) {
                        nextId_line = 1;
                    } else {
                        nextId_line = currentIdNum.intValue () + 1;
                    }
                    SalesItemLineList salesItemLineList=new SalesItemLineList ();
                    salesItemLineList.setSaleId (nextId_line);
                    salesItemLineList.setSalesHdrid (nextid);
                    salesItemLineList.setProductId (String.valueOf (quoteItemLineLists.get (i).getProductName ()));
                    salesItemLineList.setTaxAmt (quoteItemLineLists.get (i).getTaxAmt ());
                    salesItemLineList.setUomId (quoteItemLineLists.get (i).getUomId ());
                    salesItemLineList.setTaxId (quoteItemLineLists.get (i).getTax ());
                    salesItemLineList.setUnitPrice (String.valueOf (quoteItemLineLists.get (i).getUnitSellingPrice ()));
                    salesItemLineList.setQuantity (Integer.valueOf (quoteItemLineLists.get (i).getOrderQty ()));
                    salesItemLineList.setDisPer (String.valueOf (quoteItemLineLists.get (i).getDiscountPer ()));
                    salesItemLineList.setDisAmt (String.valueOf (quoteItemLineLists.get (i).getDiscountAmount ()));
                    salesItemLineList.setOrgCost (String.valueOf (quoteItemLineLists.get (i).getTotalCost ()));
                    salesItemLineList.setLineTotal (String.valueOf (quoteItemLineLists.get (i).getTotalCost ()));

                    realm.insert (salesItemLineList);

                }
            }
        });

        Intent intent = new Intent (ConvertFromQuoteToSalesActivity.this, Dashboard.class);
        startActivity (intent);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EnquiryToQuoteLineAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
     //       String name = quoteItemLineLists.get (viewHolder.getAdapterPosition ()).getProduct ();
            // backup of removed item for undo purpose
            Products products=realm.where (Products.class).equalTo ("pro_id", quoteItemLineLists.get (viewHolder.getAdapterPosition ()).getProductName ()).findFirst ();

            String name =products.getProduct_name ();
            final int deletedIndex = viewHolder.getAdapterPosition ();
            // remove the item from recycler view
            salesAdapter.removeItem (viewHolder.getAdapterPosition ());
            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make (((Activity) this).findViewById (android.R.id.content),
//                            name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction ("UNDO", new View.OnClickListener () {
//                @Override
//                public void onClick(View view) {
//                    // undo is selected, restore the deleted item
//                    salesAdapter.restoreItem (deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor (Color.YELLOW);
//            snackbar.show ();
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent (ConvertFromQuoteToSalesActivity.this, SubDashboard.class);
        it.putExtra ("type", "Sales");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
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

    public void setUnitPrice(int mPosition, int uni) {
        quoteItemLineLists.get (mPosition).setUnitSellingPrice (uni);

    }

    public void setLineTotal(int mPosition, double disper, double unit_quan, double disamt, double total_amount) {
        quoteItemLineLists.get (mPosition).setDiscountPer ((int) disper);
        //quoteItemLineLists.get (mPosition).setTotalCost (String.valueOf (unit_quan));
        quoteItemLineLists.get (mPosition).setDiscountAmount ((float) disamt);
        quoteItemLineLists.get (mPosition).setTotalCost ((int) total_amount);
        grandTotal (quoteItemLineLists);

    }

}

