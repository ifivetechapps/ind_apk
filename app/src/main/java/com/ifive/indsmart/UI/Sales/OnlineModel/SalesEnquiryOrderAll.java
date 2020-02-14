package com.ifive.indsmart.UI.Sales.OnlineModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SalesEnquiryOrderAll extends RealmObject {

    @SerializedName("so_headerenquiry_details")
    @Expose
    private RealmList<OnlineEnquiryItemModel> soHeaderenquiryDetails = null;
    @SerializedName("so_enquiry_details")
    @Expose
    private RealmList<OnlineEnquiryLineList> soLinesenquiryDetails = null;

    public RealmList<OnlineEnquiryItemModel> getSoHeaderenquiryDetails() {
        return soHeaderenquiryDetails;
    }

    public void setSoHeaderenquiryDetails(RealmList<OnlineEnquiryItemModel> soHeaderenquiryDetails) {
        this.soHeaderenquiryDetails = soHeaderenquiryDetails;
    }

    public RealmList<OnlineEnquiryLineList> getSoLinesenquiryDetails() {
        return soLinesenquiryDetails;
    }

    public void setSoLinesenquiryDetails(RealmList<OnlineEnquiryLineList> soLinesenquiryDetails) {
        this.soLinesenquiryDetails = soLinesenquiryDetails;
    }

}