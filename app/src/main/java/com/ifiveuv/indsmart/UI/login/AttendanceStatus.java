package com.ifiveuv.indsmart.UI.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Comp11 on 2/13/2018.
 */

public class AttendanceStatus {

    @SerializedName("employee_log_id")
    @Expose
    private Integer employeeLogId;
    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("login_status")
    @Expose
    private Integer loginStatus;
    @SerializedName("s_timestamp")
    @Expose
    private String sTimestamp;
    @SerializedName("s_latitude")
    @Expose
    private Double sLatitude;
    @SerializedName("s_longitude")
    @Expose
    private Double sLongitude;
    @SerializedName("s_battery")
    @Expose
    private Integer sBattery;
    @SerializedName("e_timestamp")
    @Expose
    private String eTimestamp;


    @SerializedName("e_battery")
    @Expose
    private Integer eBattery;
    @SerializedName("delete_status")
    @Expose
    private Integer deleteStatus;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

    public Integer getEmployeeLogId() {
        return employeeLogId;
    }

    public void setEmployeeLogId(Integer employeeLogId) {
        this.employeeLogId = employeeLogId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getSTimestamp() {
        return sTimestamp;
    }

    public void setSTimestamp(String sTimestamp) {
        this.sTimestamp = sTimestamp;
    }

    public Double getSLatitude() {
        return sLatitude;
    }

    public void setSLatitude(Double sLatitude) {
        this.sLatitude = sLatitude;
    }

    public Double getSLongitude() {
        return sLongitude;
    }

    public void setSLongitude(Double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public Integer getSBattery() {
        return sBattery;
    }

    public void setSBattery(Integer sBattery) {
        this.sBattery = sBattery;
    }

    public String getETimestamp() {
        return eTimestamp;
    }

    public void setETimestamp(String eTimestamp) {
        this.eTimestamp = eTimestamp;
    }


    public String getsTimestamp() {
        return sTimestamp;
    }

    public void setsTimestamp(String sTimestamp) {
        this.sTimestamp = sTimestamp;
    }

    public Double getsLatitude() {
        return sLatitude;
    }

    public void setsLatitude(Double sLatitude) {
        this.sLatitude = sLatitude;
    }

    public Double getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(Double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public Integer getsBattery() {
        return sBattery;
    }

    public void setsBattery(Integer sBattery) {
        this.sBattery = sBattery;
    }

    public String geteTimestamp() {
        return eTimestamp;
    }

    public void seteTimestamp(String eTimestamp) {
        this.eTimestamp = eTimestamp;
    }


    public Integer geteBattery() {
        return eBattery;
    }

    public void seteBattery(Integer eBattery) {
        this.eBattery = eBattery;
    }

    public Integer getEBattery() {
        return eBattery;
    }

    public void setEBattery(Integer eBattery) {
        this.eBattery = eBattery;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(Integer cmpyId) {
        this.cmpyId = cmpyId;
    }
}
