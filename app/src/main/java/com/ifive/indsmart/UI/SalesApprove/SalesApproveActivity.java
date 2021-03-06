package com.ifive.indsmart.UI.SalesApprove;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.BaseActivity.BaseActivity;
import com.ifive.indsmart.UI.SubDashboard.SubDashboard;

import butterknife.ButterKnife;

public class SalesApproveActivity extends BaseActivity {

    Button soa, sqa, sia, soe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_approve);
        ButterKnife.bind(this);

        soe = findViewById(R.id.soe);
        soa = findViewById(R.id.soa);
        sqa = findViewById(R.id.sqa);
        sia = findViewById(R.id.sia);
        loadFragment(new SalesEnquiryApprove());
        soe.setBackgroundResource(R.drawable.blue_border);
        soa.setBackgroundResource(R.drawable.before_click);
        sqa.setBackgroundResource(R.drawable.before_click);
        sia.setBackgroundResource(R.drawable.before_click);


        // perform setOnClickListener event on First Button
        soe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SalesEnquiryApprove());
                soe.setBackgroundResource(R.drawable.blue_border);
                soa.setBackgroundResource(R.drawable.before_click);
                sqa.setBackgroundResource(R.drawable.before_click);
                sia.setBackgroundResource(R.drawable.before_click);
            }
        });


        soa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SalesOrderApprove());
                soa.setBackgroundResource(R.drawable.blue_border);
                sqa.setBackgroundResource(R.drawable.before_click);
                soe.setBackgroundResource(R.drawable.before_click);
                sia.setBackgroundResource(R.drawable.before_click);
            }
        });

        sqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SalesQuoteApprove());
                soa.setBackgroundResource(R.drawable.before_click);
                sqa.setBackgroundResource(R.drawable.blue_border);
                soe.setBackgroundResource(R.drawable.before_click);
                sia.setBackgroundResource(R.drawable.before_click);
            }
        });

        sia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SalesInvoiceApprove());
                soa.setBackgroundResource(R.drawable.before_click);
                sqa.setBackgroundResource(R.drawable.before_click);
                soe.setBackgroundResource(R.drawable.before_click);
                sia.setBackgroundResource(R.drawable.blue_border);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getApplicationContext(), SubDashboard.class);
        it.putExtra("type", "Sales");

        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}