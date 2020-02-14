package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PurchaseRequisitionHeader extends RealmObject {
    @SerializedName("purchase_requisition_list")
    @Expose
    private RealmList<PurchaseRequisitionList> purchaseRequisitionList = null;
    @SerializedName("purchase_requisition_details_list")
    @Expose
    private RealmList<PurchaseRequisitionList> purchaseRequisitionListRealmList = null;

    public RealmList<PurchaseRequisitionList> getPurchaseRequisitionList() {
        return purchaseRequisitionList;
    }

    public void setPurchaseRequisitionList(RealmList<PurchaseRequisitionList> purchaseRequisitionList) {
        this.purchaseRequisitionList = purchaseRequisitionList;
    }
}
