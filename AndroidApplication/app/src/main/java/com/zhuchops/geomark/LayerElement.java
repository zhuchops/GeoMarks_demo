package com.zhuchops.geomark;

import android.media.Image;

public class LayerElement {

    private Image image;
    private String name;
    private String description;
    private boolean isChecked;

    public LayerElement(Image image, String name, String description, boolean isChecked) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.isChecked = isChecked;
    }

    public Image getImage() {
        return image;
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

    public void setImage(Image image) {
        this.image = image;
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
}
