package com.zhuchops.geomark;

import java.util.ArrayList;

public class OuterItem {
    private ArrayList<LayerElement> innerItems;

    public OuterItem(ArrayList<LayerElement> innerItems) {
        this.innerItems = innerItems;
    }

    public ArrayList<LayerElement> getInnerItems() {
        return innerItems;
    }

    public void setInnerItems(ArrayList<LayerElement> innerItems) {
        this.innerItems = innerItems;
    }
}
