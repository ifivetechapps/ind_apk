package com.ifive.indsmart.CommanAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.MyViewHolder> {
    onItemClickListner onItemClickListner;
    private Context context;
    private List<SupplierList> cartList = new ArrayList<>();
    private ArrayList<SupplierList> idlist;

    public SupplierListAdapter(Context context, List<SupplierList> cartList, onItemClickListner onItemClickListner) {
        this.context = context;
        this.cartList = cartList;
        this.idlist = new ArrayList<>();
        this.onItemClickListner = onItemClickListner;
        if (cartList != null) {
            this.idlist.addAll(cartList);
        }
    }

    @Override
    public SupplierListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new SupplierListAdapter.MyViewHolder(itemView, onItemClickListner);
    }

    @Override
    public void onBindViewHolder(SupplierListAdapter.MyViewHolder holder, final int position) {
        final SupplierList item = cartList.get(position);
        holder.listName.setText(String.valueOf(item.getSupplierName ()));


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
            for (SupplierList wp : idlist) {
                if (String.valueOf(wp.getSupplierName()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    cartList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface onItemClickListner {
        void onItemPostion(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout viewForeground;
        public TextView listName, listId;
        onItemClickListner onItemClickListner;

        public MyViewHolder(View view, onItemClickListner onItemClickListner) {
            super(view);
            listName = view.findViewById(R.id.list_name);
            viewForeground = view.findViewById(R.id.view_foreground);
            this.onItemClickListner = onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListner.onItemPostion(getAdapterPosition());
        }
    }
}
