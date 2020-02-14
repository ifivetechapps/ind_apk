package com.ifive.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ifive.indsmart.UI.Masters.Model.CustomerList;
import com.ifive.indsmart.UI.Masters.Model.InventoryType;
import com.ifive.indsmart.UI.Masters.Model.SubInventory;
import com.ifive.indsmart.UI.Masters.Model.SupplierList;
import com.ifive.indsmart.UI.Masters.Model.UomModel;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllDataList extends RealmObject {

    public RealmList<Products> getProduct() {
        return product;
    }

    public void setProduct(RealmList<Products> product) {
        this.product = product;
    }

    public RealmList<ProductCategory> getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(RealmList<ProductCategory> productCategory) {
        this.productCategory = productCategory;
    }

    public RealmList<ProductGroup> getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(RealmList<ProductGroup> productGroup) {
        this.productGroup = productGroup;
    }

    public RealmList<UomModel> getUom() {
        return uom;
    }

    public void setUom(RealmList<UomModel> uom) {
        this.uom = uom;
    }

    public RealmList<InventoryType> getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(RealmList<InventoryType> inventoryType) {
        this.inventoryType = inventoryType;
    }

    public RealmList<SubInventory> getSubInventory() {
        return subInventory;
    }

    public void setSubInventory(RealmList<SubInventory> subInventory) {
        this.subInventory = subInventory;
    }

    public RealmList<Shipping_address> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(RealmList<Shipping_address> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public RealmList<Tax_type> getTaxType() {
        return taxType;
    }

    public void setTaxType(RealmList<Tax_type> taxType) {
        this.taxType = taxType;
    }

    public RealmList<CustomerList> getCustomerLists() {
        return customerLists;
    }

    public void setCustomerLists(RealmList<CustomerList> customerLists) {
        this.customerLists = customerLists;
    }

    public RealmList<SupplierList> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(RealmList<SupplierList> supplierList) {
        this.supplierList = supplierList;
    }

    @SerializedName("product")
    @Expose
    private RealmList<Products> product = null;
    @SerializedName("product_category")
    @Expose
    private RealmList<ProductCategory> productCategory = null;
    @SerializedName("product_group")
    @Expose
    private RealmList<ProductGroup> productGroup = null;
    @SerializedName("uom")
    @Expose
    private RealmList<UomModel> uom = null;
    @SerializedName("inventory_type")
    @Expose
    private RealmList<InventoryType> inventoryType = null;
    @SerializedName("sub_inventory")
    @Expose
    private RealmList<SubInventory> subInventory = null;
    @SerializedName("shipping_address")
    @Expose
    private RealmList<Shipping_address> shippingAddress = null;
    @SerializedName("tax_type")
    @Expose
    private RealmList<Tax_type> taxType = null;
    @SerializedName("CustomerList")
    @Expose
    private RealmList<CustomerList> customerLists = null;
    @SerializedName("SupplierList")
    @Expose
    private RealmList<SupplierList> supplierList = null;


}
