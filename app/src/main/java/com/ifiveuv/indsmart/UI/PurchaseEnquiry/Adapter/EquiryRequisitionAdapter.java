package com.ifiveuv.indsmart.UI.PurchaseEnquiry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.ConvertRequisitionToEnquiryActivity;
import com.ifiveuv.indsmart.UI.PurchaseEnquiry.RequisitionList;
import com.ifiveuv.indsmart.UI.PurchaseRequisition.Model.RequisitionHeader;

import io.realm.Realm;
import io.realm.RealmResults;


public class EquiryRequisitionAdapter extends RecyclerView.Adapter<EquiryRequisitionAdapter.MyViewHolder> {
    private Context context;
    private RealmResults<RequisitionHeader> cartList;
    Realm realm;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public TextView req_num, req_name, req_date, req_type, status;

        public MyViewHolder(View view) {
            super (view);
            req_num = view.findViewById (R.id.req_num);
            req_date = view.findViewById (R.id.req_date);
            req_name = view.findViewById (R.id.req_name);
            req_type = view.findViewById (R.id.req_type);
            status = view.findViewById (R.id.status);

            viewForeground = view.findViewById (R.id.viewForeground);
        }
    }

    public EquiryRequisitionAdapter(Context context,RealmResults<RequisitionHeader> cartList, RequisitionList requisitionList) {
        this.context = context;
        this.context = requisitionList;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public EquiryRequisitionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_requisitionpenquiry, parent, false);
        return new EquiryRequisitionAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(EquiryRequisitionAdapter.MyViewHolder holder, final int position) {
        final RequisitionHeader item = cartList.get (position);

        holder.req_num.setText (item.getOnlineReqId ());
        holder.req_name.setText (item.getRequestorName ());
        holder.req_type.setText (item.getTypeRequisition ());
        holder.req_date.setText (item.getRequestDate ());
        holder.status.setText (item.getRequestStatus ());
        holder.viewForeground.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (context, ConvertRequisitionToEnquiryActivity.class);
                intent.putExtra ("headerid", String.valueOf (item.getHdrid ()));
                intent.putExtra ("type", "standard");
                intent.putExtra ("source", "requisition");
                context.startActivity (intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

