package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("login_attempt")
    @Expose
    private Integer loginAttempt;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("reminder")
    @Expose
    private String reminder;
    @SerializedName("activation")
    @Expose
    private Object activation;
    @SerializedName("last_activity")
    @Expose
    private Integer lastActivity;
    @SerializedName("login_status")
    @Expose
    private Integer loginStatus;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("cmpy_id")
    @Expose
    private Integer cmpyId;
    @SerializedName("loc_id")
    @Expose
    private Integer locId;
    @SerializedName("createdOn")
    @Expose
    private Object createdOn;
    @SerializedName("updatedOn")
    @Expose
    private Object updatedOn;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("m_token")
    @Expose
    private String mToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(Integer loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public Object getActivation() {
        return activation;
    }

    public void setActivation(Object activation) {
        this.activation = activation;
    }

    public Integer getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Integer lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
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

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

    public Object getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Object updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMToken() {
        return mToken;
    }

    public void setMToken(String mToken) {
        this.mToken = mToken;
    }

}
