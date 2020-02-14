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
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.ConvertRequisitiontoQuoteActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.QuoteFromRequisitionActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model.RequisitionHeader;

import io.realm.Realm;
import io.realm.RealmResults;


public class ListRequisitionToQuoteAdapter extends RecyclerView.Adapter<ListRequisitionToQuoteAdapter.MyViewHolder> {
    private BaseActivity context;
    private RealmResults<RequisitionHeader> cartList;
    Realm realm;
    QuoteFromRequisitionActivity fromActivities;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView enq_num, supp_name, req_date, enq_type, status;

        public MyViewHolder(View view) {
            super (view);
            enq_num = view.findViewById (R.id.enq_num);
            supp_name = view.findViewById (R.id.supp_name);

            enq_type = view.findViewById (R.id.enq_type);
            req_date = view.findViewById (R.id.req_date);

            viewForeground = view.findViewById (R.id.view_foreground);
        }
    }

    public ListRequisitionToQuoteAdapter(BaseActivity context,RealmResults<RequisitionHeader> cartList,  QuoteFromRequisitionActivity fromActivity) {
        this.context = context;
        this.cartList = cartList;
        fromActivities = fromActivity;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ListRequisitionToQuoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_purhase_req, parent, false);
        return new ListRequisitionToQuoteAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final ListRequisitionToQuoteAdapter.MyViewHolder holder, final int position) {
        final RequisitionHeader item = cartList.get (position);

        holder.enq_num.setText ("REQ" + item.getHdrid ());
        holder.supp_name.setText (item.getRequestorName ());
        holder.enq_type.setText (item.getRequestStatus ());
        holder.req_date.setText (item.getRequestDate ());
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, ConvertRequisitiontoQuoteActivity.class);
                intent.putExtra ("headerid", String.valueOf (item.getHdrid ()));
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

