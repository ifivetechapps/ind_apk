package com.ifive.indsmart.UI.Masters.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class FreightList extends RealmObject {
    public int getFreightId() {
        return freightId;
    }

    public void setFreightId(int freightId) {
        this.freightId = freightId;
    }

    public String getFreightName() {
        return freightName;
    }

    public void setFreightName(String freightName) {
        this.freightName = freightName;
    }

    @SerializedName("freight_id")
    @Expose
    private int freightId;

    @SerializedName("freight_name")
    @Expose
    private String freightName;

}
