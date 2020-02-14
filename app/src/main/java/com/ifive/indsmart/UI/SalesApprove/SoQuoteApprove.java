package com.ifive.indsmart.UI.SalesApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoQuoteApprove {

    @SerializedName("so_quote_list")
    @Expose
    private List<SoQuoteList> soQuoteList = null;

    public List<SoQuoteList> getSoQuoteList() {
        return soQuoteList;
    }

    public void setSoQuoteList(List<SoQuoteList> soQuoteList) {
        this.soQuoteList = soQuoteList;
    }
}
