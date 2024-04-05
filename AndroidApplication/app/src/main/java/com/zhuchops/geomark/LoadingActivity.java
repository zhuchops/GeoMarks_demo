package com.zhuchops.geomark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LoadingActivity extends AppCompatActivity {
    private MapView mapView;
    private BoxClass mainBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();

        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);

        mainBox = BoxClass.getInstance();
//        mapView = findViewById(R.id.display_layout).findViewById(R.id.mapView);

        VectorDrawable layers_icon =
                (VectorDrawable) this.getDrawable(R.drawable.layers_icon);
        if (layers_icon != null) {
            layers_icon.setTintList(this.getColorStateList(R.color.navigation_icons_color_selector));
        }

        // reminding of previous active layers
        if (!sharedPreferences.getString(
                getString(R.string.getIdFromSharedPreferences), "").equals("")) {
            List<Integer> ids = Arrays.stream(sharedPreferences.getString(
                    getString(R.string.getIdFromSharedPreferences), ""
            ).split(";")).map(Integer::valueOf).collect(Collectors.toList());
            for (int i = 0; i < ids.size(); i++) {
                mainBox.addIdToActiveIds(ids.get(i));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        mainBox.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}