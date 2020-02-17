package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PurchaseEnquiryLinesT extends RealmObject {
    @SerializedName("enquiry_line_id")
    @Expose
    private Integer enquiryLineId;
    @SerializedName("enquiry_hdr_id")
    @Expose
    private Integer enquiryHdrId;
    @SerializedName("line_no")
    @Expose
    private Integer lineNo;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("part_no")
    @Expose
    private Integer partNo;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("uom_code_id")
    @Expose
    private Integer uomCodeId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("promised_date")
    @Expose
    private String promisedDate;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("organization_id")
    @Expose
    private Integer organizationId;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("last_updated_by")
    @Expose
    private Integer lastUpdatedBy;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getEnquiryLineId() {
        return enquiryLineId;
    }

    public void setEnquiryLineId(Integer enquiryLineId) {
        this.enquiryLineId = enquiryLineId;
    }

    public Integer getEnquiryHdrId() {
        return enquiryHdrId;
    }

    public void setEnquiryHdrId(Integer enquiryHdrId) {
        this.enquiryHdrId = enquiryHdrId;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getPartNo() {
        return partNo;
    }

    public void setPartNo(Integer partNo) {
        this.partNo = partNo;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getUomCodeId() {
        return uomCodeId;
    }

    public void setUomCodeId(Integer uomCodeId) {
        this.uomCodeId = uomCodeId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPromisedDate() {
        return promisedDate;
    }

    public void setPromisedDate(String promisedDate) {
        this.promisedDate = promisedDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
