package com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.PurchaseEnquiryData;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.ConvertEnquirytoQuoteActivity;


import io.realm.Realm;
import io.realm.RealmResults;


public class ListQuotationAdapter extends RecyclerView.Adapter<ListQuotationAdapter.MyViewHolder> {
    private BaseActivity context;
    private RealmResults<PurchaseEnquiryData> cartList;
    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView enq_num, supp_name, sup_site_name, enq_type, status;

        public MyViewHolder(View view) {
            super (view);
            enq_num = view.findViewById (R.id.enq_num);
            supp_name = view.findViewById (R.id.supp_name);
            sup_site_name = view.findViewById (R.id.sup_site_name);

            enq_type = view.findViewById (R.id.enq_type);
            status = view.findViewById (R.id.status);

            viewForeground = view.findViewById (R.id.view_foreground);
        }
    }

    public ListQuotationAdapter(RealmResults<PurchaseEnquiryData> cartList, BaseActivity context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ListQuotationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_quote_adapter, parent, false);
        return new ListQuotationAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ListQuotationAdapter.MyViewHolder holder, final int position) {
        final PurchaseEnquiryData item = cartList.get (position);


        holder.enq_num.setText ("ENQ" + item.getEnquiryId ());
        holder.supp_name.setText (item.getSupplierName ());
        holder.sup_site_name.setText (item.getSupplierSiteName ());
        holder.enq_type.setText (item.getEnquiryType ());
        holder.status.setText (item.getSupplierSitestatus ());

        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, ConvertEnquirytoQuoteActivity.class);
                intent.putExtra ("headerid", String.valueOf (item.getEnquiryId ()));
                intent.putExtra ("type", "Standard");
                intent.putExtra ("source", "requisition");
                context.startActivity (intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

