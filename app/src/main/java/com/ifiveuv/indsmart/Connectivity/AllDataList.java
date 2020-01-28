package com.ifiveuv.indsmart.Connectivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ifiveuv.indsmart.UI.Masters.Model.InventoryType;
import com.ifiveuv.indsmart.UI.Masters.Model.SubInventory;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllDataList extends RealmObject {
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

    public RealmList<Shipping_address> getShipping_addresses() {
        return shipping_addresses;
    }

    public void setShipping_addresses(RealmList<Shipping_address> shipping_addresses) {
        this.shipping_addresses = shipping_addresses;
    }

    public RealmList<Tax_type> getTax_types() {
        return tax_types;
    }

    public void setTax_types(RealmList<Tax_type> tax_types) {
        this.tax_types = tax_types;
    }

    @SerializedName("shipping_address")
    @Expose
    private RealmList<Shipping_address> shipping_addresses = null;
    @SerializedName("tax_type")
    @Expose
    private RealmList<Tax_type> tax_types = null;

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

}
