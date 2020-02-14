package com.ifive.indsmart.UI.DashBoard;

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

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;
import com.ifive.indsmart.Connectivity.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.MyViewHolder> {

    SessionManager sessionManager;
    private Context context;
    private List<DashboardItemsList> cartList = new ArrayList<>();


    public DashboardListAdapter(Context context, List<DashboardItemsList> cartList) {
        this.context = context;
        this.cartList = cartList;
        sessionManager = new SessionManager();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
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
                String typeName = item.getMenuName();
                Intent newActivity1 = new Intent(context, SubDashboard.class);
                newActivity1.putExtra("type", typeName);
                context.startActivity(newActivity1);
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeName = item.getMenuName();
                Intent newActivity1 = new Intent(context, SubDashboard.class);
                newActivity1.putExtra("type", typeName);
                context.startActivity(newActivity1);
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