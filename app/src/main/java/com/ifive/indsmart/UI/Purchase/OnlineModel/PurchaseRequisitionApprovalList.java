package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PurchaseRequisitionApprovalList extends RealmObject {
    @SerializedName("purchase_req_id")
    @Expose
    private Integer purchaseReqId;
    @SerializedName("purchase_req_number")
    @Expose
    private String purchaseReqNumber;
    @SerializedName("purchase_req_date")
    @Expose
    private String purchaseReqDate;
    @SerializedName("purchase_req_type")
    @Expose
    private String purchaseReqType;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("purchase_req_status")
    @Expose
    private String purchasereqstatus;

    public Integer getPurchaseReqId() {
        return purchaseReqId;
    }

    public void setPurchaseReqId(Integer purchaseReqId) {
        this.purchaseReqId = purchaseReqId;
    }

    public String getPurchaseReqNumber() {
        return purchaseReqNumber;
    }

    public void setPurchaseReqNumber(String purchaseReqNumber) {
        this.purchaseReqNumber = purchaseReqNumber;
    }

    public String getPurchaseReqDate() {
        return purchaseReqDate;
    }

    public void setPurchaseReqDate(String purchaseReqDate) {
        this.purchaseReqDate = purchaseReqDate;
    }

    public String getPurchaseReqType() {
        return purchaseReqType;
    }

    public void setPurchaseReqType(String purchaseReqType) {
        this.purchaseReqType = purchaseReqType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurchasereqstatus() {
        return purchasereqstatus;
    }

    public void setPurchasereqstatus(String purchasereqstatus) {
        this.purchasereqstatus = purchasereqstatus;
    }
}
