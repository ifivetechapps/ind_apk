package com.ifiveuv.indsmart.UI.SalesEnquiry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.ifiveuv.indsmart.UI.SalesEnquiry.Adapter.EnquiryItemAdapter;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryItemModel;
import com.ifiveuv.indsmart.UI.SalesEnquiry.Model.EnquiryLineList;
import com.ifiveuv.indsmart.UI.SubDashboard.SubDashboard;
import com.ifiveuv.indsmart.Utils.RecyclerItemTouchHelperSalesEnquiryLines;

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

public class SalesEnquiryCreatingActivity extends BaseActivity implements RecyclerItemTouchHelperSalesEnquiryLines.RecyclerItemTouchHelperListener, CustomerListAdapter.onItemClickListner {
    @BindView(R.id.customer_Name)
    TextView customer_Name;
    @BindView(R.id.remarks)
    TextView remarks;
    @BindView(R.id.so_status)
    TextView so_status;
    @BindView(R.id.delivery_date)
    TextView delivery_date;
    @BindView(R.id.enquiry_source)
    TextView enquiry_source;
    @BindView(R.id.items_data)
    RecyclerView itemRecyclerView;
    @BindView(R.id.submit_data)
    Button submit_data;
    @BindView(R.id.draft_data)
    Button draft_data;

    RecyclerView.LayoutManager mLayoutManager;
    EnquiryItemModel enquiryItemModel;
    RealmList<EnquiryLineList> enquiryLineLists = new RealmList<> ();
    RealmList<EnquiryLineList> enquiryLineListRealmList;
    Calendar enquiry_date_calendar,deldateCalendar;
    Realm realm;
    EnquiryItemAdapter enquiryItemAdapter;
    int nextId;

    AlertDialog.Builder chartDialog;
    AlertDialog chartAlertDialog;
    ProgressDialog pDialog;
    int cus_id = 0;
    private Menu menu;
    List<AllDataList> allDataList=new ArrayList<> ();
    SessionManager sessionManager;
    String type_of_enquiry, enquiry_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.create_sales_enquiry);
        ButterKnife.bind (this);

        Realm.init (this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder ()
                .deleteRealmIfMigrationNeeded ()
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
        realm = Realm.getDefaultInstance ();
        sessionManager = new SessionManager ();
        pDialog = IFiveEngine.getProgDialog (this);
        enquiry_date_calendar = Calendar.getInstance ();
        deldateCalendar = Calendar.getInstance();
        Intent intent = getIntent ();
        type_of_enquiry = intent.getStringExtra ("type");
        allDataList.addAll (realm.where (AllDataList.class).findAll ());
        enquiry_source.setText (type_of_enquiry);
        enquiry_date = IFiveEngine.myInstance.getSimpleCalenderDate (enquiry_date_calendar);
        enquiryLineLists.add (new EnquiryLineList ());
        loadItemAdapter ();
    }

    @OnClick(R.id.delivery_date)
    public void salesorderDate() {
        DatePickerDialog dialog = new DatePickerDialog(SalesEnquiryCreatingActivity.this, new DatePickerDialog.OnDateSetListener() {

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


    @OnClick(R.id.submit_data)
    public void submitdata() {
        int position = enquiryLineLists.size ();
        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            Toast.makeText (this, "Enter the delivery date ", Toast.LENGTH_SHORT).show ();
        } else if (enquiryLineLists.get (position - 1).getEnquiryRequiredQuantity () == null) {
            Toast.makeText (this, "Enter the Required Quantity ", Toast.LENGTH_SHORT).show ();
        } else {
            headerSave ();
        }
    }

    @OnClick(R.id.draft_data)
    public void draftdata() {
        int position = enquiryLineLists.size ();
        if (customer_Name.getText ().toString ().equals ("")) {
            customer_Name.setError ("Required");
        } if (delivery_date.getText ().toString ().equals ("")) {
            delivery_date.setError ("Required");
        } else if (delivery_date.getText ().toString ().equals ("")) {
            Toast.makeText (this, "Enter the delivery date ", Toast.LENGTH_SHORT).show ();
        }else if (enquiryLineLists.get (position - 1).getEnquiryRequiredQuantity () == null) {
            Toast.makeText (this, "Enter the above row", Toast.LENGTH_SHORT).show ();
        } else {
            headerdraftSave ();
        }

    }

    private void headerdraftSave() {
        Number currentIdNum = realm.where (EnquiryItemModel.class).max ("enquiryId");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        enquiryItemModel = new EnquiryItemModel ();
        enquiryItemModel.setEnquiryId (nextId);
        enquiryItemModel.setEnquiryType (type_of_enquiry);
        enquiryItemModel.setEnquiryDate (enquiry_date);
        enquiryItemModel.setEnquiryCustomerId (cus_id);
        enquiryItemModel.setDeliveryDate (delivery_date.getText ().toString ());
        enquiryItemModel.setEnquiryCustomerName (customer_Name.getText ().toString ().trim ());
        enquiryItemModel.setEnquiryStatus (so_status.getText ().toString ());
        enquiryItemModel.setEnquiryRemarks (remarks.getText ().toString ());
        enquiryItemModel.setEnquiryLineLists (enquiryLineListRealmList);

        uploadLocalPurchase (enquiryItemModel);
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
            if (enquiryLineLists.get (position - 1).getEnquiryRequiredQuantity () == null) {
                Toast.makeText (this, "Enter the Required Quantity ", Toast.LENGTH_SHORT).show ();
            } else {
                insertItem (position);
            }

        }
        return super.onOptionsItemSelected (item);
    }

    private void insertItem(int position) {
        enquiryLineLists.add (new EnquiryLineList ());
        enquiryItemAdapter.notifyItemInserted (position);
    }


    private void loadItemAdapter() {
        List<Products> products = new ArrayList<Products> ();
        products.addAll (realm.where (Products.class).findAll ());
        enquiryItemAdapter = new EnquiryItemAdapter (this, enquiryLineLists, products, this);
        itemRecyclerView.setAdapter (enquiryItemAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        itemRecyclerView.setLayoutManager (mLayoutManager);
        itemRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        enquiryItemAdapter.notifyDataSetChanged ();
        enquiryLineListRealmList = enquiryLineLists;
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSalesEnquiryLines (0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (itemRecyclerView);
    }

    public void setProductList(int pos, int gPosition, int productId, String name, int uomId, String uomName) {
        enquiryLineLists.get (pos).setEnquiryProductPosition (gPosition);
        enquiryLineLists.get (pos).setEnquiryProductId (String.valueOf (productId));
        enquiryLineLists.get (pos).setEnquiryProduct (name);
        enquiryLineLists.get (pos).setEnquiryUom (uomName);
        enquiryLineLists.get (pos).setEnquiryUomId (uomId);
    }

    public void setQuantity(int position, String quant) {

        enquiryLineLists.get (position).setEnquiryRequiredQuantity (quant);

    }


    public void headerSave() {
        Number currentIdNum = realm.where (EnquiryItemModel.class).max ("enquiryId");
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue () + 1;
        }
        enquiryItemModel = new EnquiryItemModel ();
        enquiryItemModel.setEnquiryId (nextId);
        enquiryItemModel.setEnquiryCustomerId (cus_id);
        enquiryItemModel.setEnquiryDate (enquiry_date);
        enquiryItemModel.setDeliveryDate (delivery_date.getText ().toString ());
        enquiryItemModel.setEnquiryType (type_of_enquiry);
        enquiryItemModel.setEnquiryCustomerName (customer_Name.getText ().toString ().trim ());
        enquiryItemModel.setEnquiryRemarks (remarks.getText ().toString ());
        enquiryItemModel.setEnquiryStatus ("Submitted");
        enquiryItemModel.setStautsOnline ("0");
        enquiryItemModel.setEnquiryLineLists (enquiryLineListRealmList);
        uploadLocalPurchase (enquiryItemModel);
    }

    private void uploadLocalPurchase(final EnquiryItemModel quoteItemLists) {
        realm = Realm.getDefaultInstance ();
        Observable<Integer> observable = Observable.just (1);
        observable
                .subscribeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<Integer> () {
                    @Override
                    public void onCompleted() {
                        realm.beginTransaction ();
                        EnquiryItemModel allSalesOrder = realm.copyToRealm (quoteItemLists);
                        realm.commitTransaction ();
                        Intent intent = new Intent (SalesEnquiryCreatingActivity.this, SubDashboard.class);
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof EnquiryItemAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = enquiryLineLists.get (viewHolder.getAdapterPosition ()).getEnquiryProduct ();
            // backup of removed item for undo purpose
            final EnquiryLineList deletedItem = enquiryLineLists.get (viewHolder.getAdapterPosition ());
            final int deletedIndex = viewHolder.getAdapterPosition ();
            // remove the item from recycler view
            enquiryItemAdapter.removeItem (viewHolder.getAdapterPosition ());
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make (((Activity) this).findViewById (android.R.id.content),
                            name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction ("UNDO", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    enquiryItemAdapter.restoreItem (deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor (Color.YELLOW);
            snackbar.show ();
        }
    }


    @Override
    public void onBackPressed() {
        Intent it = new Intent (SalesEnquiryCreatingActivity.this, SubDashboard.class);
        it.putExtra ("type", "Sales");
        it.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity (it);
        finish ();
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
