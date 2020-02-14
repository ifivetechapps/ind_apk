package com.ifive.indsmart.UI.Sales.SalesInvoice.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InvoiceItemList extends RealmObject {
    public Integer getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    public int getSo_number() {
        return so_number;
    }

    public void setSo_number(int so_number) {
        this.so_number = so_number;
    }



    public String getTypeOfOrder() {
        return typeOfOrder;
    }

    public void setTypeOfOrder(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getDel_date() {
        return del_date;
    }

    public void setDel_date(String del_date) {
        this.del_date = del_date;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }


    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(String taxValue) {
        this.taxValue = taxValue;
    }

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String taxAmt) {
        this.taxAmt = taxAmt;
    }


    public String getFreightcost() {
        return freightcost;
    }

    public void setFreightcost(String freightcost) {
        this.freightcost = freightcost;
    }

    public String getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(String grossTotal) {
        this.grossTotal = grossTotal;
    }

    public String getFreightpay() {
        return freightpay;
    }

    public void setFreightpay(String freightpay) {
        this.freightpay = freightpay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatusvalue() {
        return statusvalue;
    }

    public void setStatusvalue(String statusvalue) {
        this.statusvalue = statusvalue;
    }


    public RealmList<InvoiceItemLinelist> getInvoiceItemLinelists() {
        return invoiceItemLinelists;
    }

    public void setInvoiceItemLinelists(RealmList<InvoiceItemLinelist> invoiceItemLinelists) {
        this.invoiceItemLinelists = invoiceItemLinelists;
    }

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer invoiceid;
    @SerializedName("so_number")
    @Expose
    private int so_number;
    @SerializedName("type_id")
    @Expose
    private String typeOfOrder;
    @SerializedName("approval_status")
    @Expose
    private String approvalstatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sales_invoice_date")
    @Expose
    private String invDate;
    @SerializedName("invoice_delivery_date")
    @Expose
    private String del_date;
    @SerializedName("customer_id")
    @Expose
    private int cus_id;
    @SerializedName("customer_name")
    @Expose
    private String cus_name;
    @SerializedName("del_address")
    @Expose
    private String deliveryaddress;

    @SerializedName("tax_type")
    @Expose
    private String taxType;
    @SerializedName("tax_value")
    @Expose
    private String taxValue;
    @SerializedName("tax_amount")
    @Expose
    private String taxAmt;
    @SerializedName("freight_cost")
    @Expose
    private String freightcost;
    @SerializedName("gross_amount")
    @Expose
    private String grossTotal;
    @SerializedName("freight_pay")
    @Expose
    private String freightpay;

    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("online_id")
    @Expose
    private String onlineId;
    @SerializedName("so_invoice_line_list")
    @Expose
    private RealmList<InvoiceItemLinelist> invoiceItemLinelists;

    @SerializedName("status_id")
    @Expose
    private String status;
    @SerializedName("invoice_total_price")
    @Expose
    private String totalPrice;
    @SerializedName("invoice_status")
    @Expose
    private String statusvalue;

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(String onlineId) {
        this.onlineId = onlineId;
    }


}
