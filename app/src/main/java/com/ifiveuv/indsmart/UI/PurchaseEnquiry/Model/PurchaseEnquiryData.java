package com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PurchaseEnquiryData extends RealmObject {

    @PrimaryKey
    @SerializedName("Enquiry_id")
    @Expose
    private int enquiryId;
    @SerializedName("SupplierName")
    @Expose
    private String supplierName;
    @SerializedName("SupplierId")
    @Expose
    private String supplierId;
    @SerializedName("SupplierSiteName")
    @Expose
    private String supplierSiteName;
    @SerializedName("onlineStatus")
    @Expose
    private String onlineStatus;
    @SerializedName("onlineId")
    @Expose
    private String onlineId;
    @SerializedName("enquiry_type_id")
    @Expose
    private String enquiryType;
    @SerializedName("enquiry_date")
    @Expose
    private String enquiryDate;
    @SerializedName("Enquiry_source")
    @Expose
    private String enquirySource;
    @SerializedName("status")
    @Expose
    private String supplierSitestatus;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("purchase_requisition_details_tbl")
    @Expose
    private RealmList<EnquiryItemList> enquiryItemLists;
    public int getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(int enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierSiteName() {
        return supplierSiteName;
    }

    public void setSupplierSiteName(String supplierSiteName) {
        this.supplierSiteName = supplierSiteName;
    }


    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(String onlineId) {
        this.onlineId = onlineId;
    }



    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }



    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }


    public String getEnquirySource() {
        return enquirySource;
    }

    public void setEnquirySource(String enquirySource) {
        this.enquirySource = enquirySource;
    }



    public String getSupplierSitestatus() {
        return supplierSitestatus;
    }

    public void setSupplierSitestatus(String supplierSitestatus) {
        this.supplierSitestatus = supplierSitestatus;
    }


    public RealmList<EnquiryItemList> getEnquiryItemLists() {
        return enquiryItemLists;
    }

    public void setEnquiryItemLists(RealmList<EnquiryItemList> enquiryItemLists) {
        this.enquiryItemLists = enquiryItemLists;
    }



}
