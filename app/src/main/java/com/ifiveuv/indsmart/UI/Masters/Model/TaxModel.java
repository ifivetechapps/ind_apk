package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaxModel extends RealmObject {


    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @PrimaryKey
    @SerializedName("tax_id")
    @Expose
    private Integer taxId;

    @SerializedName("tax_name")
    @Expose
    private String taxName;
}
