package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PurchaseOrderAll extends RealmObject {

    @SerializedName("purchase_tbl")
    @Expose
    private RealmList<PurchaseTbl> purchaseTbl = null;
    @SerializedName("po_lines_tbl")
    @Expose
    private RealmList<PoLinesTbl> poLinesTbl = null;

    public RealmList<PurchaseTbl> getPurchaseTbl() {
        return purchaseTbl;
    }

    public void setPurchaseTbl(RealmList<PurchaseTbl> purchaseTbl) {
        this.purchaseTbl = purchaseTbl;
    }

    public RealmList<PoLinesTbl> getPoLinesTbl() {
        return poLinesTbl;
    }

    public void setPoLinesTbl(RealmList<PoLinesTbl> poLinesTbl) {
        this.poLinesTbl = poLinesTbl;
    }

}
