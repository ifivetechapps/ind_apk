package com.ifiveuv.indsmart.UI.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PaymentType extends RealmObject {

    @SerializedName("type_id")
    @Expose
    private int typeId;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
