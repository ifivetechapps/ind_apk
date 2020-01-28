package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CustomerList extends RealmObject {

    @SerializedName("customer_id")
    @Expose
    private int cusNo;

    @SerializedName("customer_name")
    @Expose
    private String cusName;
    @SerializedName("address")
    @Expose
    private String cusAdd;
    @SerializedName("mobile_number")
    @Expose
    private String cusMobile;
    @SerializedName("email_id")
    @Expose
    private String cusEmail;

    public int getCusNo() {
        return cusNo;
    }

    public void setCusNo(int cusNo) {
        this.cusNo = cusNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAdd() {
        return cusAdd;
    }

    public void setCusAdd(String cusAdd) {
        this.cusAdd = cusAdd;
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }
}
