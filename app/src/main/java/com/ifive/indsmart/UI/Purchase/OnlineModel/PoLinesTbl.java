package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PoLinesTbl extends RealmObject {
    @SerializedName("po_lines_id")
    @Expose
    private Integer poLinesId;
    @SerializedName("purchase_id")
    @Expose
    private Integer purchaseId;
    @SerializedName("products_name")
    @Expose
    private Integer productsName;
    @SerializedName("package_size")
    @Expose
    private Integer packageSize;
    @SerializedName("po_uom")
    @Expose
    private Integer poUom;
    @SerializedName("order_qty")
    @Expose
    private Integer orderQty;
    @SerializedName("po_cost")
    @Expose
    private Integer poCost;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("tax")
    @Expose
    private String tax;
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
    @SerializedName("job_order_status")
    @Expose
    private Integer jobOrderStatus;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

    public Integer getPoLinesId() {
        return poLinesId;
    }

    public void setPoLinesId(Integer poLinesId) {
        this.poLinesId = poLinesId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getProductsName() {
        return productsName;
    }

    public void setProductsName(Integer productsName) {
        this.productsName = productsName;
    }

    public Integer getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(Integer packageSize) {
        this.packageSize = packageSize;
    }

    public Integer getPoUom() {
        return poUom;
    }

    public void setPoUom(Integer poUom) {
        this.poUom = poUom;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getPoCost() {
        return poCost;
    }

    public void setPoCost(Integer poCost) {
        this.poCost = poCost;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
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

    public Integer getJobOrderStatus() {
        return jobOrderStatus;
    }

    public void setJobOrderStatus(Integer jobOrderStatus) {
        this.jobOrderStatus = jobOrderStatus;
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
