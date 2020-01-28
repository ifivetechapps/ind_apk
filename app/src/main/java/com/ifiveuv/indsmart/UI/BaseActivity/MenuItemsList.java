package com.ifiveuv.indsmart.UI.BaseActivity;

import java.util.List;

public class MenuItemsList {

    private String menuName;
    private int iconID;
    private Class<?> aClass;
    private List<MenuItemsList> subMenuItemsList;
    private boolean withAttendance;

    public boolean isWithAttendance() {
        return withAttendance;
    }

    public void setWithAttendance(boolean withAttendance) {
        this.withAttendance = withAttendance;
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

    public List<MenuItemsList> getSubMenuItemsList() {
        return subMenuItemsList;
    }

    public void setSubMenuItemsList(List<MenuItemsList> subMenuItemsList) {
        this.subMenuItemsList = subMenuItemsList;
    }
}
