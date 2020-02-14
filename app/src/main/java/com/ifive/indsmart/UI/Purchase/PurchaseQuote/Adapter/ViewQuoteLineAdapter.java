package com.ifive.indsmart.UI.Purchase.PurchaseQuote.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.Purchase.PurchaseQuote.Model.QuotationLines;

import io.realm.Realm;
import io.realm.RealmList;

public class ViewQuoteLineAdapter extends RecyclerView.Adapter<ViewQuoteLineAdapter.MyViewHolder> {
    private Context context;
    BaseActivity createEnquiryActivity;
    RealmList<QuotationLines> itemLists;

    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, date, tax, price;
        public TextView quantity, discount;
        public TextView product;
        public LinearLayout viewForeground;

        public MyViewHolder(View view) {
            super (view);
            viewForeground = view.findViewById (R.id.view_foreground);
            product = view.findViewById (R.id.product);
            quantity = view.findViewById (R.id.quantity);
            amount = view.findViewById (R.id.amount);
            tax = view.findViewById (R.id.tax);
            price = view.findViewById (R.id.price);
            discount = view.findViewById (R.id.discount);
            date = view.findViewById (R.id.date);
            realm = Realm.getDefaultInstance ();
        }
    }

    public ViewQuoteLineAdapter(Context context, RealmList<QuotationLines> cartList, BaseActivity createPurchaseOrder) {
        this.context = context;
        this.createEnquiryActivity = createPurchaseOrder;
        this.itemLists = cartList;

    }

    @Override
    public ViewQuoteLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.view_quote_line, parent, false);

        return new ViewQuoteLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final ViewQuoteLineAdapter.MyViewHolder holder, final int mPosition) {
        final QuotationLines itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));
        holder.product.setText (itemList.getProduct ());
        holder.quantity.setText (itemList.getQuoteQty ());
        holder.price.setText (itemList.getPrice () + "");
        holder.discount.setText (itemList.getDiscountPercent ());
        holder.tax.setText (itemList.getTaxGroup ());
        holder.amount.setText (itemList.getLineTotal ());
        holder.date.setText (itemList.getPromisedDate ());


    }

    @Override
    public int getItemCount() {
        return itemLists.size ();
    }

    public void removeItem(int position) {
        itemLists.remove (position);
        notifyItemRemoved (position);
    }

    public void restoreItem(QuotationLines item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }
}