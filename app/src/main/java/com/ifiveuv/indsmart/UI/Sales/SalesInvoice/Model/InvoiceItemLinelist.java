package com.ifiveuv.indsmart.UI.Sales.SalesInvoice.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class InvoiceItemLinelist extends RealmObject {

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getUomID() {
        return uomID;
    }

    public void setUomID(int uomID) {
        this.uomID = uomID;
    }

    public String getOrdqty() {
        return ordqty;
    }

    public void setOrdqty(String ordqty) {
        this.ordqty = ordqty;
    }

    public String getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(String unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public String getInvqty() {
        return invqty;
    }

    public void setInvqty(String invqty) {
        this.invqty = invqty;
    }

    public String getInvoiceDisPer() {
        return invoiceDisPer;
    }

    public void setInvoiceDisPer(String invoiceDisPer) {
        this.invoiceDisPer = invoiceDisPer;
    }

    public String getInvoiceDis() {
        return invoiceDis;
    }

    public void setInvoiceDis(String invoiceDis) {
        this.invoiceDis = invoiceDis;
    }

    public String getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(String invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public String getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(String invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    @SerializedName("product")
    @Expose
    private String product;

    @SerializedName("inv_product_id")
    @Expose
    private String productId;

    @SerializedName("uom")
    @Expose
    private String uom;

    @SerializedName("inv_uom_id")
    @Expose
    private int uomID;

    @SerializedName("ordqty")
    @Expose
    private String ordqty;

    @SerializedName("inv_unit_price")
    @Expose
    private String unitSellingPrice;

    @SerializedName("inv_quantity")
    @Expose
    private String invqty;

    @SerializedName("discount_percentage")
    @Expose
    private String invoiceDisPer;

    @SerializedName("invoice_discount_amount")
    @Expose
    private String invoiceDis;

    @SerializedName("invoice_amount")
    @Expose
    private String invoiceAmt;

    @SerializedName("invoice_total_cost")
    @Expose
    private String invoiceTotal;


}
