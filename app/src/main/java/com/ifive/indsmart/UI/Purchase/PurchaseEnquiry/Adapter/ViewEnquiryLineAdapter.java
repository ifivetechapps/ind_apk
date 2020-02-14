package com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.Model.EnquiryItemList;
import com.ifive.indsmart.UI.Purchase.PurchaseEnquiry.ViewEnquiryActivity;

import io.realm.Realm;
import io.realm.RealmList;

public class ViewEnquiryLineAdapter extends RecyclerView.Adapter<ViewEnquiryLineAdapter.MyViewHolder> {
    private Context context;
    ViewEnquiryActivity createEnquiryActivity;
    RealmList<EnquiryItemList> itemLists;
    Realm realm;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, uom;
        public TextView quantity;
        public TextView product;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            amount = view.findViewById (R.id.amount);

            uom = view.findViewById (R.id.uom);
            realm = Realm.getDefaultInstance ();
        }
    }

    public ViewEnquiryLineAdapter(Context context, RealmList<EnquiryItemList> cartList, ViewEnquiryActivity createPurchaseOrder) {
        this.context = context;
        this.createEnquiryActivity = createPurchaseOrder;
        this.itemLists = cartList;
    }

    @Override
    public ViewEnquiryLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.view_purchaseenquiry_line, parent, false);

        return new ViewEnquiryLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final ViewEnquiryLineAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryItemList itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));

        holder.product.setText (itemList.getProduct ());
        holder.uom.setText (itemList.getUom ());
        holder.quantity.setText (itemList.getOrdQty ());
        holder.amount.setText (itemList.getPromised_date ());
    }

    @Override
    public int getItemCount() {
        return itemLists.size ();
    }

    public void removeItem(int position) {
        itemLists.remove (position);
        notifyItemRemoved (position);
    }

    public void restoreItem(EnquiryItemList item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }
}