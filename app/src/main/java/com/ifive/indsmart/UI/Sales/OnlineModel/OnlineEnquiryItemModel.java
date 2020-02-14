package com.ifive.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class OnlineEnquiryItemModel extends RealmObject {
    @SerializedName("sales_enquiry_hdr_id")
    @Expose
    private Integer salesEnquiryHdrId;
    @SerializedName("sales_enquiry_no")
    @Expose
    private String salesEnquiryNo;
    @SerializedName("sales_enquiry_date")
    @Expose
    private String salesEnquiryDate;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
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
    @SerializedName("so_status")
    @Expose
    private Integer soStatus;
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
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public Integer getSalesEnquiryHdrId() {
        return salesEnquiryHdrId;
    }

    public void setSalesEnquiryHdrId(Integer salesEnquiryHdrId) {
        this.salesEnquiryHdrId = salesEnquiryHdrId;
    }

    public String getSalesEnquiryNo() {
        return salesEnquiryNo;
    }

    public void setSalesEnquiryNo(String salesEnquiryNo) {
        this.salesEnquiryNo = salesEnquiryNo;
    }

    public String getSalesEnquiryDate() {
        return salesEnquiryDate;
    }

    public void setSalesEnquiryDate(String salesEnquiryDate) {
        this.salesEnquiryDate = salesEnquiryDate;
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

    public Integer getSoStatus() {
        return soStatus;
    }

    public void setSoStatus(Integer soStatus) {
        this.soStatus = soStatus;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
