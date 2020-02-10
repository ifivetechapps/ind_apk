package com.ifiveuv.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SoHeaderquoteDetail  extends RealmObject {

    @SerializedName("sales_quote_id")
    @Expose
    private Integer salesQuoteId;
    @SerializedName("se_id")
    @Expose
    private String seId;
    @SerializedName("sales_quote_no")
    @Expose
    private String salesQuoteNo;
    @SerializedName("sales_quote_date")
    @Expose
    private String salesQuoteDate;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("approve_status")
    @Expose
    private String approveStatus;
    @SerializedName("sales_quote_tbl")
    @Expose
    private String salesQuoteTbl;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("tax_value")
    @Expose
    private String taxValue;
    @SerializedName("gross_amount")
    @Expose
    private Float grossAmount;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("total_tax")
    @Expose
    private Integer totalTax;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("round_off")
    @Expose
    private String roundOff;
    @SerializedName("quote_status")
    @Expose
    private Integer quoteStatus;
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

    public Integer getSalesQuoteId() {
        return salesQuoteId;
    }

    public void setSalesQuoteId(Integer salesQuoteId) {
        this.salesQuoteId = salesQuoteId;
    }

    public String getSeId() {
        return seId;
    }

    public void setSeId(String seId) {
        this.seId = seId;
    }

    public String getSalesQuoteNo() {
        return salesQuoteNo;
    }

    public void setSalesQuoteNo(String salesQuoteNo) {
        this.salesQuoteNo = salesQuoteNo;
    }

    public String getSalesQuoteDate() {
        return salesQuoteDate;
    }

    public void setSalesQuoteDate(String salesQuoteDate) {
        this.salesQuoteDate = salesQuoteDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getSalesQuoteTbl() {
        return salesQuoteTbl;
    }

    public void setSalesQuoteTbl(String salesQuoteTbl) {
        this.salesQuoteTbl = salesQuoteTbl;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

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

    public Float getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Float grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Integer totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRoundOff() {
        return roundOff;
    }

    public void setRoundOff(String roundOff) {
        this.roundOff = roundOff;
    }

    public Integer getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(Integer quoteStatus) {
        this.quoteStatus = quoteStatus;
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
