package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

import io.realm.RealmObject;

public class PurchaseTbl extends RealmObject {
    @SerializedName("purchase_id")
    @Expose
    private Integer purchaseId;
    @SerializedName("po_number")
    @Expose
    private String poNumber;
    @SerializedName("purchase_description")
    @Expose
    private String purchaseDescription;
    @SerializedName("po_date")
    @Expose
    private String poDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("tax_type")
    @Expose
    private Integer taxType;
    @SerializedName("tax_amount")
    @Expose
    private Float taxAmount;
    @SerializedName("taxable_amount")
    @Expose
    private Integer taxableAmount;
    @SerializedName("grand_total")
    @Expose
    private Float grandTotal;
    @SerializedName("po_order_status")
    @Expose
    private Integer poOrderStatus;
    @SerializedName("sales_reference")
    @Expose
    private String salesReference;
    @SerializedName("po_supplier_address")
    @Expose
    private String poSupplierAddress;
    @SerializedName("po_supplier_no")
    @Expose
    private Double poSupplierNo;
    @SerializedName("iventory_status")
    @Expose
    private Integer iventoryStatus;
    @SerializedName("vat_account")
    @Expose
    private Integer vatAccount;
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
    @SerializedName("gin_grn_status")
    @Expose
    private Integer ginGrnStatus;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(Integer taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getPoOrderStatus() {
        return poOrderStatus;
    }

    public void setPoOrderStatus(Integer poOrderStatus) {
        this.poOrderStatus = poOrderStatus;
    }

    public String getSalesReference() {
        return salesReference;
    }

    public void setSalesReference(String salesReference) {
        this.salesReference = salesReference;
    }

    public String getPoSupplierAddress() {
        return poSupplierAddress;
    }

    public void setPoSupplierAddress(String poSupplierAddress) {
        this.poSupplierAddress = poSupplierAddress;
    }

    public Double getPoSupplierNo() {
        return poSupplierNo;
    }

    public void setPoSupplierNo(Double poSupplierNo) {
        this.poSupplierNo = poSupplierNo;
    }

    public Integer getIventoryStatus() {
        return iventoryStatus;
    }

    public void setIventoryStatus(Integer iventoryStatus) {
        this.iventoryStatus = iventoryStatus;
    }

    public Integer getVatAccount() {
        return vatAccount;
    }

    public void setVatAccount(Integer vatAccount) {
        this.vatAccount = vatAccount;
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

    public Integer getGinGrnStatus() {
        return ginGrnStatus;
    }

    public void setGinGrnStatus(Integer ginGrnStatus) {
        this.ginGrnStatus = ginGrnStatus;
    }
}
