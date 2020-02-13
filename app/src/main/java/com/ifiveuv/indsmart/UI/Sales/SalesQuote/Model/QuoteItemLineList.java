package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuoteItemLineList extends RealmObject {

    @SerializedName("product_position")
    @Expose
    private int productPosition;

    @SerializedName("quote_product_id")
    @Expose
    private Integer productId;

    @SerializedName("quote_uom_id")
    @Expose
    private Integer uomId;


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

    @SerializedName("discount_percent")
    @Expose
    private String disPer;
    @SerializedName("discount_amount")
    @Expose
    private String disAmt;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("quote_tax")
    @Expose
    private String quote_tax;
    @SerializedName("quote_tax_amt")
    @Expose
    private String quote_taxAmt;

    @SerializedName("quote_unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("quote_total_cost")
    @Expose
    private String lineTotal;


    public int getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(int productPosition) {
        this.productPosition = productPosition;
    }


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


    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
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

    public String getQuote_taxAmt() {
        return quote_taxAmt;
    }

    public void setQuote_taxAmt(String quote_taxAmt) {
        this.quote_taxAmt = quote_taxAmt;
    }

    public Integer  getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}

