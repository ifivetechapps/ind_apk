package com.ifiveuv.indsmart.UI.Sales.SalesQuote.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuoteItemList extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("sales_quote_date")
    @Expose
    private String qodate;
    @SerializedName("qcustomer_name")
    @Expose
    private String qcus_name;
    @SerializedName("seonline_id")
    @Expose
    private String enquiryId;

    public String getOnlineEnquiryId() {
        return onlineEnquiryId;
    }

    public void setOnlineEnquiryId(String onlineEnquiryId) {
        this.onlineEnquiryId = onlineEnquiryId;
    }

    @SerializedName("se_id")
    @Expose
    private String onlineEnquiryId;

    @SerializedName("type_id")
    @Expose
    private String quoteType;
    @SerializedName("customer_id")
    @Expose
    private String qcus_id;
    @SerializedName("status_id")
    @Expose
    private String qstatus;
    @SerializedName("quote_delivery_date")
    @Expose
    private String qdel_date;

    @SerializedName("quote_total_price")
    @Expose
    private String totalPrice;

    public String getNetrice() {
        return netprice;
    }

    public void setNetrice(String netrice) {
        this.netprice = netrice;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    @SerializedName("net_price")
    @Expose
    private String netprice;
    @SerializedName("tax_total")
    @Expose
    private String taxTotal;



    @SerializedName("sales_convertstatus")
    @Expose
    private String convertstatus;
    @SerializedName("Approval_status")
    @Expose
    private String approval_status;

    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("online_id")
    @Expose
    private String onlineId;

    @SerializedName("quote_line_list")
    @Expose
    private RealmList<QuoteItemLineList> quoteItemLines = null;

    public Integer getQuoteItemlist() {
        return id;
    }

    public void setQuoteItemlist(Integer quoteItemlist) {
        this.id = quoteItemlist;
    }

    public String getQodate() {
        return qodate;
    }

    public void setQodate(String qodate) {
        this.qodate = qodate;
    }

    public String getQcus_name() {
        return qcus_name;
    }

    public void setQcus_name(String qcus_name) {
        this.qcus_name = qcus_name;
    }

    public String getQstatus() {
        return qstatus;
    }

    public void setQstatus(String qstatus) {
        this.qstatus = qstatus;
    }

    public String getQdel_date() {
        return qdel_date;
    }

    public void setQdel_date(String qdel_date) {
        this.qdel_date = qdel_date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RealmList<QuoteItemLineList> getQuoteItemLines() {
        return quoteItemLines;
    }

    public void setQuoteItemLines(RealmList<QuoteItemLineList> quoteItemLines) {
        this.quoteItemLines = quoteItemLines;
    }



    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }


    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }



    public String getQcus_id() {
        return qcus_id;
    }

    public void setQcus_id(String qcus_id) {
        this.qcus_id = qcus_id;
    }



    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


    public String getConvertstatus() {
        return convertstatus;
    }

    public void setConvertstatus(String convertstatus) {
        this.convertstatus = convertstatus;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(String onlineId) {
        this.onlineId = onlineId;
    }
}

