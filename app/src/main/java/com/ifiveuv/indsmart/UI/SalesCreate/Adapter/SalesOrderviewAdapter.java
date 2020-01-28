package com.ifiveuv.indsmart.UI.SalesCreate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;

import com.ifiveuv.indsmart.UI.SalesCreate.Model.SalesItemLineList;
import com.ifiveuv.indsmart.UI.SalesCreate.SalesOrderViewActivity;
import com.ifiveuv.indsmart.UI.SalesQuote.SalesQuoteViewActivity;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesOrderviewAdapter extends RecyclerView.Adapter<SalesOrderviewAdapter.MyViewHolder> {
    SalesOrderViewActivity salesOrderEditActivity;
    RealmList<SalesItemLineList> itemLists;
    Realm realm;
    private Context context;


    public SalesOrderviewAdapter(Context context, RealmList<SalesItemLineList> cartList, SalesOrderViewActivity salesOrderEditActivity) {
        this.context = context;
        this.salesOrderEditActivity = salesOrderEditActivity;
        this.itemLists = cartList;
    }

    @Override
    public SalesOrderviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salesorder_item_view, parent, false);
        return new SalesOrderviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesOrderviewAdapter.MyViewHolder holder, final int mPosition) {
        final SalesItemLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
       holder.product.setText (itemList.getProduct ());
       holder.quantity.setText (itemList.getQuantity ()+"");
       holder.unit_price.setText (itemList.getUnitPrice ());
       holder.discount.setText (itemList.getDisAmt ());
       holder.amount.setText (itemList.getLineTotal ());
       holder.uom.setText (itemList.getUom ());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void removeItem(int position) {
        itemLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(SalesItemLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quantity, unit_price,amount;
        public TextView uom, type, size, product,discount;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            quantity = view.findViewById(R.id.quantity);
            unit_price = view.findViewById(R.id.unit_price);
            discount = view.findViewById(R.id.discount);
            amount = view.findViewById(R.id.amount);
            uom = view.findViewById(R.id.uom);
            realm = Realm.getDefaultInstance();
        }
    }
}