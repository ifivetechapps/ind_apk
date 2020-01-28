package com.ifiveuv.indsmart.UI.SalesCreate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SalesItemLineList extends RealmObject {
    @SerializedName("product_position")
    @Expose
    private int productPosition;

    @SerializedName("product")
    @Expose
    private String product;


    @SerializedName("so_product_id")
    @Expose
    private String productId;

    @SerializedName("uom")
    @Expose
    private String uom;

    @SerializedName("so_uom_id")
    @Expose
    private Integer uomId;
    @SerializedName("so_quantity")
    @Expose
    private Integer quantity;
    @SerializedName("so_unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("discount_percent")
    @Expose
    private String disPer;
    @SerializedName("discount_amount")
    @Expose
    private String disAmt;
    @SerializedName("orginal_cost")
    @Expose
    private String orgCost;
    @SerializedName("so_total_cost")
    @Expose
    private String lineTotal;

    public String getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(String invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public String getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(String invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public String getInvoiceDis() {
        return invoiceDis;
    }

    public void setInvoiceDis(String invoiceDis) {
        this.invoiceDis = invoiceDis;
    }

    @SerializedName("Invoice_qty")
    @Expose
    private String invoiceQty;
    @SerializedName("Invoice_amt")
    @Expose
    private String invoiceAmt;
    @SerializedName("Invoice_dis")
    @Expose
    private String invoiceDis;
    @SerializedName("Invoice_dis_per")
    @Expose
    private String invoiceDisPer;
    @SerializedName("Invoice_total")
    @Expose
    private String invoiceTotal;
    public String getInvoiceDisPer() {
        return invoiceDisPer;
    }

    public void setInvoiceDisPer(String invoiceDisPer) {
        this.invoiceDisPer = invoiceDisPer;
    }


    public String getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(String invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
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

    public String getOrgCost() {
        return orgCost;
    }

    public void setOrgCost(String orgCost) {
        this.orgCost = orgCost;
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


    public int getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(int productPosition) {
        this.productPosition = productPosition;
    }



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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}