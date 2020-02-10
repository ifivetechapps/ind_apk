package com.ifiveuv.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import io.realm.RealmObject;

public class SoDetailTable extends RealmObject {
    @SerializedName("sales_order_details_id")
    @Expose
    private Integer salesOrderDetailsId;
    @SerializedName("sales_order_hdr_id")
    @Expose
    private Integer salesOrderHdrId;
    @SerializedName("product_name")
    @Expose
    private Integer productName;
    @SerializedName("order_qty")
    @Expose
    private Integer orderQty;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("packing_size_id")
    @Expose
    private String packingSizeId;
    @SerializedName("packing_type_id")
    @Expose
    private String packingTypeId;
    @SerializedName("uom_id")
    @Expose
    private Integer uomId;
    @SerializedName("discount_per")
    @Expose
    private Integer discountPer;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("unit_selling_price")
    @Expose
    private Integer unitSellingPrice;
    @SerializedName("total_cost")
    @Expose
    private Integer totalCost;
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
    @SerializedName("plan_status")
    @Expose
    private Integer planStatus;

    public Integer getSalesOrderDetailsId() {
        return salesOrderDetailsId;
    }

    public void setSalesOrderDetailsId(Integer salesOrderDetailsId) {
        this.salesOrderDetailsId = salesOrderDetailsId;
    }

    public Integer getSalesOrderHdrId() {
        return salesOrderHdrId;
    }

    public void setSalesOrderHdrId(Integer salesOrderHdrId) {
        this.salesOrderHdrId = salesOrderHdrId;
    }

    public Integer getProductName() {
        return productName;
    }

    public void setProductName(Integer productName) {
        this.productName = productName;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public String getPackingSizeId() {
        return packingSizeId;
    }

    public void setPackingSizeId(String packingSizeId) {
        this.packingSizeId = packingSizeId;
    }

    public String getPackingTypeId() {
        return packingTypeId;
    }

    public void setPackingTypeId(String packingTypeId) {
        this.packingTypeId = packingTypeId;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public Integer getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Integer discountPer) {
        this.discountPer = discountPer;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(Integer unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
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

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

}
