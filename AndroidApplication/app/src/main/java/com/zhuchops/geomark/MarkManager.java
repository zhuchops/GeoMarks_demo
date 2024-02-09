package com.zhuchops.geomark;

import android.content.Context;
import android.widget.ImageButton;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;

public class MarkManager {

    static void placeMarks (CoordinatorLayout layout, BoxClass box) {
        ArrayList<GeoLayer> layers = box.getActiveUsersLayers();
        GeoLayer layer;
        for (int i = 0; i < layers.size(); i++) {
            layer = layers.get(i);
            for (int j = 0; j < layer.size(); j++) {
                layout.addView(MarkManager.markToView(layout.getContext(), layer.get(i)));
            }
        }
    }

    static ImageButton markToView(Context context, GeoMark mark) {
        //нужно добавить id, для того чтобы потом получать данные из метки
        ImageButton markView = new ImageButton(context);
        markView.setImageResource(R.drawable.layers_icon);
        markView.setBackgroundResource(R.drawable.mark_sizes_selector);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.
        return markView;
    }

}
