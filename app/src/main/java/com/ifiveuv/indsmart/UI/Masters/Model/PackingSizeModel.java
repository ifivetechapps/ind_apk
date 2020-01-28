package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PackingSizeModel extends RealmObject {
    @PrimaryKey
    @SerializedName("SizeModelid")
    @Expose
    private Integer SizeModel_id;

    @SerializedName("SizeModel_name")
    @Expose
    private String SizeModel_name;
    @SerializedName("description")
    @Expose
    private String description;
}
