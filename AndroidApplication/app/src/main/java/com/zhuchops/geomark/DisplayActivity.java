package com.zhuchops.geomark;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.ActivityDisplayBinding;
public class DisplayActivity extends AppCompatActivity
        implements NavigationBarFragment.OnChangeWindowListener,
                    LayersListFragment.OnAddNewLayerListener {
    private MapView mapView;
    private BoxClass box;
    public int activeWindowId;
    NavigationBarFragment navigationBarFragment;

    ActivityDisplayBinding activityDisplayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("0a98fb9d-4b92-4861-a005-87e3998b9d96");
        MapKitFactory.initialize(this);

        activityDisplayBinding = ActivityDisplayBinding.inflate(this.getLayoutInflater());
        setContentView(activityDisplayBinding.getRoot());

        activeWindowId = R.id.mapFragment;
        navigationBarFragment = (NavigationBarFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigationBarFragment);
    }

    @Override
    public void onChangeWindow(int windowId) {
        FragmentContainerView activeFragment = (FragmentContainerView) findViewById(activeWindowId);
        FragmentContainerView nextActiveFragment = (FragmentContainerView) findViewById(windowId);
        activeFragment.setVisibility(View.GONE);
        nextActiveFragment.setVisibility(View.VISIBLE);
        navigationBarFragment.setNewActivated(this.activeWindowId, windowId);
        this.activeWindowId = windowId;
    }

    @Override
    public void onAddNewLayer(GeoLayer layer) {
        onChangeWindow();
    }
}