package com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.EditEnquiryActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.ListEnquiryActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.ViewEnquiryActivity;

import io.realm.Realm;
import io.realm.RealmResults;


public class ListEnquiryAdapter extends RecyclerView.Adapter<ListEnquiryAdapter.MyViewHolder> {
    private Context context;
    private ListEnquiryActivity listEnquiryActivity;
    private RealmResults<PurchaseEnquiryData> cartList;
    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        Button syncdata;
        public TextView enq_num, supp_name, sup_site_name, enq_type, status;

        public MyViewHolder(View view) {
            super (view);
            enq_num = view.findViewById (R.id.enq_num);
            supp_name = view.findViewById (R.id.supp_name);
            sup_site_name = view.findViewById (R.id.sup_site_name);
            enq_type = view.findViewById (R.id.enq_type);
            status = view.findViewById (R.id.status);
            syncdata = view.findViewById (R.id.sync_data);
            viewForeground = view.findViewById (R.id.view_foreground);
        }
    }

    public ListEnquiryAdapter(Context context,RealmResults<PurchaseEnquiryData> cartList, ListEnquiryActivity listEnquiryActivity) {
        this.context = context;
        this.listEnquiryActivity = listEnquiryActivity;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ListEnquiryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_puchaseenquiry_line, parent, false);
        return new ListEnquiryAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ListEnquiryAdapter.MyViewHolder holder, final int position) {
        final PurchaseEnquiryData item = cartList.get (position);

        holder.enq_num.setText (item.getOnlineId ());
        holder.supp_name.setText (item.getSupplierName ());
        holder.sup_site_name.setText (item.getSupplierSiteName ());
        holder.enq_type.setText (item.getEnquiryType ());
        holder.status.setText (item.getSupplierSitestatus ());
        if (item.getSupplierSitestatus().equals("Draft")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_pen);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
        }
        if (item.getSupplierSitestatus().equals("Submit")) {
            Drawable img = context.getResources ().getDrawable (R.drawable.ic_view);
            img.setBounds (0, 0, 60, 60);
            holder.status.setCompoundDrawables (null, null, img, null);
            if(item.getOnlineStatus ().equals ("1")){
                holder.syncdata.setVisibility (View.GONE);
            }else{
                holder.syncdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText (context, "calling method-elsse", Toast.LENGTH_SHORT).show ();
                        listEnquiryActivity.onlineSyncMethodSingle(item.getEnquiryId ());

                    }
                });
            }
        }
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (item.getSupplierSitestatus ().equals ("Submit")) {
                    Intent intent = new Intent (context, ViewEnquiryActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getEnquiryId ()));
                    context.startActivity (intent);
                } else {
                    Intent intent = new Intent (context, EditEnquiryActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getEnquiryId ()));
                    intent.putExtra("typeof", "edit");
                    context.startActivity (intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

