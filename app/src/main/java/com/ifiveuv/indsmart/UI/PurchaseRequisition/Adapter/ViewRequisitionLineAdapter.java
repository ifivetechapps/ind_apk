package com.ifiveuv.indsmart.UI.PurchaseRequisition.Adapter;

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
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionLines;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class ViewRequisitionLineAdapter extends RecyclerView.Adapter<ViewRequisitionLineAdapter.MyViewHolder> {
    private Context contexts;
    BaseActivity createEnquiryActivity;
    RealmList<RequisitionLines> itemLists;
    List<Products> productsList;

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

    public ViewRequisitionLineAdapter(Context context, RealmList<RequisitionLines> cartList, List<Products> products, BaseActivity createPurchaseOrder) {
        this.contexts = context;
        this.createEnquiryActivity = createPurchaseOrder;
        this.itemLists = cartList;
        productsList = products;
    }

    @Override
    public ViewRequisitionLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.view_purchaserequisition_line, parent, false);

        return new ViewRequisitionLineAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final ViewRequisitionLineAdapter.MyViewHolder holder, final int mPosition) {
        final RequisitionLines itemList = itemLists.get (mPosition);
        Log.d ("position", String.valueOf (mPosition));


        holder.product.setText (itemList.getProduct ());
        holder.quantity.setText (itemList.getOrdQty ());
        holder.amount.setText (itemList.getPromised_date ());
        holder.uom.setText (itemList.getUom ());


    }

    @Override
    public int getItemCount() {
        return itemLists.size ();
    }

    public void removeItem(int position) {
        itemLists.remove (position);
        notifyItemRemoved (position);
    }

    public void restoreItem(RequisitionLines item, int position) {
        itemLists.add (position, item);
        notifyItemInserted (position);
    }
}