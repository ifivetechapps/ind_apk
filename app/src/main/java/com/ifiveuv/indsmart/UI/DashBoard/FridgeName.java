package com.ifiveuv.indsmart.UI.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FridgeName extends RealmObject {

    @PrimaryKey
    @SerializedName("fn_id")
    @Expose
    private Integer fnId;
    @SerializedName("ft_name")
    @Expose
    private String ftName;
    @SerializedName("ft_id")
    @Expose
    private int ftId;

    public Integer getFnId() {
        return fnId;
    }

    public void setFnId(Integer fnId) {
        this.fnId = fnId;
    }

    public String getFtName() {
        return ftName;
    }

    public void setFtName(String ftName) {
        this.ftName = ftName;
    }

    public int getFtId() {
        return ftId;
    }

    public void setFtId(int ftId) {
        this.ftId = ftId;
    }
}
