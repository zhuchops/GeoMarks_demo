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

public class MainActivity extends AppCompatActivity implements CameraListener {
    private MapView mapView;
    protected ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("0a98fb9d-4b92-4861-a005-87e3998b9d96");
        MapKitFactory.initialize(this);

        VectorDrawable layers_icon =
                (VectorDrawable) this.getDrawable(R.drawable.layers_icon);
        layers_icon.setTintList(this.getColorStateList(R.color.navigation_icons_color_selector));

        mainBinding = ActivityMainBinding.inflate(this.getLayoutInflater());

        MapKit mapKit = MapKitFactory.getInstance();

        setContentView(mainBinding.getRoot());

        ColorStateList colorStateList = this.getColorStateList(R.color.navigation_icons_color_selector);

        mapView = mainBinding.mapView;
        mapView.getMapWindow().getMap().addCameraListener(this);
        mainBinding.mapButton.setActivated(true);
        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {

            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {

    }
}