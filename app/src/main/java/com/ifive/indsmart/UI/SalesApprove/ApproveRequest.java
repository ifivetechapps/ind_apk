package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApproveRequest {

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;
}
