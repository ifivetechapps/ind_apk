package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Shipping_address extends RealmObject {

    @SerializedName("ship_address_id")
    @Expose
    private Integer shipAddressId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("address_lane_one")
    @Expose
    private String addressLaneOne;
    @SerializedName("address_lane_two")
    @Expose
    private String addressLaneTwo;
    @SerializedName("ship_state")
    @Expose
    private String shipState;
    @SerializedName("ship_city")
    @Expose
    private String shipCity;
    @SerializedName("ship_pincode")
    @Expose
    private String shipPincode;
    @SerializedName("freight_type")
    @Expose
    private Integer freightType;
    @SerializedName("freight_name")
    @Expose
    private Integer freightName;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;

    public Integer getShipAddressId() {
        return shipAddressId;
    }

    public void setShipAddressId(Integer shipAddressId) {
        this.shipAddressId = shipAddressId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getAddressLaneOne() {
        return addressLaneOne;
    }

    public void setAddressLaneOne(String addressLaneOne) {
        this.addressLaneOne = addressLaneOne;
    }

    public String getAddressLaneTwo() {
        return addressLaneTwo;
    }

    public void setAddressLaneTwo(String addressLaneTwo) {
        this.addressLaneTwo = addressLaneTwo;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipPincode() {
        return shipPincode;
    }

    public void setShipPincode(String shipPincode) {
        this.shipPincode = shipPincode;
    }

    public Integer getFreightType() {
        return freightType;
    }

    public void setFreightType(Integer freightType) {
        this.freightType = freightType;
    }

    public Integer getFreightName() {
        return freightName;
    }

    public void setFreightName(Integer freightName) {
        this.freightName = freightName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Integer getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(Integer cmpyId) {
        this.cmpyId = cmpyId;
    }

}