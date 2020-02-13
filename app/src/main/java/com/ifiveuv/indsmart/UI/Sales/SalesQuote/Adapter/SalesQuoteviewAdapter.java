package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.Connectivity.Products;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model.QuoteItemLineList;
import com.ifiveuv.indsmart.UI.Sales.SalesQuote.SalesQuoteViewActivity;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SalesQuoteviewAdapter extends RecyclerView.Adapter<SalesQuoteviewAdapter.MyViewHolder> {
    SalesQuoteViewActivity salesOrderEditActivity;
    RealmList<QuoteItemLineList> itemLists;
    Realm realm;
    private Context context;


    public SalesQuoteviewAdapter(Context context, RealmList<QuoteItemLineList> cartList, SalesQuoteViewActivity salesOrderEditActivity) {
        this.context = context;
        this.salesOrderEditActivity = salesOrderEditActivity;
        this.itemLists = cartList;
    }

    @Override
    public SalesQuoteviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salesquote_item_view, parent, false);
        return new SalesQuoteviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalesQuoteviewAdapter.MyViewHolder holder, final int mPosition) {
        final QuoteItemLineList itemList = itemLists.get(mPosition);
        Log.d("position", String.valueOf(mPosition));
        holder.quantity.setText(String.valueOf(itemList.getQuantity()));
        holder.amount.setText(itemList.getUnitPrice());
       // String product = itemList.getProduct();
        //holder.product.setText(product);

        Products products=realm.where (Products.class).equalTo ("pro_id", itemList.getProductId ()).findFirst ();

        String name =products.getProduct_name ();
        holder.product.setText(name);
        RealmResults<UomModel> uomModels = realm.where(UomModel.class).equalTo("uom_id", Integer.valueOf(itemList.getUomId())).findAll();
        holder.uom.setText(uomModels.get(0).getUom_name());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void removeItem(int position) {
        itemLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(QuoteItemLineList item, int position) {
        itemLists.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quantity, amount;
        public TextView uom, type, size, product;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            viewForeground = view.findViewById(R.id.view_foreground);
            product = view.findViewById(R.id.product);
            quantity = view.findViewById(R.id.quantity);
            amount = view.findViewById(R.id.amount);
            uom = view.findViewById(R.id.uom);
            realm = Realm.getDefaultInstance();
        }
    }
}