package com.ifiveuv.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Tax_type extends RealmObject {

    public Integer getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(Integer taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public String getTaxGroupName() {
        return taxGroupName;
    }

    public void setTaxGroupName(String taxGroupName) {
        this.taxGroupName = taxGroupName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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

    @SerializedName("tax_group_id")
    @Expose
    private Integer taxGroupId;
    @SerializedName("tax_group_name")
    @Expose
    private String taxGroupName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("last_updated_by")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    @SerializedName("organization_id")
    @Expose
    private Integer organizationId;
}