package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoOrderList {

    @SerializedName("sales_order_hdr_id")
    @Expose
    private Integer salesOrderHdrId;
    @SerializedName("sales_order_no")
    @Expose
    private String salesOrderNo;
    @SerializedName("sales_order_date")
    @Expose
    private String salesOrderDate;
    @SerializedName("username")
    @Expose
    private String username;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @SerializedName("gross_amount")
    @Expose
    private String total;

    public Integer getSalesOrderHdrId() {
        return salesOrderHdrId;
    }

    public void setSalesOrderHdrId(Integer salesOrderHdrId) {
        this.salesOrderHdrId = salesOrderHdrId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesOrderDate() {
        return salesOrderDate;
    }

    public void setSalesOrderDate(String salesOrderDate) {
        this.salesOrderDate = salesOrderDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
