package com.example.jason.route_application_kotlin.data.pojos;

/**
 * Created by Jason on 12-Apr-18.
 */

public class MarkerInfo {

    private String iconType;
    private boolean business;
    private boolean selected;

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
