package com.zhuchops.geomark;

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
import com.zhuchops.geomark.databinding.FragmentNewLayerBinding;

public class NewLayerFragment extends Fragment implements CameraListener {

    private FragmentNewLayerBinding binding;
    private BoxClass box;
    private DisplayActivity displayActivity;
    private MapView mapView;

    GeoLayer modifyingLayer;

    public NewLayerFragment(GeoLayer modifyingLayer) {
        super(R.layout.fragment_new_layer);
        this.modifyingLayer = modifyingLayer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewLayerBinding.inflate(inflater, container, false);
        displayActivity = (DisplayActivity) getActivity();
        box = BoxClass.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = binding.mapView;
        mapView.getMapWindow().getMap().move(mapView.getMapWindow().getMap().getCameraPosition());

        mapView.getMapWindow().getMap().addCameraListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MarkManager.placeLayersMark(binding.marksLayout, this.modifyingLayer, mapView);
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition,
                                        @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        MarkManager.placeLayersMark(binding.marksLayout, this.modifyingLayer, mapView);
    }

    public MapView getMapView() {
        return mapView;
    }
}