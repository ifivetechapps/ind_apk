package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PackingTypeModel extends RealmObject {
    @PrimaryKey
    @SerializedName("TypeModelid")
    @Expose
    private Integer TypeModel_id;

    @SerializedName("TypeModel_name")
    @Expose
    private String TypeModel_name;
    @SerializedName("description")
    @Expose
    private String description;
}
