package com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model.EnquiryLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryViewActivity;

import io.realm.Realm;
import io.realm.RealmList;

public class SalesEnquiryviewAdapter extends RecyclerView.Adapter<SalesEnquiryviewAdapter.MyViewHolder> {
    SalesEnquiryViewActivity salesOrderEditActivity;
    RealmList<EnquiryLineList> itemLists;
    Realm realm;
    private Context context;


    public SalesEnquiryviewAdapter(Context context, RealmList<EnquiryLineList> cartList, SalesEnquiryViewActivity salesOrderEditActivity) {
        this.context = context;
        this.salesOrderEditActivity = salesOrderEditActivity;
        this.itemLists = cartList;
    }

    @Override
    public SalesEnquiryviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enquiry_item_view, parent, false);
        return new SalesEnquiryviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesEnquiryviewAdapter.MyViewHolder holder, final int mPosition) {
        final EnquiryLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
        holder.quantity.setText (itemList.getEnquiryRequiredQuantity ());
        holder.uom.setText (itemList.getEnquiryUom ());
        holder.product.setText (itemList.getEnquiryProduct ());


    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void removeItem(int position) {
        itemLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(EnquiryLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uom, quantity, product;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            uom = view.findViewById(R.id.uom);
            quantity = view.findViewById(R.id.quantity);

            realm = Realm.getDefaultInstance();
        }
    }
}