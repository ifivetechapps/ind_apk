package com.ifive.indsmart.UI.Purchase.OnlineModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;

public class PurchaseEnquiryAll extends RealmObject {
    public RealmList<PurchaseEnquiryHdrT> getPurchaseEnquiryHdrT() {
        return purchaseEnquiryHdrT;
    }

    public void setPurchaseEnquiryHdrT(RealmList<PurchaseEnquiryHdrT> purchaseEnquiryHdrT) {
        this.purchaseEnquiryHdrT = purchaseEnquiryHdrT;
    }

    public RealmList<PurchaseEnquiryLinesT> getPurchaseEnquiryLinesT() {
        return purchaseEnquiryLinesT;
    }

    public void setPurchaseEnquiryLinesT(RealmList<PurchaseEnquiryLinesT> purchaseEnquiryLinesT) {
        this.purchaseEnquiryLinesT = purchaseEnquiryLinesT;
    }

    @SerializedName("purchase_enquiry_hdr_t")
    @Expose
    private RealmList<PurchaseEnquiryHdrT> purchaseEnquiryHdrT = null;
    @SerializedName("purchase_enquiry_lines_t")
    @Expose
    private RealmList<PurchaseEnquiryLinesT> purchaseEnquiryLinesT = null;
}
