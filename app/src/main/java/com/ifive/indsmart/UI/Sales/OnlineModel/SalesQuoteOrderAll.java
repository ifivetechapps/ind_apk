package com.ifive.indsmart.UI.Sales.OnlineModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SalesQuoteOrderAll extends RealmObject {
    @SerializedName("so_headerquote_details")
    @Expose
    private RealmList<SoHeaderquoteDetail> soHeaderquoteDetails = null;
    @SerializedName("so_quote_details")
    @Expose
    private RealmList<SoQuoteDetail> soQuoteDetails = null;

    public RealmList<SoHeaderquoteDetail> getSoHeaderquoteDetails() {
        return soHeaderquoteDetails;
    }

    public void setSoHeaderquoteDetails(RealmList<SoHeaderquoteDetail> soHeaderquoteDetails) {
        this.soHeaderquoteDetails = soHeaderquoteDetails;
    }

    public RealmList<SoQuoteDetail> getSoQuoteDetails() {
        return soQuoteDetails;
    }

    public void setSoQuoteDetails(RealmList<SoQuoteDetail> soQuoteDetails) {
        this.soQuoteDetails = soQuoteDetails;
    }
}