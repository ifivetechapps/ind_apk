package com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifive.indsmart.R;
import com.ifive.indsmart.UI.Purchase.OnlineModel.PurchaseRequisitionApprovalList;
import com.ifive.indsmart.UI.Purchase.PurchaseRequisition.ApproveListRequisitionActivity;

import io.realm.Realm;
import io.realm.RealmList;


public class ApproveListRequisitionAdapter extends RecyclerView.Adapter<ApproveListRequisitionAdapter.MyViewHolder> {
    private Context context;
    private RealmList<PurchaseRequisitionApprovalList> cartList;
    Realm realm;
    ApproveListRequisitionActivity approveListRequisitionActivity;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;
        public Button draft_data, submit_data;
        public TextView req_num, req_date, req_name, req_type, status;

        public MyViewHolder(View view) {
            super (view);
            req_num = view.findViewById (R.id.req_num);
            req_date = view.findViewById (R.id.req_date);
            req_name = view.findViewById (R.id.req_name);
            req_type = view.findViewById (R.id.req_type);
            status = view.findViewById (R.id.status);
            draft_data = view.findViewById (R.id.draft_data);
            submit_data = view.findViewById (R.id.submit_data);

            viewForeground = view.findViewById (R.id.viewForeground);
        }
    }

    public ApproveListRequisitionAdapter(Context context, RealmList<PurchaseRequisitionApprovalList> cartList, ApproveListRequisitionActivity approveListRequisitionActivity) {
        this.context = context;
        this.approveListRequisitionActivity = approveListRequisitionActivity;
        this.cartList = cartList;
        realm = Realm.getDefaultInstance ();
    }

    @Nullable
    @Override
    public ApproveListRequisitionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from (parent.getContext ()).inflate (R.layout.list_appprequisition_line, parent, false);
        return new ApproveListRequisitionAdapter.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ApproveListRequisitionAdapter.MyViewHolder holder, final int position) {
        final PurchaseRequisitionApprovalList item = cartList.get (position);

        holder.req_num.setText (item.getPurchaseReqNumber ());
        holder.req_name.setText (item.getSupplierName ());
        holder.req_type.setText (item.getPurchaseReqType ());
        if(item.getPurchaseReqType ().equals ("2")){
            holder.req_type.setText ("Labour");
        }else{
            holder.req_type.setText ("standard");
        }
        holder.req_date.setText (item.getPurchaseReqDate ());
if(item.getPurchasereqstatus ().equals ("0")){
    holder.status.setText ("Inititated");

}

        holder.submit_data.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

              approveListRequisitionActivity.onlineApprove(String.valueOf (item.getPurchaseReqId ()),"3");
            }

        });     holder.draft_data.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                approveListRequisitionActivity.onlineApprove(String.valueOf (item.getPurchaseReqId ()),"4");


            }

        });

    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }


}

