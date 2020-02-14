package com.ifive.indsmart.UI.Sales.SalesEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

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


    public int getEnqLineId() {
        return enqLineId;
    }

    public void setEnqLineId(int enqLineId) {
        this.enqLineId = enqLineId;
    }


    public int getEnquiryHdrId() {
        return enquiryHdrId;
    }

    public void setEnquiryHdrId(int enquiryHdrId) {
        this.enquiryHdrId = enquiryHdrId;
    }


    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

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


    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }
    @PrimaryKey
    @SerializedName("enq_id")
    @Expose
    private int enqLineId;

    @SerializedName("enquiry_header_id")
    @Expose
    private int enquiryHdrId;
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
    @SerializedName("tax_id")
    @Expose
    private Integer taxId;



    @SerializedName("tax_amt")
    @Expose
    private String taxAmt;
    @SerializedName("enquiry_quantity")
    @Expose
    private String enquiryRequiredQuantity;

}
