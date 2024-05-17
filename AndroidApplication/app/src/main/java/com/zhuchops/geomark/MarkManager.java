package com.zhuchops.geomark;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkManager{
    static private HashMap<MapView, ArrayList<String>> markIdsOfMapView = new HashMap<>();

    static void placeActiveMarks(ConstraintLayout layout, BoxClass box, MapView mapView) {
        ArrayList<String> idsOfActiveLayers = box.getIdsOfActiveLayers();
        GeoLayer layer;
        for (int i = 0; i < idsOfActiveLayers.size(); i++) {
            layer = box.getLayerWithId(idsOfActiveLayers.get(i));
            placeLayersMark(layout, layer, mapView);
        }
    }

    static void placeLayersMark(ConstraintLayout layout, GeoLayer layer, MapView mapView) {
        if (!markIdsOfMapView.containsKey(mapView)) {
            markIdsOfMapView.put(mapView, new ArrayList<>());
        }

        for (int j = 0; j < layer.getSize(); j++) {
            if (!markIdsOfMapView.get(mapView).contains(layer.getMark(j).getId())) {
                layout.addView(MarkManager.newMarkToView(
                        layout,
                        mapView,
                        layer.getMark(j)));
            } else {
                MarkManager.existedMarkToView(
                        layout,
                        mapView,
                        layer.getMark(j)
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
        markView.setTag(mark.getId());


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
        ImageButton markView = null;

        View view;
        ArrayList<View> allViews = getAllViews(layout);
        for (int i = 0; i < allViews.size(); i++) {
            view = allViews.get(i);
            if (view instanceof ImageButton) {
                if (view.getTag().equals(mark.getId())) {
                    markView = (ImageButton) view;
                    break;
                }
            }
        }

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

    static public ArrayList<View> getAllViews(ViewGroup container) {
        View view;
        ArrayList<View> allViews = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            view = container.getChildAt(i);
            if (view instanceof ViewGroup) {
                allViews.addAll(getAllViews(container));
            } else {
                allViews.add(view);
            }
        }
        return allViews;
    }

}
