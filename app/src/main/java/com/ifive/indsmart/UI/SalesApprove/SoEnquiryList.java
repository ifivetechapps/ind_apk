package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoEnquiryList {

    @SerializedName("sales_enquiry_hdr_id")
    @Expose
    private Integer salesEnquiryHdrId;
    @SerializedName("sales_enquiry_no")
    @Expose
    private String salesEnquiryNo;
    @SerializedName("sales_enquiry_date")
    @Expose
    private String salesEnquiryDate;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("total_amount")
    @Expose
    private String total;

    public Integer getSalesEnquiryHdrId() {
        return salesEnquiryHdrId;
    }

    public void setSalesEnquiryHdrId(Integer salesEnquiryHdrId) {
        this.salesEnquiryHdrId = salesEnquiryHdrId;
    }

    public String getSalesEnquiryNo() {
        return salesEnquiryNo;
    }

    public void setSalesEnquiryNo(String salesEnquiryNo) {
        this.salesEnquiryNo = salesEnquiryNo;
    }

    public String getSalesEnquiryDate() {
        return salesEnquiryDate;
    }

    public void setSalesEnquiryDate(String salesEnquiryDate) {
        this.salesEnquiryDate = salesEnquiryDate;
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
