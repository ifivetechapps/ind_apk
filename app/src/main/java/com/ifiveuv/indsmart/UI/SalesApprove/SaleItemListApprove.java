package com.ifiveuv.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleItemListApprove {

    @SerializedName("so_order_list")
    @Expose
    private List<SoOrderList> soOrderList = null;

    public List<SoOrderList> getSoOrderList() {
        return soOrderList;
    }

    public void setSoOrderList(List<SoOrderList> soOrderList) {
        this.soOrderList = soOrderList;
    }
}
