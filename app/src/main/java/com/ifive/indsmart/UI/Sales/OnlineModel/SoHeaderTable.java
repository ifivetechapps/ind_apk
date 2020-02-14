package com.ifive.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SoHeaderTable extends RealmObject {
    @SerializedName("sales_order_hdr_id")
    @Expose
    private Integer salesOrderHdrId;
    @SerializedName("sales_order_no")
    @Expose
    private String salesOrderNo;
    @SerializedName("sales_quote_no")
    @Expose
    private String salesQuoteNo;
    @SerializedName("sales_enquiry_no")
    @Expose
    private String salesEnquiryNo;
    @SerializedName("sales_order_date")
    @Expose
    private String salesOrderDate;
    @SerializedName("sales_order_status")
    @Expose
    private Integer salesOrderStatus;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("approve_status")
    @Expose
    private String approveStatus;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("tax_value")
    @Expose
    private String taxValue;
    @SerializedName("vat_account")
    @Expose
    private Integer vatAccount;
    @SerializedName("gross_amount")
    @Expose
    private Integer grossAmount;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("round_off")
    @Expose
    private String roundOff;
    @SerializedName("so_status")
    @Expose
    private Integer soStatus;
    @SerializedName("production_plan_status")
    @Expose
    private Integer productionPlanStatus;
    @SerializedName("ready_to_ship")
    @Expose
    private Integer readyToShip;
    @SerializedName("pay_recieve_status")
    @Expose
    private Integer payRecieveStatus;
    @SerializedName("advance_status")
    @Expose
    private Integer advanceStatus;
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

    public String getSalesQuoteNo() {
        return salesQuoteNo;
    }

    public void setSalesQuoteNo(String salesQuoteNo) {
        this.salesQuoteNo = salesQuoteNo;
    }

    public String getSalesEnquiryNo() {
        return salesEnquiryNo;
    }

    public void setSalesEnquiryNo(String salesEnquiryNo) {
        this.salesEnquiryNo = salesEnquiryNo;
    }

    public String getSalesOrderDate() {
        return salesOrderDate;
    }

    public void setSalesOrderDate(String salesOrderDate) {
        this.salesOrderDate = salesOrderDate;
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
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

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
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

    public Integer getVatAccount() {
        return vatAccount;
    }

    public void setVatAccount(Integer vatAccount) {
        this.vatAccount = vatAccount;
    }

    public Integer getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Integer grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRoundOff() {
        return roundOff;
    }

    public void setRoundOff(String roundOff) {
        this.roundOff = roundOff;
    }

    public Integer getSoStatus() {
        return soStatus;
    }

    public void setSoStatus(Integer soStatus) {
        this.soStatus = soStatus;
    }

    public Integer getProductionPlanStatus() {
        return productionPlanStatus;
    }

    public void setProductionPlanStatus(Integer productionPlanStatus) {
        this.productionPlanStatus = productionPlanStatus;
    }

    public Integer getReadyToShip() {
        return readyToShip;
    }

    public void setReadyToShip(Integer readyToShip) {
        this.readyToShip = readyToShip;
    }

    public Integer getPayRecieveStatus() {
        return payRecieveStatus;
    }

    public void setPayRecieveStatus(Integer payRecieveStatus) {
        this.payRecieveStatus = payRecieveStatus;
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
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
