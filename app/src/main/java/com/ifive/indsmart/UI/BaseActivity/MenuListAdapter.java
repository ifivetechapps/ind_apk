package com.ifive.indsmart.UI.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.Connectivity.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    SessionManager sessionManager;
    String datess;
    private Context context;
    private List<MenuItemsList> cartList;

    public MenuListAdapter(Context context, List<MenuItemsList> cartList) {
        this.context = context;
        this.cartList = cartList;
        sessionManager = new SessionManager();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MenuItemsList item = cartList.get(position);


        holder.icon.setImageDrawable(context.getResources().getDrawable(item.getIconID()));
        holder.menuName.setText(item.getMenuName());
        if (item.getSubMenuItemsList() != null) {
            holder.rightDownArrow.setVisibility(View.VISIBLE);
            holder.rightUpArrow.setVisibility(View.GONE);
        } else {
            holder.rightDownArrow.setVisibility(View.GONE);
            holder.rightUpArrow.setVisibility(View.GONE);
        }
        holder.bottomMenu.setVisibility(View.GONE);
        holder.menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSubMenuItemsList() != null) {
                    holder.menuItemListAdapter = new MenuListAdapter(context, item.getSubMenuItemsList());
                    holder.bottomMenu.setAdapter(holder.menuItemListAdapter);
                    holder.mLayoutManager = new LinearLayoutManager(context);
                    holder.bottomMenu.setLayoutManager(holder.mLayoutManager);
                    Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slidedown);
                    Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slideup);
                    if (holder.bottomMenu.getVisibility() == View.GONE) {
                        holder.bottomMenu.startAnimation(slideDown);
                        holder.bottomMenu.setVisibility(View.VISIBLE);
                        holder.rightDownArrow.setVisibility(View.GONE);
                        holder.rightUpArrow.setVisibility(View.VISIBLE);
                    } else {
                        holder.bottomMenu.startAnimation(slideDown);
                        holder.bottomMenu.setVisibility(View.GONE);
                        holder.rightDownArrow.setVisibility(View.VISIBLE);
                        holder.rightUpArrow.setVisibility(View.GONE);
                    }
                } else {
                    context.startActivity(new Intent(context, item.getaClass()));
                    holder.bottomMenu.setVisibility(View.GONE);
                }
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
        RelativeLayout menuLayout;
        MenuListAdapter menuItemListAdapter;
        RecyclerView bottomMenu;
        ImageView rightDownArrow, rightUpArrow;
        RecyclerView.LayoutManager mLayoutManager;

        public MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.icon);
            rightDownArrow = view.findViewById(R.id.right_down_arrow);
            rightUpArrow = view.findViewById(R.id.right_up_arrow);
            menuName = view.findViewById(R.id.menu_name);
            menuLayout = view.findViewById(R.id.menu_layout);
            bottomMenu = view.findViewById(R.id.bottom_menu);
            datess = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        }
    }
}