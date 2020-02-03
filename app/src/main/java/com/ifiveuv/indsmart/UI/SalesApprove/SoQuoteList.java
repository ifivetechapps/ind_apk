package com.ifiveuv.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoQuoteList {

    @SerializedName("sales_quote_id")
    @Expose
    private Integer salesQuoteId;
    @SerializedName("sales_quote_no")
    @Expose
    private String salesQuoteNo;
    @SerializedName("sales_quote_date")
    @Expose
    private String salesQuoteDate;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("total_amount")
    @Expose
    private String total;

    public Integer getSalesQuoteId() {
        return salesQuoteId;
    }

    public void setSalesQuoteId(Integer salesQuoteId) {
        this.salesQuoteId = salesQuoteId;
    }

    public String getSalesQuoteNo() {
        return salesQuoteNo;
    }

    public void setSalesQuoteNo(String salesQuoteNo) {
        this.salesQuoteNo = salesQuoteNo;
    }

    public String getSalesQuoteDate() {
        return salesQuoteDate;
    }

    public void setSalesQuoteDate(String salesQuoteDate) {
        this.salesQuoteDate = salesQuoteDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
