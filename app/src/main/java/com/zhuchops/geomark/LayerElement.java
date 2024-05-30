package com.zhuchops.geomark;

public class LayerElement {

    private String layerId;
    private byte[] imageData;
    private String name;
    private String description;
    private boolean isChecked;

    public LayerElement(String layerId, byte[] imageData, String name, String description, boolean isChecked) {
        this.layerId = layerId;
        this.imageData = imageData;
        this.name = name;
        this.description = description;
        this.isChecked = isChecked;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return layerId;
    }
}
