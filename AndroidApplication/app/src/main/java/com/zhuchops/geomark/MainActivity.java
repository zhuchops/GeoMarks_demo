package com.zhuchops.geomark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ImageButton;

import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("0a98fb9d-4b92-4861-a005-87e3998b9d96");
        MapKitFactory.initialize(this);

        VectorDrawable layers_icon =
                (VectorDrawable) this.getDrawable(R.drawable.layers_icon);
        layers_icon.setTintList(this.getColorStateList(R.color.navigation_icons_color_selector));

        mainBinding = ActivityMainBinding.inflate(this.getLayoutInflater());

        setContentView(mainBinding.getRoot());

        ColorStateList colorStateList = this.getColorStateList(R.color.navigation_icons_color_selector);

        mapView = mainBinding.mapView;
        mainBinding.mapButton.setActivated(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}