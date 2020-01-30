package com.ifiveuv.indsmart.UI.BaseActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifiveuv.indsmart.Connectivity.SessionManager;
import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.SalesEnquiryList;
import com.ifiveuv.indsmart.UI.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    Typeface typeface, sfaTypeFace;
    SessionManager sessionManager;
    TextView topHeadSfa;
    RecyclerView customMenuNavigation;
    MenuListAdapter menuItemListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    TextView saleForceTitle, username, userId;
    List<MenuItemsList> menuItemsList;
    List<MenuItemsList> subMenuItemsList;
    ImageView navLogo;
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        super.setContentView (R.layout.drawer_layout);// The base layout that contains your navigation drawer.
        view_stub = findViewById (R.id.view_stub);
        navigation_view = findViewById (R.id.navigation_view);
        username = findViewById (R.id.username);
        userId = findViewById (R.id.userId);
        customMenuNavigation = findViewById (R.id.custom_menu_navigation);
        navLogo = findViewById (R.id.img_logo);
        mDrawerLayout = findViewById (R.id.drawer_layout);
        topHeadSfa = findViewById (R.id.topHeadSfa);
        saleForceTitle = findViewById (R.id.saleForceTitle);
        mDrawerToggle = new ActionBarDrawerToggle (this, mDrawerLayout,
                0, 0);
        mDrawerLayout.setDrawerListener (mDrawerToggle);
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        sessionManager = new SessionManager ();
        drawerMenu = navigation_view.getMenu ();
        for (int i = 0; i < drawerMenu.size (); i++) {
            drawerMenu.getItem (i).setOnMenuItemClickListener (this);
        }
        navigation_view.setItemIconTintList (null);
        topHeadSfa.setTypeface (sfaTypeFace);
        saleForceTitle.setTypeface (typeface, Typeface.BOLD);
        getMenuItems ();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow ().setNavigationBarColor (ContextCompat.getColor (this, R.color.colorPrimary));
        }


        navLogo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (BaseActivity.this, BaseActivity.class);
                startActivity (i);
            }
        });
    }


    private void getMenuItems() {
        menuItemsList = new ArrayList<> ();
        menuItemsList.add (setMenuItem (SalesEnquiryList.class, "Create Sales Order",
                R.drawable.purchase, null, true));

        setMenuRecycler ();
    }

    private void setMenuRecycler() {
        menuItemListAdapter = new MenuListAdapter (this, menuItemsList);
        customMenuNavigation.setAdapter (menuItemListAdapter);
        mLayoutManager = new LinearLayoutManager (this);
        customMenuNavigation.setLayoutManager (mLayoutManager);
    }

    private MenuItemsList setMenuItem(Class<?> className, String menuName, int iconPath,
                                      List<MenuItemsList> subMenuItems, boolean withAttendance) {
        MenuItemsList menuItemsList = new MenuItemsList ();
        menuItemsList.setaClass (className);
        menuItemsList.setIconID (iconPath);
        menuItemsList.setMenuName (menuName);
        menuItemsList.setSubMenuItemsList (subMenuItems);
        menuItemsList.setWithAttendance (withAttendance);
        return menuItemsList;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate (savedInstanceState);
        mDrawerToggle.syncState ();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged (newConfig);
        mDrawerToggle.onConfigurationChanged (newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService (LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams (
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate (layoutResID, view_stub, false);
            view_stub.addView (stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams (
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView (view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView (view, params);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId ()) {
        }
        mDrawerLayout.closeDrawers ();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected (item)) {
            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (sessionManager.getToken (this) == null) {

            startActivity (new Intent (BaseActivity.this, LoginActivity.class));
        }
    }
}