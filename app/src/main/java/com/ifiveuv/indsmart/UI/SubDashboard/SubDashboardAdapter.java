package com.ifiveuv.indsmart.UI.SubDashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.DashBoard.DashboardItemsList;
import com.ifiveuv.indsmart.Connectivity.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SubDashboardAdapter extends RecyclerView.Adapter<SubDashboardAdapter.MyViewHolder> {

    SessionManager sessionManager;
    private Context context;
    private List<DashboardItemsList> cartList = new ArrayList<>();


    public SubDashboardAdapter(Context context, List<DashboardItemsList> cartList) {
        this.context = context;
        this.cartList = cartList;
        sessionManager = new SessionManager();
    }

    @Override
    public SubDashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_sub_list, parent, false);

        return new SubDashboardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubDashboardAdapter.MyViewHolder holder, final int position) {
        final DashboardItemsList item = cartList.get(position);
        holder.icon.setImageDrawable(context.getResources().getDrawable(item.getIconID()));
        if (item.getColorID() != 0) {
            holder.icon.setColorFilter(context.getResources().getColor(item.getColorID()));
        }
        holder.menuName.setText(item.getMenuName());
//        holder.icon.setBackgroundColor(context.getResources().getColor(item.getColorID()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, item.getaClass()));
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, item.getaClass()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView menuName;
        CardView menuLayout;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.image);
            menuName = view.findViewById(R.id.menu_name);
            menuLayout = view.findViewById(R.id.menu_layout);
            layout = view.findViewById(R.id.layout);
        }
    }
}