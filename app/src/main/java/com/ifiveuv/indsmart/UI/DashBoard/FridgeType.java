package com.ifiveuv.indsmart.UI.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FridgeType extends RealmObject {

    @PrimaryKey
    @SerializedName("ft_id")
    @Expose
    private Integer ftId;

    @SerializedName("ft_type")
    @Expose
    private String ftType;

    public Integer getFtId() {
        return ftId;
    }

    public void setFtId(Integer ftId) {
        this.ftId = ftId;
    }

    public String getFtType() {
        return ftType;
    }

    public void setFtType(String ftType) {
        this.ftType = ftType;
    }
}
