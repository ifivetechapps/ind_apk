package com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class EnquiryItemList extends RealmObject {
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOrdQty() {
        return ordQty;
    }

    public void setOrdQty(String ordQty) {
        this.ordQty = ordQty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPromised_date() {
        return promised_date;
    }

    public void setPromised_date(String promised_date) {
        this.promised_date = promised_date;
    }

    @SerializedName("product")
    @Expose
    private String product;

    @SerializedName("ord_qty")
    @Expose
    private String ordQty;

    @SerializedName("uom")
    @Expose
    private String uom;

    public Integer getUom_id() {
        return uom_id;
    }

    public void setUom_id(Integer uom_id) {
        this.uom_id = uom_id;
    }

    @SerializedName("uom_id")
    @Expose
    private Integer uom_id;


    @SerializedName("promised_date")
    @Expose
    private String promised_date;


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

    public String getTaxtotal() {
        return taxtotal;
    }

    public void setTaxtotal(String taxtotal) {
        this.taxtotal = taxtotal;
    }

    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getHsnId() {
        return hsnId;
    }

    public void setHsnId(Integer hsnId) {
        this.hsnId = hsnId;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("discount_amt")
    @Expose
    private String discountAmt;
    @SerializedName("hsn_code")
    @Expose
    private String hsnCode;
    @SerializedName("tax_total")
    @Expose
    private String taxtotal;
    @SerializedName("line_total")
    @Expose
    private String lineTotal;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("hsn_id")
    @Expose
    private Integer hsnId;
    @SerializedName("tax_id")
    @Expose
    private int taxId;

    public int getProductPostion() {
        return productPostion;
    }

    public void setProductPostion(int productPostion) {
        this.productPostion = productPostion;
    }

    @SerializedName("product_position")
    @Expose
    private int productPostion;
    @SerializedName("tax_group")
    @Expose
    private String taxGroup;
}
