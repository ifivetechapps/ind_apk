package com.ifive.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class SubInventory extends RealmObject {

    @SerializedName("sub_inventory_id")
    @Expose
    private Integer subInventoryId;
    @SerializedName("inventory_type_id")
    @Expose
    private Integer inventoryTypeId;
    @SerializedName("sub_inventory_name")
    @Expose
    private String subInventoryName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

    public Integer getSubInventoryId() {
        return subInventoryId;
    }

    public void setSubInventoryId(Integer subInventoryId) {
        this.subInventoryId = subInventoryId;
    }

    public Integer getInventoryTypeId() {
        return inventoryTypeId;
    }

    public void setInventoryTypeId(Integer inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }

    public String getSubInventoryName() {
        return subInventoryName;
    }

    public void setSubInventoryName(String subInventoryName) {
        this.subInventoryName = subInventoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
