package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesEnquiryList {
    @SerializedName("so_enquiry_list")
    @Expose
    private List<SoEnquiryList> soEnquiryList = null;

    public List<SoEnquiryList> getSoEnquiryList() {
        return soEnquiryList;
    }

    public void setSoEnquiryList(List<SoEnquiryList> soEnquiryList) {
        this.soEnquiryList = soEnquiryList;
    }

}
