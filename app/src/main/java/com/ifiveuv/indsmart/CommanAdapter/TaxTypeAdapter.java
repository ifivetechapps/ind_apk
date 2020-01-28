package com.ifiveuv.indsmart.CommanAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.Connectivity.AllDataList;
import com.ifiveuv.indsmart.Connectivity.Tax_type;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Masters.Model.CustomerList;
import com.ifiveuv.indsmart.UI.SalesInvoice.CreateSalesInvoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;

public class TaxTypeAdapter extends RecyclerView.Adapter<TaxTypeAdapter.MyViewHolder> {
    taxonItemClickListner onItemClickListner;
    private Context context;
    private List<Tax_type> cartList = new ArrayList<>();
    private ArrayList<Tax_type> idlist;

    public TaxTypeAdapter(Context context, RealmList<Tax_type> tax_types, taxonItemClickListner onItemClickListner) {
        this.context = context;
        this.cartList = tax_types;
        this.idlist = new ArrayList<>();
        this.onItemClickListner = onItemClickListner;
        if (cartList != null) {
            this.idlist.addAll(cartList);
        }
    }



    @Override
    public TaxTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new TaxTypeAdapter.MyViewHolder(itemView, onItemClickListner);
    }

    @Override
    public void onBindViewHolder(TaxTypeAdapter.MyViewHolder holder, final int position) {
        final Tax_type item = cartList.get(position);
        holder.listName.setText(String.valueOf(item.getTaxType ()));


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cartList.clear();
        if (charText.length() == 0) {
            cartList.addAll(idlist);
        } else {
            for (Tax_type wp : idlist) {
                if (String.valueOf(wp.getTaxType ()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    cartList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface taxonItemClickListner {
        void onItemtaxPostion(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout viewForeground;
        public TextView listName, listId;
        taxonItemClickListner onItemClickListner;

        public MyViewHolder(View view, taxonItemClickListner onItemClickListner) {
            super(view);
            listName = view.findViewById(R.id.list_name);
            viewForeground = view.findViewById(R.id.view_foreground);
            this.onItemClickListner = onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListner.onItemtaxPostion(getAdapterPosition());
        }
    }
}
