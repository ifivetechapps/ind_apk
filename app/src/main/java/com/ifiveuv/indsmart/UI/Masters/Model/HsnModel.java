package com.ifiveuv.indsmart.UI.Masters.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HsnModel extends RealmObject {
    public Integer getHsnId() {
        return hsnId;
    }

    public void setHsnId(Integer hsnId) {
        this.hsnId = hsnId;
    }

    public String getHsnName() {
        return hsnName;
    }

    public void setHsnName(String hsnName) {
        this.hsnName = hsnName;
    }

    @PrimaryKey
    @SerializedName("hsn_id")
    @Expose
    private Integer hsnId;

    @SerializedName("hsn_name")
    @Expose
    private String hsnName;
}
