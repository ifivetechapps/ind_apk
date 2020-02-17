package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PurchaseRequisitionDetailsTbl extends RealmObject {
    @SerializedName("purchase_req_details_id")
    @Expose
    private Integer purchaseReqDetailsId;
    @SerializedName("purchase_req_id")
    @Expose
    private Integer purchaseReqId;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("required_date")
    @Expose
    private String requiredDate;
    @SerializedName("required_uom")
    @Expose
    private String requiredUom;
    @SerializedName("required_quantity")
    @Expose
    private String requiredQuantity;
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

    public Integer getPurchaseReqDetailsId() {
        return purchaseReqDetailsId;
    }

    public void setPurchaseReqDetailsId(Integer purchaseReqDetailsId) {
        this.purchaseReqDetailsId = purchaseReqDetailsId;
    }

    public Integer getPurchaseReqId() {
        return purchaseReqId;
    }

    public void setPurchaseReqId(Integer purchaseReqId) {
        this.purchaseReqId = purchaseReqId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getRequiredUom() {
        return requiredUom;
    }

    public void setRequiredUom(String requiredUom) {
        this.requiredUom = requiredUom;
    }

    public String getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(String requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
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
