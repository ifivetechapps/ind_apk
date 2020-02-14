package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Products extends RealmObject {

    @SerializedName("product_id")
    @Expose
    private Integer pro_id;
    @SerializedName("product_group_id")
    @Expose
    private int product_group_id;
    @SerializedName("product_category_id")
    @Expose
    private int product_category_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_uom")
    @Expose
    private int uom_id;

    @SerializedName("hsn_id")
    @Expose
    private int hsn_id;

    public int getTax_group_id() {
        return tax_group_id;
    }

    public void setTax_group_id(int tax_group_id) {
        this.tax_group_id = tax_group_id;
    }

    public String getTax_group_name() {
        return tax_group_name;
    }

    public void setTax_group_name(String tax_group_name) {
        this.tax_group_name = tax_group_name;
    }

    @SerializedName("tax_group_id")
    @Expose
    private int tax_group_id;
    @SerializedName("tax_group_name")
    @Expose
    private String tax_group_name;
    @SerializedName("product_description")
    @Expose
    private String description;
    @SerializedName("item_price")
    @Expose
    private String itemPrice;
    @SerializedName("bom_status")
    @Expose
    private String bomStatus;

    public Integer getPro_id() {
        return pro_id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public int getProduct_group_id() {
        return product_group_id;
    }

    public void setProduct_group_id(int product_group_id) {
        this.product_group_id = product_group_id;
    }

    public int getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(int product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getUom_id() {
        return uom_id;
    }

    public void setUom_id(int uom_id) {
        this.uom_id = uom_id;
    }

    public int getHsn_id() {
        return hsn_id;
    }

    public void setHsn_id(int hsn_id) {
        this.hsn_id = hsn_id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getBomStatus() {
        return bomStatus;
    }

    public void setBomStatus(String bomStatus) {
        this.bomStatus = bomStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getPurchaseUom() {
        return purchaseUom;
    }

    public void setPurchaseUom(Integer purchaseUom) {
        this.purchaseUom = purchaseUom;
    }

    public Integer getInventoryUom() {
        return inventoryUom;
    }

    public void setInventoryUom(Integer inventoryUom) {
        this.inventoryUom = inventoryUom;
    }

    public Integer getIssueOrder() {
        return issueOrder;
    }

    public void setIssueOrder(Integer issueOrder) {
        this.issueOrder = issueOrder;
    }

    public Integer getFgName() {
        return fgName;
    }

    public void setFgName(Integer fgName) {
        this.fgName = fgName;
    }

    public Integer getPackingTypeId() {
        return packingTypeId;
    }

    public void setPackingTypeId(Integer packingTypeId) {
        this.packingTypeId = packingTypeId;
    }

    public Integer getPackingSizeId() {
        return packingSizeId;
    }

    public void setPackingSizeId(Integer packingSizeId) {
        this.packingSizeId = packingSizeId;
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

    @SerializedName("product_code")
    @Expose
    private String productCode;

    @SerializedName("purchase_uom")
    @Expose
    private Integer purchaseUom;
    @SerializedName("inventory_uom")
    @Expose
    private Integer inventoryUom;
    @SerializedName("issue_order")
    @Expose
    private Integer issueOrder;
    @SerializedName("fg_name")
    @Expose
    private Integer fgName;
    @SerializedName("packing_type_id")
    @Expose
    private Integer packingTypeId;
    @SerializedName("packing_size_id")
    @Expose
    private Integer packingSizeId;
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
