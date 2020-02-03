package com.ifiveuv.indsmart.UI.PurchaseQuote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class QuotationLines extends RealmObject {
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public String getQuoteQty() {
        return quoteQty;
    }

    public void setQuoteQty(String quoteQty) {
        this.quoteQty = quoteQty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }


    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getPromisedDate() {
        return promisedDate;
    }

    public void setPromisedDate(String promisedDate) {
        this.promisedDate = promisedDate;
    }

    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("uom_id")
    @Expose
    private Integer uomId;

    public Integer getHsnId() {
        return hsnId;
    }

    public void setHsnId(Integer hsnId) {
        this.hsnId = hsnId;
    }

    @SerializedName("hsn_id")
    @Expose
    private Integer hsnId;
    @SerializedName("quantity")
    @Expose
    private String quoteQty;
    @SerializedName("price")
    @Expose
    private int price;

    public int getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(int productPosition) {
        this.productPosition = productPosition;
    }

    @SerializedName("product_position")
    @Expose
    private int productPosition;
    @SerializedName("discount_amt")
    @Expose
    private String discountAmt;

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    @SerializedName("discount_per")
    @Expose
    private String discountPercent;
    @SerializedName("hsn_code")
    @Expose
    private String hsnCode;

    public String getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    @SerializedName("tax_group")
    @Expose
    private String taxGroup;

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    @SerializedName("tax_id")
    @Expose
    private int taxId;
    @SerializedName("line_total")
    @Expose
    private String lineTotal;

    public String getTaxtotal() {
        return taxtotal;
    }

    public void setTaxtotal(String taxtotal) {
        this.taxtotal = taxtotal;
    }

    @SerializedName("tax_total")
    @Expose
    private String taxtotal;
    @SerializedName("promised_date")
    @Expose
    private String promisedDate;



}
