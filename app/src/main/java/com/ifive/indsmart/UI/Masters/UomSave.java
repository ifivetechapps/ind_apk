package com.ifive.indsmart.UI.Masters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UomSave {

    @SerializedName("uom_name")
    @Expose
    private String uomname;

    public String getUomname() {
        return uomname;
    }

    public void setUomname(String uomname) {
        this.uomname = uomname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
}
