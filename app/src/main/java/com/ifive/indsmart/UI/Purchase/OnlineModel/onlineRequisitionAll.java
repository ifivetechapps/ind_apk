package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class onlineRequisitionAll extends RealmObject {

    @SerializedName("purchase_requisition_tbl")
    @Expose
    private RealmList<PurchaseRequisitionTbl> purchaseRequisitionTbl = null;
    @SerializedName("purchase_requisition_details_tbl")
    @Expose
    private RealmList<PurchaseRequisitionDetailsTbl> purchaseRequisitionDetailsTbl = null;

    public RealmList<PurchaseRequisitionTbl> getPurchaseRequisitionTbl() {
        return purchaseRequisitionTbl;
    }

    public void setPurchaseRequisitionTbl(RealmList<PurchaseRequisitionTbl> purchaseRequisitionTbl) {
        this.purchaseRequisitionTbl = purchaseRequisitionTbl;
    }

    public RealmList<PurchaseRequisitionDetailsTbl> getPurchaseRequisitionDetailsTbl() {
        return purchaseRequisitionDetailsTbl;
    }

    public void setPurchaseRequisitionDetailsTbl(RealmList<PurchaseRequisitionDetailsTbl> purchaseRequisitionDetailsTbl) {
        this.purchaseRequisitionDetailsTbl = purchaseRequisitionDetailsTbl;
    }
}
