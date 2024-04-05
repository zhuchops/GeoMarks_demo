package com.zhuchops.geomark;

import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkManager{
    static private HashMap<MapView, ArrayList<Integer>> markIdsOfMapView = new HashMap<>();

    static void placeActiveMarks(ConstraintLayout layout, BoxClass box, MapView mapView) {
        ArrayList<Integer> idsOfActiveLayers = box.getIdsOfActiveLayers();
        GeoLayer layer;
        for (int i = 0; i < idsOfActiveLayers.size(); i++) {
            layer = box.getLayerWithId(idsOfActiveLayers.get(i));
            placeLayersMark(layout, layer, mapView);
        }
    }

    static void placeLayersMark(ConstraintLayout layout, GeoLayer layer, MapView mapView) {
        for (int j = 0; j < layer.size(); j++) {
            if (!markIdsOfMapView.get(mapView).contains(layer.get(j).getId())) {
                layout.addView(MarkManager.newMarkToView(
                        layout,
                        mapView,
                        layer.get(j)));
            } else {
                MarkManager.existedMarkToView(
                        layout,
                        mapView,
                        layer.get(j)
                );
            }
        }
    }

    static ImageButton newMarkToView(ConstraintLayout layout, MapView mapView, GeoMark mark) {
        //нужно добавить id, для того чтобы потом получать данные из метки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        MapWindow mapWindow = mapView.getMapWindow();
        ImageButton markView = null;
        markView = new ImageButton(layout.getContext());
        markView.setImageResource(R.drawable.layers_icon);
        markView.setBackgroundResource(R.drawable.mark_sizes_selector);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        markIdsOfMapView.get(mapView).add(mark.getId());
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

    static public void existedMarkToView(ConstraintLayout layout, MapView mapView, GeoMark mark) {
        ImageButton markView = layout.findViewById(mark.getId());
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) markView.getLayoutParams();
        MapWindow mapWindow = mapView.getMapWindow();

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
