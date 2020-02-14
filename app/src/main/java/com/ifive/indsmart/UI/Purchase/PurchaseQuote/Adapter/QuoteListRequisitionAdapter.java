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
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.EditQuotationActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Model.QuotationHeader;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.ViewQuotationActivity;


import io.realm.Realm;
import io.realm.RealmResults;


public class QuoteListRequisitionAdapter extends RecyclerView.Adapter<QuoteListRequisitionAdapter.MyViewHolder> {
    private BaseActivity context;
    private RealmResults<QuotationHeader> cartList;
    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView qo_num, supp_name, supp_site, grand_total, enq_type, status;

        public MyViewHolder(View view) {
            super (view);
            qo_num = view.findViewById (R.id.qo_num);
            supp_name = view.findViewById (R.id.supp_name);
            grand_total = view.findViewById (R.id.grand_total);
            supp_site = view.findViewById (R.id.supp_site);

            status = view.findViewById (R.id.status);
            enq_type = view.findViewById (R.id.enq_type);

            viewForeground = view.findViewById (R.id.view_foreground);
        }
    }

    public QuoteListRequisitionAdapter(RealmResults<QuotationHeader> cartList, BaseActivity context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public QuoteListRequisitionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.quotelist_view, parent, false);
        return new QuoteListRequisitionAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(QuoteListRequisitionAdapter.MyViewHolder holder, final int position) {
        final QuotationHeader item = cartList.get (position);

        holder.qo_num.setText ("QO" + item.getQuote_id ());
        holder.supp_name.setText (item.getSupplierName ());
        holder.grand_total.setText (item.getGrandTotal ());
        holder.enq_type.setText (item.getQuoteType ());
        holder.supp_site.setText (item.getSupplierSite ());
        holder.status.setText (item.getStatus ());
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (item.getStatus ().equals ("Draft")) {

                    Intent intent = new Intent (context, EditQuotationActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getQuote_id ()));
                    context.startActivity (intent);
                } else {
                    Intent intent = new Intent (context, ViewQuotationActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getQuote_id ()));
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

