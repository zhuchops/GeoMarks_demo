package com.zhuchops.geomark;

import java.util.List;

public class OuterItem {
    private List<LayerElement> innerItems;

    public OuterItem(List<LayerElement> innerItems) {
        this.innerItems = innerItems;
    }

    public List<LayerElement> getInnerItems() {
        return innerItems;
    }

    public void setInnerItems(List<LayerElement> innerItems) {
        this.innerItems = innerItems;
    }
}
