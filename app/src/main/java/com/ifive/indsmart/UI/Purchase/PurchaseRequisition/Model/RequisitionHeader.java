package com.ifive.indsmart.UI.Purchase.PurchaseRequisition.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RequisitionHeader extends RealmObject {
    public int getHdrid() {
        return Hdrid;
    }

    public void setHdrid(int hdrid) {
        Hdrid = hdrid;
    }

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RealmList<RequisitionLines> getRequisitionLines() {
        return requisitionLines;
    }

    public void setRequisitionLines(RealmList<RequisitionLines> requisitionLines) {
        this.requisitionLines = requisitionLines;
    }

    @PrimaryKey
    @SerializedName("req_hdrid")
    @Expose
    private int Hdrid;

    @SerializedName("requestor_id")
    @Expose
    private int reqID;

    @SerializedName("supplier_name")
    @Expose
    private String requestorName;
    @SerializedName("purchase_req_type")
    @Expose
    private String typeRequisition;
    @SerializedName("purchase_req_date")
    @Expose
    private String requestDate;

    public String getPurchase_reqdescription() {
        return purchase_reqdescription;
    }

    public void setPurchase_reqdescription(String purchase_reqdescription) {
        this.purchase_reqdescription = purchase_reqdescription;
    }

    @SerializedName("purchase_req_description")
    @Expose
    private String purchase_reqdescription;
    @SerializedName("request_status")
    @Expose
    private String requestStatus;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("online_id")
    @Expose
    private String onlineReqId;

    public String getOnlineReqId() {
        return onlineReqId;
    }

    public void setOnlineReqId(String onlineReqId) {
        this.onlineReqId = onlineReqId;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @SerializedName("purchase_requisition_details_tbl")
    @Expose
    private RealmList<RequisitionLines> requisitionLines;

    public String getTypeRequisition() {
        return typeRequisition;
    }

    public void setTypeRequisition(String typeRequisition) {
        this.typeRequisition = typeRequisition;
    }
}
