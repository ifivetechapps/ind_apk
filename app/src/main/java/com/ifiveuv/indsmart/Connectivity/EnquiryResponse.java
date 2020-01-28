package com.ifiveuv.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class EnquiryResponse extends RealmObject {
    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("id")
    @Expose
    private String enquiryId;

    @SerializedName("message")
    @Expose
    private String message;
}
