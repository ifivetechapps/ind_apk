package com.ifiveuv.indsmart.UI.Sales.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SoQuoteDetail  extends RealmObject {

    @SerializedName("sales_quote_details_id")
    @Expose
    private Integer salesQuoteDetailsId;
    @SerializedName("sales_quote_id")
    @Expose
    private Integer salesQuoteId;
    @SerializedName("se_id")
    @Expose
    private Integer seId;
    @SerializedName("product_name")
    @Expose
    private Integer productName;
    @SerializedName("order_qty")
    @Expose
    private Integer orderQty;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("tax_amt")
    @Expose
    private String taxAmt;
    @SerializedName("packing_size_id")
    @Expose
    private String packingSizeId;
    @SerializedName("packing_type_id")
    @Expose
    private String packingTypeId;
    @SerializedName("uom_id")
    @Expose
    private Integer uomId;
    @SerializedName("unit_selling_price")
    @Expose
    private Integer unitSellingPrice;
    @SerializedName("discount_per")
    @Expose
    private Integer discountPer;
    @SerializedName("discount_amount")
    @Expose
    private Float discountAmount;
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

    public Integer getSalesQuoteDetailsId() {
        return salesQuoteDetailsId;
    }

    public void setSalesQuoteDetailsId(Integer salesQuoteDetailsId) {
        this.salesQuoteDetailsId = salesQuoteDetailsId;
    }

    public Integer getSalesQuoteId() {
        return salesQuoteId;
    }

    public void setSalesQuoteId(Integer salesQuoteId) {
        this.salesQuoteId = salesQuoteId;
    }

    public Integer getSeId() {
        return seId;
    }

    public void setSeId(Integer seId) {
        this.seId = seId;
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

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
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

    public Integer getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(Integer unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public Integer getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Integer discountPer) {
        this.discountPer = discountPer;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
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
}
