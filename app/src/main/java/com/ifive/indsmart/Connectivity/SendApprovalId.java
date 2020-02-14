package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendApprovalId {
    @SerializedName("id")
    @Expose
    private String id;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    @SerializedName("status")
    @Expose
    private String status_id;

    public String getMessage() {
        return id;
    }

    public void setMessage(String message) {
        this.id = message;
    }
}
