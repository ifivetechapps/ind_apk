package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SupplierList extends RealmObject {

    @SerializedName("emp_id")
    @Expose
    private int empId;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAdd() {
        return empAdd;
    }

    public void setEmpAdd(String empAdd) {
        this.empAdd = empAdd;
    }

    @SerializedName("emp_name")
    @Expose
    private String empName;

    @SerializedName("emp_add")
    @Expose
    private String empAdd;

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    @SerializedName("emp_num")
    @Expose
    private String empNum;

}
