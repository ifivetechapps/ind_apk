package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceItemApprove {

    @SerializedName("so_invoice_list")
    @Expose
    private List<SoInvoiceList> soInvoiceList = null;

    public List<SoInvoiceList> getSoInvoiceList() {
        return soInvoiceList;
    }

    public void setSoInvoiceList(List<SoInvoiceList> soInvoiceList) {
        this.soInvoiceList = soInvoiceList;
    }
}
