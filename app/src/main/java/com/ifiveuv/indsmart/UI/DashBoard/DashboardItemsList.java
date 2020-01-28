package com.ifiveuv.indsmart.UI.DashBoard;

import java.util.List;

public class DashboardItemsList {

    private String menuName;
    private int iconID;
    private Class<?> aClass;
    private int colorID;
    private List<DashboardItemsList> subMenuItemsList;

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public List<DashboardItemsList> getSubMenuItemsList() {
        return subMenuItemsList;
    }

    public void setSubMenuItemsList(List<DashboardItemsList> subMenuItemsList) {
        this.subMenuItemsList = subMenuItemsList;
    }
}