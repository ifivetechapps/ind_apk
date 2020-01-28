package com.ifiveuv.indsmart.UI.Masters.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllCustomerList extends RealmObject {
    @SerializedName("customerList")
    @Expose
    private RealmList<CustomerList> customerLists = null;

    public RealmList<CustomerList> getCustomerLists() {
        return customerLists;
    }

    public void setCustomerLists(RealmList<CustomerList> customerLists) {
        this.customerLists = customerLists;
    }
}
