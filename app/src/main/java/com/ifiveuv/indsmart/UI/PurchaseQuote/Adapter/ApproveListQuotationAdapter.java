package com.ifiveuv.indsmart.UI.PurchaseQuote.Adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;

import com.ifiveuv.indsmart.UI.PurchaseQuote.ApproveQuotationActivity;
import com.ifiveuv.indsmart.UI.PurchaseQuote.Model.QuotationHeader;

import io.realm.Realm;
import io.realm.RealmResults;


public class ApproveListQuotationAdapter extends RecyclerView.Adapter<ApproveListQuotationAdapter.MyViewHolder> {
    private BaseActivity context;
    private RealmResults<QuotationHeader> cartList;
    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView enq_num, supp_name, sup_site_name, enq_type, status;

        public MyViewHolder(View view) {
            super (view);
            enq_num = view.findViewById (R.id.enq_num);
            supp_name = view.findViewById (R.id.supp_name);

            enq_type = view.findViewById (R.id.enq_type);

            viewForeground = view.findViewById (R.id.view_foreground);
        }
    }

    public ApproveListQuotationAdapter(RealmResults<QuotationHeader> cartList, BaseActivity context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ApproveListQuotationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_quotation_approve, parent, false);
        return new ApproveListQuotationAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ApproveListQuotationAdapter.MyViewHolder holder, final int position) {
        final QuotationHeader item = cartList.get (position);

        holder.enq_num.setText ("QO" + item.getQuote_id ());
        holder.supp_name.setText (item.getSupplierName ());
        holder.enq_type.setText (item.getSupplierSite ());
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (context, ApproveQuotationActivity.class);
                intent.putExtra ("headerid", String.valueOf (item.getQuote_id ()));
                context.startActivity (intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

