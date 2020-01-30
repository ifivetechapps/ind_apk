package com.ifiveuv.indsmart.UI.Sales.SalesEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EnquiryItemModel extends RealmObject {
    public Integer getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Integer enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public String getEnquiryCustomerName() {
        return enquiryCustomerName;
    }

    public void setEnquiryCustomerName(String enquiryCustomerName) {
        this.enquiryCustomerName = enquiryCustomerName;
    }

    public int getEnquiryCustomerId() {
        return enquiryCustomerId;
    }

    public void setEnquiryCustomerId(int enquiryCustomerId) {
        this.enquiryCustomerId = enquiryCustomerId;
    }

    public String getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(String enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public RealmList<EnquiryLineList> getEnquiryLineLists() {
        return enquiryLineLists;
    }

    public void setEnquiryLineLists(RealmList<EnquiryLineList> enquiryLineLists) {
        this.enquiryLineLists = enquiryLineLists;
    }
    public String getEnquiryRemarks() {
        return enquiryRemarks;
    }

    public void setEnquiryRemarks(String enquiryRemarks) {
        this.enquiryRemarks = enquiryRemarks;
    }
    @PrimaryKey
    @SerializedName("enq_id")
    @Expose
    private Integer enquiryId;

    @SerializedName("enquiry_date")
    @Expose
    private String enquiryDate;

    public String getEnqOnlineId() {
        return enqOnlineId;
    }

    public void setEnqOnlineId(String enqOnlineId) {
        this.enqOnlineId = enqOnlineId;
    }

    @SerializedName("enquiry_online_id")
    @Expose
    private String enqOnlineId;
    @SerializedName("online_status")
    @Expose
    private String stautsOnline;
    @SerializedName("enquiry_del_date")
    @Expose
    private String deliveryDate;
    @SerializedName("ecustomer_name")
    @Expose
    private String enquiryCustomerName;

    @SerializedName("customer_id")
    @Expose
    private int enquiryCustomerId;

    @SerializedName("enquiry_status")
    @Expose
    private String enquiryStatus;

    @SerializedName("enquiry_type")
    @Expose
    private String enquiryType;
    @SerializedName("enquiry_remarks")
    @Expose
    private String enquiryRemarks;
    @SerializedName("enquiry_line_list")
    @Expose
    private RealmList<EnquiryLineList> enquiryLineLists = null;


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStautsOnline() {
        return stautsOnline;
    }

    public void setStautsOnline(String stautsOnline) {
        this.stautsOnline = stautsOnline;
    }
}
