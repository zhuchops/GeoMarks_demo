package com.zhuchops.geomark;

import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapWindow;

import java.util.ArrayList;

public class MarkManager {

    static private ArrayList<Integer> marksId = new ArrayList<>();

    static void placeMarks (ConstraintLayout layout, BoxClass box) {
        ArrayList<GeoLayer> layers = box.getActiveUsersLayers();
        GeoLayer layer;
        for (int i = 0; i < layers.size(); i++) {
            layer = layers.get(i);
            for (int j = 0; j < layer.size(); j++) {
                if (!marksId.contains(layer.get(j).getId())) {
                    layout.addView(MarkManager.newMarkToView(
                            layout,
                            box.getMapWindow(),
                            layer.get(j)));
                } else {
                    MarkManager.existedMarkToView(
                            layout,
                            box.getMapWindow(),
                            layer.get(j)
                    );
                }
            }
        }
    }

    static ImageButton newMarkToView(ConstraintLayout layout, MapWindow mapWindow, GeoMark mark) {
        //нужно добавить id, для того чтобы потом получать данные из метки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        ImageButton markView = null;
        markView = new ImageButton(layout.getContext());
        markView.setImageResource(R.drawable.layers_icon);
        markView.setBackgroundResource(R.drawable.mark_sizes_selector);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        marksId.add(mark.getId());
        markView.setId(mark.getId());

        ScreenPoint markOnMap = mapWindow.worldToScreen(
                new Point(
                        Double.parseDouble(mark.getX()),
                        Double.parseDouble(mark.getY())
                )
        );

        layoutParams.leftMargin = (int) markOnMap.getX() - markView.getWidth() / 2;
        layoutParams.topMargin = (int) markOnMap.getY() - markView.getHeight();

        markView.setLayoutParams(layoutParams);

        return markView;
    }

    static public void existedMarkToView(ConstraintLayout layout, MapWindow mapWindow, GeoMark mark) {
        ImageButton markView = layout.findViewById(mark.getId());
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) markView.getLayoutParams();

        ScreenPoint markOnMap = mapWindow.worldToScreen(
                new Point(
                        Double.parseDouble(mark.getX()),
                        Double.parseDouble(mark.getY())
                )
        );

        layoutParams.leftMargin = (int) markOnMap.getX() - markView.getWidth() / 2;
        layoutParams.topMargin = (int) markOnMap.getY() - markView.getHeight();

        markView.setLayoutParams(layoutParams);
    }

}
