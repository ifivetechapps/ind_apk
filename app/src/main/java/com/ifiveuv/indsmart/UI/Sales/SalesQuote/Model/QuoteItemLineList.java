package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuoteItemLineList extends RealmObject {

    @SerializedName("product_position")
    @Expose
    private int productPosition;

    @SerializedName("product")
    @Expose
    private String product;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @SerializedName("quote_product_id")
    @Expose
    private String productId;

    public int getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(int productPosition) {
        this.productPosition = productPosition;
    }

    @SerializedName("uom")
    @Expose
    private String uom;

    @SerializedName("quote_uom_id")
    @Expose
    private Integer uomId;

    public Integer getQuoteHdrId() {
        return quoteHdrId;
    }

    public void setQuoteHdrId(Integer quoteHdrId) {
        this.quoteHdrId = quoteHdrId;
    }

    public Integer getQuoteLineId() {
        return quoteLineId;
    }

    public void setQuoteLineId(Integer quoteLineId) {
        this.quoteLineId = quoteLineId;
    }

    @SerializedName("quote_Hdr_id")
    @Expose
    private Integer  quoteHdrId;
    @SerializedName("quote_taxid")
    @Expose
    private Integer  quoteTaxId;
    @PrimaryKey
    @SerializedName("quote_line_id")
    @Expose
    private Integer quoteLineId;

    @SerializedName("quote_quantity")
    @Expose
    private String quantity;

    public Integer getQuoteTaxId() {
        return quoteTaxId;
    }

    public void setQuoteTaxId(Integer quoteTaxId) {
        this.quoteTaxId = quoteTaxId;
    }

    public String getQuote_tax() {
        return quote_tax;
    }

    public void setQuote_tax(String quote_tax) {
        this.quote_tax = quote_tax;
    }

    @SerializedName("quote_tax")
    @Expose
    private String quote_tax;

    @SerializedName("quote_unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("quote_total_cost")
    @Expose
    private String lineTotal;

    public String getDisPer() {
        return disPer;
    }

    public void setDisPer(String disPer) {
        this.disPer = disPer;
    }

    public String getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(String disAmt) {
        this.disAmt = disAmt;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    @SerializedName("discount_percent")
    @Expose
    private String disPer;
    @SerializedName("discount_amount")
    @Expose
    private String disAmt;
    @SerializedName("mrp")
    @Expose
    private String mrp;

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}

