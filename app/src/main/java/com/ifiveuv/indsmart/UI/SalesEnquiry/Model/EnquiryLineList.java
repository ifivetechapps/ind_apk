package com.ifiveuv.indsmart.UI.SalesEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class EnquiryLineList extends RealmObject {

    public int getEnquiryProductPosition() {
        return enquiryProductPosition;
    }

    public void setEnquiryProductPosition(int enquiryProductPosition) {
        this.enquiryProductPosition = enquiryProductPosition;
    }

    public String getEnquiryProduct() {
        return enquiryProduct;
    }

    public void setEnquiryProduct(String enquiryProduct) {
        this.enquiryProduct = enquiryProduct;
    }

    public String getEnquiryUom() {
        return enquiryUom;
    }

    public void setEnquiryUom(String enquiryUom) {
        this.enquiryUom = enquiryUom;
    }

    public Integer getEnquiryUomId() {
        return enquiryUomId;
    }

    public void setEnquiryUomId(Integer enquiryUomId) {
        this.enquiryUomId = enquiryUomId;
    }

    public String getEnquiryRequiredQuantity() {
        return enquiryRequiredQuantity;
    }

    public void setEnquiryRequiredQuantity(String enquiryRequiredQuantity) {
        this.enquiryRequiredQuantity = enquiryRequiredQuantity;
    }

    public String getEnquiryProductId() {
        return enquiryProductId;
    }

    public void setEnquiryProductId(String enquiryProductId) {
        this.enquiryProductId = enquiryProductId;
    }



    @SerializedName("enquiry_product_position")
    @Expose
    private int enquiryProductPosition;

    @SerializedName("enquiry_product")
    @Expose
    private String enquiryProduct;

    @SerializedName("enquiry_product_id")
    @Expose
    private String enquiryProductId;
    @SerializedName("enquiry_uom")
    @Expose
    private String enquiryUom;
    @SerializedName("enquiry_uom_id")
    @Expose
    private Integer enquiryUomId;
    @SerializedName("enquiry_quantity")
    @Expose
    private String enquiryRequiredQuantity;

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @SerializedName("discount_percent")
    @Expose
    private String discountPercent;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("cost")
    @Expose
    private String originalCost;
    @SerializedName("unit_price_quote")
    @Expose
    private String unitPrice;
    @SerializedName("line_total")
    @Expose
    private String lineTotal;
    @SerializedName("product_id")
    @Expose
    private String productId;

}
