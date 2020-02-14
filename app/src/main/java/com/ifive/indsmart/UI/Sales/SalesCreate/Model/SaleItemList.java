package com.ifive.indsmart.UI.Sales.SalesCreate.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SaleItemList extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer SalesOrderid;
    @SerializedName("senq_id")
    @Expose
    private Integer seId;
    @SerializedName("squo_id")
    @Expose
    private int sqId;
    @SerializedName("online_status")
    @Expose
    private String onlinestatus;
    @SerializedName("online_id")
    @Expose
    private String onlineId;
    @SerializedName("Approval")
    @Expose
    private String approvalstatus;
    @SerializedName("se_id")
    @Expose
    private String onlineseId;
    @SerializedName("sq_id")
    @Expose
    private String onlinesqId;
    @SerializedName("sales_order_date")
    @Expose
    private String sodate;
    @SerializedName("customer_name")
    @Expose
    private String cus_name;
    @SerializedName("customer_id")
    @Expose
    private int cus_id;
    @SerializedName("status_id")
    @Expose
    private String status;
    @SerializedName("type_id")
    @Expose
    private String typeOfOrder;
    @SerializedName("so_delivery_date")
    @Expose
    private String del_date;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("invoice_status")
    @Expose
    private String statusvalue;

    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("tax_value")
    @Expose
    private String taxValue;
    @SerializedName("tax_type_id")
    @Expose
    private String taxTypeID;
    @SerializedName("tax_total")
    @Expose
    private String totalTax;
    @SerializedName("net_price")
    @Expose
    private String netPrice;

    @SerializedName("so_line_list")
    @Expose
    private RealmList<SalesItemLineList> salesItemLineLists = null;
    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getTaxTypeID() {
        return taxTypeID;
    }

    public void setTaxTypeID(String taxTypeID) {
        this.taxTypeID = taxTypeID;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }


    public Integer getSalesOrderid() {
        return SalesOrderid;
    }

    public void setSalesOrderid(Integer salesOrderid) {
        SalesOrderid = salesOrderid;
    }

    public Integer getSeId() {
        return seId;
    }

    public void setSeId(Integer seId) {
        this.seId = seId;
    }

    public int getSqId() {
        return sqId;
    }

    public void setSqId(int sqId) {
        this.sqId = sqId;
    }

    public String getSodate() {
        return sodate;
    }

    public void setSodate(String sodate) {
        this.sodate = sodate;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public String getDel_date() {
        return del_date;
    }

    public void setDel_date(String del_date) {
        this.del_date = del_date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatusvalue() {
        return statusvalue;
    }

    public void setStatusvalue(String statusvalue) {
        this.statusvalue = statusvalue;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }



    public String getOnlineseId() {
        return onlineseId;
    }

    public void setOnlineseId(String onlineseId) {
        this.onlineseId = onlineseId;
    }

    public String getOnlinesqId() {
        return onlinesqId;
    }

    public void setOnlinesqId(String onlinesqId) {
        this.onlinesqId = onlinesqId;
    }


    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public String getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(String onlineId) {
        this.onlineId = onlineId;
    }




    public RealmList<SalesItemLineList> getSalesItemLineLists() {
        return salesItemLineLists;
    }

    public void setSalesItemLineLists(RealmList<SalesItemLineList> salesItemLineLists) {
        this.salesItemLineLists = salesItemLineLists;
    }
}
