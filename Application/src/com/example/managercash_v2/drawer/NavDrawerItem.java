package com.example.managercash_v2.drawer;

public interface NavDrawerItem {

    public int getId();
    public String getLabel();
    public int getType();
    public boolean isEnabled();
    public boolean updateActionBarTitle();

}
