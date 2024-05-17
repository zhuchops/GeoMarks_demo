package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.FragmentLayerViewMapBinding;

import org.json.JSONException;

public class LayerViewMapFragment extends Fragment
        implements CameraListener,
                View.OnClickListener {

    private FragmentLayerViewMapBinding binding;
    private BoxClass box;
    private DisplayActivity activity;
    private MapView mapView;
    private MarkViewFragment markViewFragment;
    GeoLayer modifyingLayer;


    public LayerViewMapFragment() {
        super(R.layout.fragment_layer_view_map);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLayerViewMapBinding.inflate(inflater, container, false);
        try {
            box = BoxClass.getInstance(null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        markViewFragment = new MarkViewFragment();

        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.markViewLayout, markViewFragment)
                .hide(markViewFragment)
                .commit();

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = binding.mapView;
        mapView.getMapWindow().getMap().move(mapView.getMapWindow().getMap().getCameraPosition());
        update();

        mapView.getMapWindow().getMap().addCameraListener(this);

        binding.addMarkButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        update();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.updateUpperPanel(LayerViewMapFragment.class);
    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition,
                                        @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        update();
    }

    public MapView getMapView() {
        return mapView;
    }

    public void setNewLayer(GeoLayer layer) {
        modifyingLayer = layer;
        if (this.isVisible()) {
            update();
        }
    }

    private void update() {
        if (modifyingLayer != null) {
            MarkManager.placeLayersMark(binding.marksLayout, modifyingLayer, mapView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.addMarkButton)) {
            GeoMark mark = new GeoMark(
                    "0.0", "0.0",
                    modifyingLayer.getSize(),
                    "no name",
                    "no description"
            );
            modifyingLayer.addMark(mark);
            save();
            update();
            onOpenMark(mark);
        }
    }

    public void save() {
        activity.saveLayer(modifyingLayer);
    }

    public void onOpenMark(GeoMark mark) {
        activity.onOpenMark(mark);
    }
}