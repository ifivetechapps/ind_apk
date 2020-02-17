package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PurchaseEnquiryHdrT extends RealmObject {

    @SerializedName("enquiry_hdr_id")
    @Expose
    private Integer enquiryHdrId;
    @SerializedName("enquiry_number")
    @Expose
    private String enquiryNumber;
    @SerializedName("poenq_count")
    @Expose
    private Integer poenqCount;
    @SerializedName("enquiry_date")
    @Expose
    private String enquiryDate;
    @SerializedName("enquiry_type_id")
    @Expose
    private String enquiryTypeId;
    @SerializedName("enquiry_type")
    @Expose
    private String enquiryType;
    @SerializedName("enquiry_status")
    @Expose
    private String enquiryStatus;
    @SerializedName("supplier_id")
    @Expose
    private Integer supplierId;
    @SerializedName("suppliersite_id")
    @Expose
    private Integer suppliersiteId;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("reference_number")
    @Expose
    private String referenceNumber;
    @SerializedName("reference_id")
    @Expose
    private Integer referenceId;
    @SerializedName("other_info")
    @Expose
    private String otherInfo;
    @SerializedName("attachment_file")
    @Expose
    private String attachmentFile;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
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

    public Integer getEnquiryHdrId() {
        return enquiryHdrId;
    }

    public void setEnquiryHdrId(Integer enquiryHdrId) {
        this.enquiryHdrId = enquiryHdrId;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public Integer getPoenqCount() {
        return poenqCount;
    }

    public void setPoenqCount(Integer poenqCount) {
        this.poenqCount = poenqCount;
    }

    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public String getEnquiryTypeId() {
        return enquiryTypeId;
    }

    public void setEnquiryTypeId(String enquiryTypeId) {
        this.enquiryTypeId = enquiryTypeId;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(String enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSuppliersiteId() {
        return suppliersiteId;
    }

    public void setSuppliersiteId(Integer suppliersiteId) {
        this.suppliersiteId = suppliersiteId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(String attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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
