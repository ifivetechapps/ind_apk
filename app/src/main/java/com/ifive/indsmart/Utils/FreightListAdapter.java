package com.ifive.indsmart.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Masters.Model.FreightList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FreightListAdapter extends RecyclerView.Adapter<FreightListAdapter.MyViewHolder> {
    private Context context;
    private List<FreightList> cartList = new ArrayList<> ();

    private ArrayList<FreightList> idlist;
    onItemClickListner onItemClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public LinearLayout viewForeground;
        public TextView listName, listId;
        onItemClickListner onItemClickListner;
        public MyViewHolder(View view,onItemClickListner onItemClickListner) {
            super (view);
            listName = view.findViewById (R.id.list_name);

            viewForeground = view.findViewById (R.id.view_foreground);
            this.onItemClickListner = onItemClickListner;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onItemClickListner.onItemPostion(getAdapterPosition(),"freight");
        }
    }

    public FreightListAdapter(Context context, List<FreightList> cartList, onItemClickListner onItemClickListner ) {
        this.context = context;
        this.cartList = cartList;
        this.idlist = new ArrayList<>();
        this.onItemClickListner = onItemClickListner;
        if (cartList != null) {
            this.idlist.addAll(cartList);
        }
    }

    @Override
    public FreightListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.list_item, parent, false);

        return new FreightListAdapter.MyViewHolder (itemView,onItemClickListner);
    }

    @Override
    public void onBindViewHolder(FreightListAdapter.MyViewHolder holder, final int position) {
        final FreightList item = cartList.get (position);
        holder.listName.setText(String.valueOf(item.getFreightName ()));

    }


    @Override
    public int getItemCount() {
        return cartList.size ();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase (Locale.getDefault ());
        cartList.clear ();
        if (charText.length () == 0) {
            cartList.addAll (idlist);
        } else {
            for (FreightList wp : idlist) {
                if (String.valueOf (wp.getFreightName ()).toLowerCase (Locale.getDefault ()).contains (charText)) {
                    cartList.add (wp);
                }
            }
        }
        notifyDataSetChanged ();
    }
    public interface onItemClickListner {
        void onItemPostion(int position,String type);
    }
}
