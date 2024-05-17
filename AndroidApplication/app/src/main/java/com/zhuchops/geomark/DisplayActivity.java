package com.zhuchops.geomark;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.ActivityDisplayBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayActivity extends AppCompatActivity
        implements NavigationBarFragment.OnChangeWindowListener,
        LayersListFragment.OnOpenWindowListener,
                    UpperPanelFragment.OnGetLayerNameListener,
                    LayerAdapter.OnItemClickListener,
                    UpperPanelFragment.OnChangeEditModeOfDescriptionListener,
        LayerViewTextFragment.OnSaveListener {
    private MapView mapView;
    private BoxClass box;
    public Class<?> activeWindow;
    private MapFragment mapFragment;
    private LayersListFragment layersListFragment;
    private UpperPanelFragment upperPanelFragment;
    private NavigationBarFragment navigationBarFragment;
    private LayerViewFragment layerViewFragment;
    private MarkViewFragment markViewFragment;

    private final HashMap<Class<?>, Fragment> classToFragment = new HashMap<>();

    ActivityDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("0a98fb9d-4b92-4861-a005-87e3998b9d96");
        MapKitFactory.initialize(this);

        binding = ActivityDisplayBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            box = BoxClass.getInstance(this);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        BoxClass.setContext(this);

        navigationBarFragment = new NavigationBarFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.navigationBarFragment, navigationBarFragment)
                .commit();

        upperPanelFragment = new UpperPanelFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.upperPanel, upperPanelFragment)
                .commit();

        mapFragment = new MapFragment();
        layersListFragment = new LayersListFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activeFragment, mapFragment)
                .add(R.id.activeFragment, layersListFragment)
                .hide(layersListFragment)
                .commit();

        layerViewFragment = new LayerViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activeFragment, layerViewFragment)
                .hide(layerViewFragment)
                .commit();

        markViewFragment = new MarkViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.markViewLayout, markViewFragment)
                .hide(markViewFragment)
                .commit();

        classToFragment.put(MapFragment.class, mapFragment);
        classToFragment.put(LayersListFragment.class, layersListFragment);
        classToFragment.put(LayerViewFragment.class, layerViewFragment);
        activeWindow = MapFragment.class;
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onStop() {
        Log.i("DisplayActivity", "has stopped");
        try {
            box.onStop();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    public void updateUpperPanel(Class<?> window) {
        if (window.equals(MapFragment.class))
            upperPanelFragment.setMode(UpperPanelFragment.MAP_MODE);
        else if (window.equals(LayersListFragment.class))
            upperPanelFragment.setMode(UpperPanelFragment.LAYERS_LIST_MODE);
        else if (window.equals(LayerViewTextFragment.class))
            upperPanelFragment.setMode(UpperPanelFragment.LAYER_VIEW_TEXT_MODE);
        else if (window.equals(LayerViewMapFragment.class))
            upperPanelFragment.setMode(UpperPanelFragment.LAYER_VIEW_MAP_MODE);
    }

    public void updateUpperPanel(Class<?> window, ArrayList<String> options) {
        updateUpperPanel(window);
        upperPanelFragment.clearOptions();
        for (String option:
             options) {
            upperPanelFragment.addOption(option);
        }
    }

    @Override
    public void onChangeWindow(Class<?> window) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment activeFragment = classToFragment.get(activeWindow);
        Fragment newActiveFragment = classToFragment.get(window);
        transaction.hide(activeFragment).show(newActiveFragment).commit();
        navigationBarFragment.setNewActivated(newActiveFragment.getClass());
        activeWindow = newActiveFragment.getClass();
        updateUpperPanel(window);
    }

    @Override
    public void onOpenLayer(GeoLayer layer) {
        getSupportFragmentManager().beginTransaction()
                .hide(classToFragment.get(activeWindow))
                .show(layerViewFragment)
                .commit();
        layerViewFragment.setNewLayer(layer);
        activeWindow = LayerViewFragment.class;
        updateUpperPanel(LayerViewTextFragment.class);
    }

    @Override
    public void onOpenMark(GeoMark mark) {
        markViewFragment.setNewMark(mark);
        getSupportFragmentManager().beginTransaction()
                .show(markViewFragment)
                .commit();

        binding.markViewLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public String getLayerName() {
        return layerViewFragment.getLayerName();
    }

    @Override
    public void onItemClick(String id) {
        layersListFragment.onItemClick(id);
    }

    @Override
    public void onBeginEdit() {
        layerViewFragment.setDescriptionEditable(true);
    }

    @Override
    public void onEndEdit() {
        layerViewFragment.setDescriptionEditable(false);
    }

    @Override
    public void saveLayer(GeoLayer layer) {
        layerViewFragment.saveLayer(layer);
    }

    public void saveMark(GeoMark mark) {
        layerViewFragment.saveMark(mark);
    }

    public void onCloseMark() {
        getSupportFragmentManager().beginTransaction()
                .hide(markViewFragment)
                .commit();
    }

    public void onOptionClick(String text) {
        Fragment activeFragment = classToFragment.get(activeWindow);
        if (activeFragment instanceof LayerViewFragment) {
            ((LayerViewFragment) activeFragment).onOptionClick(text);
        }
    }
}