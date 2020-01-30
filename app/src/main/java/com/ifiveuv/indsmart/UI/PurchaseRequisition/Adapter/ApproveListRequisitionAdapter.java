package com.ifiveuv.indsmart.UI.PurchaseRequisition.Adapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.BaseActivity.BaseActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.ApproveRequisitionActivity;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionHeader;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.ViewRequisitionActivity;

import io.realm.Realm;
import io.realm.RealmResults;


public class ApproveListRequisitionAdapter extends RecyclerView.Adapter<ApproveListRequisitionAdapter.MyViewHolder> {
    private BaseActivity context;
    private RealmResults<RequisitionHeader> cartList;
    Realm realm;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public Button sync_data;
        public TextView req_num,req_date,req_name,req_type,status;

        public MyViewHolder(View view) {
            super (view);
            req_num = view.findViewById (R.id.req_num);
            req_date = view.findViewById (R.id.req_date);
            req_name = view.findViewById (R.id.req_name);
            req_type = view.findViewById (R.id.req_type);
            status = view.findViewById (R.id.status);
            sync_data = view.findViewById (R.id.sync_data);

            viewForeground = view.findViewById (R.id.viewForeground);
        }
    }
    public ApproveListRequisitionAdapter(RealmResults<RequisitionHeader> cartList, BaseActivity context) {
        this.context = context;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ApproveListRequisitionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_prequisition_line, parent, false);
        return new ApproveListRequisitionAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ApproveListRequisitionAdapter.MyViewHolder holder, final int position) {
        final RequisitionHeader item = cartList.get (position);

        holder.req_num.setText (item.getOnlineReqId ());
        holder.req_name.setText (item.getRequestorName ());
        holder.req_type.setText (item.getTypeRequisition ());
        holder.req_date.setText (item.getRequestDate ());
        holder.status.setText(item.getRequestStatus ());
        holder.sync_data.setVisibility (View.GONE);
        Drawable img = context.getResources ().getDrawable (R.drawable.ic_check_circle_black_24dp);
        img.setBounds (0, 0, 60, 60);
        holder.status.setCompoundDrawables (null, null, img, null);
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(item.getRequestStatus ().equals ("Approved")||item.getRequestStatus ().equals ("Rejected")){
                    Intent intent = new Intent (context, ViewRequisitionActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getHdrid ()));
                    context.startActivity (intent);
                }else{
                    Intent intent = new Intent (context, ApproveRequisitionActivity.class);
                    intent.putExtra ("headerid", String.valueOf (item.getHdrid ()));
                    context.startActivity (intent);
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

