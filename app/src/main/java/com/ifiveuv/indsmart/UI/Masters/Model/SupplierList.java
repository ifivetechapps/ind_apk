package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SupplierList extends RealmObject {

    public Integer getSupplierTblId() {
        return supplierTblId;
    }

    public void setSupplierTblId(Integer supplierTblId) {
        this.supplierTblId = supplierTblId;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getOverDue() {
        return overDue;
    }

    public void setOverDue(Integer overDue) {
        this.overDue = overDue;
    }

    public Integer getExciseDuty() {
        return exciseDuty;
    }

    public void setExciseDuty(Integer exciseDuty) {
        this.exciseDuty = exciseDuty;
    }

    public Integer getDefaultBank() {
        return defaultBank;
    }

    public void setDefaultBank(Integer defaultBank) {
        this.defaultBank = defaultBank;
    }

    public Integer getSupplierTin() {
        return supplierTin;
    }

    public void setSupplierTin(Integer supplierTin) {
        this.supplierTin = supplierTin;
    }

    public Integer getSupplierPan() {
        return supplierPan;
    }

    public void setSupplierPan(Integer supplierPan) {
        this.supplierPan = supplierPan;
    }

    public Integer getSupplierTds() {
        return supplierTds;
    }

    public void setSupplierTds(Integer supplierTds) {
        this.supplierTds = supplierTds;
    }

    public Integer getSupplierEcc() {
        return supplierEcc;
    }

    public void setSupplierEcc(Integer supplierEcc) {
        this.supplierEcc = supplierEcc;
    }

    public String getServiceTaxNo() {
        return serviceTaxNo;
    }

    public void setServiceTaxNo(String serviceTaxNo) {
        this.serviceTaxNo = serviceTaxNo;
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

    @SerializedName("supplier_tbl_id")
    @Expose
    private Integer supplierTblId;
    @SerializedName("supplier_no")
    @Expose
    private String supplierNo;
    @SerializedName("supplier_name")
    @Expose
    private String supplierName;
    @SerializedName("supplier_address")
    @Expose
    private String supplierAddress;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("over_due")
    @Expose
    private Integer overDue;
    @SerializedName("excise_duty")
    @Expose
    private Integer exciseDuty;
    @SerializedName("default_bank")
    @Expose
    private Integer defaultBank;
    @SerializedName("supplier_tin")
    @Expose
    private Integer supplierTin;
    @SerializedName("supplier_pan")
    @Expose
    private Integer supplierPan;
    @SerializedName("supplier_tds")
    @Expose
    private Integer supplierTds;
    @SerializedName("supplier_ecc")
    @Expose
    private Integer supplierEcc;
    @SerializedName("service_tax_no")
    @Expose
    private String serviceTaxNo;
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

}
