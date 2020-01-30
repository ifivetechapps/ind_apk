package com.ifiveuv.indsmart.UI.PurchaseEnquiry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PurchaseEnquiryData extends RealmObject {
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

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    @SerializedName("Enquirytype")
    @Expose
    private String enquiryType;

    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    @SerializedName("Enquiry_date")
    @Expose
    private String enquiryDate;

    public String getEnquirySource() {
        return enquirySource;
    }

    public void setEnquirySource(String enquirySource) {
        this.enquirySource = enquirySource;
    }

    @SerializedName("Enquiry_source")
    @Expose
    private String enquirySource;


    public String getSupplierSitestatus() {
        return supplierSitestatus;
    }

    public void setSupplierSitestatus(String supplierSitestatus) {
        this.supplierSitestatus = supplierSitestatus;
    }

    @SerializedName("SupplierStatus")
    @Expose
    private String supplierSitestatus;

    public RealmList<EnquiryItemList> getEnquiryItemLists() {
        return enquiryItemLists;
    }

    public void setEnquiryItemLists(RealmList<EnquiryItemList> enquiryItemLists) {
        this.enquiryItemLists = enquiryItemLists;
    }

    @SerializedName("EnquiryItemList")
    @Expose
    private RealmList<EnquiryItemList> enquiryItemLists;

}
