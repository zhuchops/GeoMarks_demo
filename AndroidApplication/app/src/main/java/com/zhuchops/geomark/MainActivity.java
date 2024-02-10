package com.zhuchops.geomark;

import android.content.res.ColorStateList;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CameraListener {
    private MapView mapView;
    private ActivityMainBinding mainBinding;
    private BoxClass mainBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("0a98fb9d-4b92-4861-a005-87e3998b9d96");

        VectorDrawable layers_icon =
                (VectorDrawable) this.getDrawable(R.drawable.layers_icon);
        layers_icon.setTintList(this.getColorStateList(R.color.navigation_icons_color_selector));

        MapKitFactory.initialize(this);

        mainBinding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(mainBinding.getRoot());

        MapKit mapKit = MapKitFactory.getInstance();

        ColorStateList colorStateList = this.getColorStateList(R.color.navigation_icons_color_selector);

        mapView = mainBinding.mapView;
        mapView.getMapWindow().getMap().move(mapView.getMapWindow().getMap().getCameraPosition());
        mapView.getMapWindow().getMap().addCameraListener(this);
        mainBinding.mapButton.setActivated(true);

        mainBox = new BoxClass(mapView.getMapWindow());
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

        GeoLayer layer = new GeoLayer("firstOne", "something here",
                new ArrayList<GeoMark>());
        layer.addMark(new GeoMark(
                "55.560226",
                "37.576095",
                1,
                "one",
                "something here"));
        layer.addMark(new GeoMark(
                "54.989345",
                "73.368211",
                2,
                "two",
                "something here"
        ));
        mainBox.addLayerToActiveUsersLayers(layer);
        MarkManager.placeMarks(mainBinding.marksLayout, mainBox);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        mainBox.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        MarkManager.placeMarks(mainBinding.marksLayout, mainBox);
    }
}