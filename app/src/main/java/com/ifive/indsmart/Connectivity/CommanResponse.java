package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommanResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
