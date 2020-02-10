package com.ifiveuv.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OnlineEnquiryLineList extends RealmObject {
    @SerializedName("sales_enquiry_details_id")
    @Expose
    private Integer salesEnquiryDetailsId;
    @SerializedName("sales_enquiry_hdr_id")
    @Expose
    private Integer salesEnquiryHdrId;
    @SerializedName("product_name")
    @Expose
    private Integer productName;
    @SerializedName("order_qty")
    @Expose
    private Integer orderQty;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("uom_id")
    @Expose
    private Integer uomId;
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
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

    public Integer getSalesEnquiryDetailsId() {
        return salesEnquiryDetailsId;
    }

    public void setSalesEnquiryDetailsId(Integer salesEnquiryDetailsId) {
        this.salesEnquiryDetailsId = salesEnquiryDetailsId;
    }

    public Integer getSalesEnquiryHdrId() {
        return salesEnquiryHdrId;
    }

    public void setSalesEnquiryHdrId(Integer salesEnquiryHdrId) {
        this.salesEnquiryHdrId = salesEnquiryHdrId;
    }

    public Integer getProductName() {
        return productName;
    }

    public void setProductName(Integer productName) {
        this.productName = productName;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
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
