package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PurchaseRequisitionTbl extends RealmObject {

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
    @SerializedName("purchase_req_status")
    @Expose
    private Integer purchaseReqStatus;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("purchase_req_description")
    @Expose
    private String purchaseReqDescription;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

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

    public Integer getPurchaseReqStatus() {
        return purchaseReqStatus;
    }

    public void setPurchaseReqStatus(Integer purchaseReqStatus) {
        this.purchaseReqStatus = purchaseReqStatus;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseReqDescription() {
        return purchaseReqDescription;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Integer getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(Integer cmpyId) {
        this.cmpyId = cmpyId;
    }

}
