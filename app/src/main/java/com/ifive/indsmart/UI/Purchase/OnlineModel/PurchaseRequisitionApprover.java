package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PurchaseRequisitionApprover extends RealmObject {
    @SerializedName("purchase_requisition_list")
    @Expose
    private RealmList<PurchaseRequisitionApprovalList> purchaseRequisitionApprovalList = null;

    public RealmList<PurchaseRequisitionApprovalList> getPurchaseRequisitionApprovalList() {
        return purchaseRequisitionApprovalList;
    }

    public void setPurchaseRequisitionApprovalList(RealmList<PurchaseRequisitionApprovalList> purchaseRequisitionApprovalList) {
        this.purchaseRequisitionApprovalList = purchaseRequisitionApprovalList;
    }
}
