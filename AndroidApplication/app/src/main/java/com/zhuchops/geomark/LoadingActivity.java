package com.zhuchops.geomark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.mapview.MapView;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

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

        VectorDrawable layers_icon =
                (VectorDrawable) this.getDrawable(R.drawable.layers_icon);

        if (layers_icon != null) {
            layers_icon.setTintList(this.getColorStateList(R.color.navigation_icons_color_selector));
        }

        VectorDrawable done_button =
                (VectorDrawable) this.getDrawable(R.drawable.check_icon_done);

        if (done_button != null) {
            done_button.setTintList(this.getColorStateList(R.color.done_button_selector));
        }

        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);

        try {
            mainBox = BoxClass.getInstance(this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
//        mapView = findViewById(R.id.display_layout).findViewById(R.id.mapView);

        // reminding of previous active layers
        if (!sharedPreferences.getString(
                getString(R.string.getIdFromSharedPreferences), "").equals("")) {
            List<String> ids = Arrays.asList(sharedPreferences.getString(
                    getString(R.string.getIdFromSharedPreferences), ""
            ).split(";"));
            for (int i = 0; i < ids.size(); i++) {
                mainBox.addIdToActiveIds(ids.get(i));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LOADING ACTIVITY", "start working");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i("LOADING ACTIVITY", "stop working");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}