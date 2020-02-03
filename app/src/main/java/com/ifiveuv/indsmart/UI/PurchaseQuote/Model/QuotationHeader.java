package com.ifiveuv.indsmart.UI.PurchaseQuote.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuotationHeader extends RealmObject {
    public int getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(int quote_id) {
        this.quote_id = quote_id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierSite() {
        return supplierSite;
    }

    public void setSupplierSite(String supplierSite) {
        this.supplierSite = supplierSite;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public String getFreight_carrier() {
        return freight_carrier;
    }

    public void setFreight_carrier(String freight_carrier) {
        this.freight_carrier = freight_carrier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrandTax() {
        return grandTax;
    }

    public void setGrandTax(String grandTax) {
        this.grandTax = grandTax;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    @PrimaryKey
    @SerializedName("quote_id")
    @Expose
    private int quote_id;
    @SerializedName("SupplierName")
    @Expose
    private String supplierName;
    @SerializedName("SupplierSite")
    @Expose
    private String supplierSite;
    @SerializedName("SupplierId")
    @Expose
    private int supplierId;
    @SerializedName("quote_date")
    @Expose
    private String quoteDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("quote_type")
    @Expose
    private String quoteType;
    @SerializedName("freight_carrier")
    @Expose
    private String freight_carrier;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tax")
    @Expose
    private String grandTax;
    @SerializedName("total")
    @Expose
    private String grandTotal;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("QuotationLines")
    @Expose
    private RealmList<QuotationLines> quotationLines;

    public RealmList<QuotationLines> getQuotationLines() {
        return quotationLines;
    }

    public void setQuotationLines(RealmList<QuotationLines> quotationLines) {
        this.quotationLines = quotationLines;
    }
}
