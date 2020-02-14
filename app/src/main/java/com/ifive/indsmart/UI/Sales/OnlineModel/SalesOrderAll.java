package com.ifive.indsmart.UI.Sales.OnlineModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SalesOrderAll extends RealmObject {

    @SerializedName("so_headerorder_details")
    @Expose
    private RealmList<SoHeaderTable> soHeaderorderDetails = null;
    @SerializedName("so_order_details")
    @Expose
    private RealmList<SoDetailTable> soOrderDetails = null;

    public RealmList<SoHeaderTable> getSoHeaderorderDetails() {
        return soHeaderorderDetails;
    }

    public void setSoHeaderorderDetails(RealmList<SoHeaderTable> soHeaderorderDetails) {
        this.soHeaderorderDetails = soHeaderorderDetails;
    }

    public RealmList<SoDetailTable> getSoOrderDetails() {
        return soOrderDetails;
    }

    public void setSoOrderDetails(RealmList<SoDetailTable> soOrderDetails) {
        this.soOrderDetails = soOrderDetails;
    }


}