package com.ifive.indsmart.UI.Sales.SalesEnquiry;

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

import com.ifive.indsmart.CommanAdapter.CustomerListAdapter;
import com.ifive.indsmart.Connectivity.AllDataList;
import com.ifive.indsmart.Connectivity.Products;
import com.ifive.indsmart.Connectivity.SessionManager;
import com.ifive.indsmart.Engine.IFiveEngine;
import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.DashBoard.Dashboard;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Adapter.EnquiryCopyAdapter;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Adapter.EnquiryEditAdapter;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryItemModel;
import com.ifive.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryLineList;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;
import com.ifive.indsmart.Utils.RecyclerItemTouchHelperSalesEnquiryEdit;

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

public class SalesEnquiryCopyActivity extends BaseActivity implements RecyclerItemTouchHelperSalesEnquiryEdit.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner {
    EnquiryCopyAdapter enquiryEditAdapter;
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.remarks)
    EditText remarks;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.enquiry_source)
    TextView enquiry_source;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.draft_data)
    Button draft_data;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ActionBar actionBar;
    RealmList<EnquiryLineList> enquiryLineLists = new RealmList<> ();
    Calendar sodateCalendar, deldateCalendar;
    Realm realm;
    int hdrid, cus_id = 0, nextId;
    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    EnquiryItemModel enquiryItemModel;
    private Menu menu;
    String enquiry_date, typeof;
    ProgressDialog pDialog;
    List<AllDataList> allDataLists=new ArrayList<> ();
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.create_sales_enquiry);
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
        allDataLists.addAll (realm.where(AllDataList.class).findAll ());

            loadData ();
            submit_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    copyheaderSave ();
                }
            });
            draft_data.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    copyDraftSave ();
                }
            });

        sodateCalendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance ();
        enquiry_date = IFiveEngine.myInstance.getSimpleCalenderDate (sodateCalendar);

    }

    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog (SalesEnquiryCopyActivity.this, new DatePickerDialog.OnDateSetListener () {

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
        RealmResults<EnquiryItemModel> enquiryItemModels;
        enquiryItemModels = realm.where (EnquiryItemModel.class)
                .equalTo ("enquiryId", hdrid)
                .findAll ();

        customer_Name.setText (enquiryItemModels.get (0).getEnquiryCustomerName ());
        remarks.setText (enquiryItemModels.get (0).getEnquiryRemarks ());
        enquiry_source.setText (enquiryItemModels.get (0).getEnquiryType ());
        so_status.setText (enquiryItemModels.get (0).getEnquiryStatus ());
        delivery_date.setText (enquiryItemModels.get (0).getDeliveryDate ());
        cus_id = enquiryItemModels.get (0).getEnquiryCustomerId ();

        enquiryLineLists.addAll (realm.copyFromRealm (realm.where (EnquiryLineList.class)
                .equalTo ("enquiryHdrId", hdrid)
                .findAll ()));
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        enquiryEditAdapter = new EnquiryCopyAdapter (this, enquiryLineLists, products, this);
        itemRecyclerView.setAdapter (enquiryEditAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        enquiryEditAdapter.notifyDataSetChanged ();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesEnquiryEdit (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);
    }


    private void copyheaderSave() {
        Number currentIdNum = realm.where (EnquiryItemModel.class).max ("enquiryId");

        if (currentIdNum == null) {
            nextId = 1;
            Log.d("jhgwjhg", String.valueOf (nextId));
        } else {
            nextId = currentIdNum.intValue () + 1;
            Log.d("jhgwjhg", String.valueOf (nextId));
        }
        enquiryItemModel = new EnquiryItemModel ();
        enquiryItemModel.setEnquiryId (nextId);
        enquiryItemModel.setEnquiryCustomerId (cus_id);
        enquiryItemModel.setEnquiryDate (enquiry_date);
        enquiryItemModel.setDeliveryDate (delivery_date.getText ().toString ());
        enquiryItemModel.setEnquiryType (enquiry_source.getText ().toString ());
        enquiryItemModel.setEnquiryCustomerName (customer_Name.getText ().toString ().trim ());
        enquiryItemModel.setEnquiryRemarks (remarks.getText ().toString ());
        enquiryItemModel.setEnquiryStatus ("Submitted");
        enquiryItemModel.setStautsOnline ("0");
        CopyuploadLocalPurchase (enquiryItemModel,nextId);

    }


    private void copyDraftSave() {
        Number currentIdNum = realm.where (EnquiryItemModel.class).max ("enquiryId");
        if (currentIdNum == null) {
            nextId = 1;
            Log.d("jhgwjhg123", String.valueOf (nextId));
        } else {
            nextId = currentIdNum.intValue () + 1;
            Log.d("jhgwjhg", String.valueOf (nextId));

        }
        enquiryItemModel = new EnquiryItemModel ();
        enquiryItemModel.setEnquiryId (nextId);
        enquiryItemModel.setEnquiryCustomerId (cus_id);
        enquiryItemModel.setEnquiryDate (enquiry_date);
        enquiryItemModel.setDeliveryDate (delivery_date.getText ().toString ());
        enquiryItemModel.setEnquiryType (enquiry_source.getText ().toString ());
        enquiryItemModel.setEnquiryCustomerName (customer_Name.getText ().toString ().trim ());
        enquiryItemModel.setEnquiryRemarks (remarks.getText ().toString ());
        enquiryItemModel.setEnquiryStatus ("Opened");

        CopyuploadLocalPurchase (enquiryItemModel,nextId);

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
            Log.d ("postion value", String.valueOf (position));
            insertItem (position);
        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(final int position) {
        enquiryLineLists.add (new EnquiryLineList ());
        enquiryEditAdapter.notifyItemInserted (position);

    }


    public void setProductList(int pos, int gPosition, int productId, String name, int uomId, String uomName,int tax_id) {
        enquiryLineLists.get (pos).setEnquiryProductPosition (gPosition);
        enquiryLineLists.get (pos).setEnquiryProductId (String.valueOf (productId));
        enquiryLineLists.get (pos).setEnquiryProduct (name);
        enquiryLineLists.get (pos).setEnquiryUom (uomName);
        enquiryLineLists.get (pos).setEnquiryUomId (uomId);
        enquiryLineLists.get (pos).setTaxId (tax_id);
    }

    public void setQuantity(int position, String quant) {

        enquiryLineLists.get (position).setEnquiryRequiredQuantity (quant);

    }


    private void CopyuploadLocalPurchase(final EnquiryItemModel enquiryItemModel,final int nextId) {
        Log.d("jhg434wjhg", String.valueOf (nextId));

        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        EnquiryItemModel allSalesOrder = realm.copyToRealm (enquiryItemModel);
                        realm.commitTransaction ();
                        lineSave (nextId);
//                        Intent intent = new Intent (SalesEnquiryCopyActivity.this, Dashboard.class);
//                        startActivity (intent);
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
    public void lineSave(final int nextId){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int nextId_line ;
                for (int j = 0; j < enquiryLineLists.size (); j++) {

                    Number currentIdNum = realm.where (EnquiryLineList.class).max ("enqLineId");
                    Log.d("jhgwjhlineg", String.valueOf (currentIdNum));
                    if (currentIdNum == null) {
                        nextId_line = 1;
                        Log.d("jhgwjhline34g", String.valueOf (currentIdNum));
                    } else {
                        nextId_line = currentIdNum.intValue () + 1;
                        Log.d("jhgwjhlineg", String.valueOf (currentIdNum));
                    }
                    EnquiryLineList enquiryLineList=new EnquiryLineList ();
                    enquiryLineList.setEnqLineId (nextId_line);
                    enquiryLineList.setEnquiryHdrId (nextId);
                    enquiryLineList.setEnquiryProductId (enquiryLineLists.get (j).getEnquiryProductId ());
                    enquiryLineList.setEnquiryProductPosition (enquiryLineLists.get (j).getEnquiryProductPosition ());
                    enquiryLineList.setEnquiryProduct (enquiryLineLists.get (j).getEnquiryProduct ());
                    enquiryLineList.setEnquiryUomId (enquiryLineLists.get (j).getEnquiryUomId ());
                    enquiryLineList.setEnquiryUom (enquiryLineLists.get (j).getEnquiryUom ());
                    enquiryLineList.setTaxId (enquiryLineLists.get (j).getTaxId ());
                    enquiryLineList.setEnquiryRequiredQuantity (enquiryLineLists.get (j).getEnquiryRequiredQuantity ());
                    realm.insert (enquiryLineList);
                    Intent intent = new Intent (SalesEnquiryCopyActivity.this, SubDashboard.class);
                    intent.putExtra ("type", "Sales");
                    startActivity (intent);

                }
            }
        });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EnquiryEditAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = enquiryLineLists.get (viewHolder.getAdapterPosition ()).getEnquiryProduct ();
// backup of removed item for undo purpose
            final EnquiryLineList deletedItem = enquiryLineLists.get (viewHolder.getAdapterPosition ());
            final int deletedIndex = viewHolder.getAdapterPosition ();
            // remove the item from recycler view
            enquiryEditAdapter.removeItem (viewHolder.getAdapterPosition ());
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make (((Activity) this).findViewById (android.R.id.content),
                            name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction ("UNDO", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    enquiryEditAdapter.restoreItem (deletedItem, deletedIndex);
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
        Intent it = new Intent (SalesEnquiryCopyActivity.this, Dashboard.class);
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
    }
}