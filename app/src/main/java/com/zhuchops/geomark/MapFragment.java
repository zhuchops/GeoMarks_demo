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
import com.zhuchops.geomark.databinding.FragmentMapBinding;

import org.json.JSONException;

public class MapFragment extends Fragment
        implements CameraListener {

    private static final String name = "mapFragment";
    private DisplayActivity displayActivity;
    private MapView mapView;
    private FragmentMapBinding fragmentMapBinding;
    private BoxClass box;

    public MapFragment() {
        super(R.layout.fragment_map);
    }

    public static String getName() {
        return name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false);
        try {
            box = BoxClass.getInstance(null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        displayActivity = (DisplayActivity) getActivity();
        return fragmentMapBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = fragmentMapBinding.mapView;
        mapView.getMapWindow().getMap().move(mapView.getMapWindow().getMap().getCameraPosition());

        mapView.getMapWindow().getMap().addCameraListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MarkManager.placeActiveMarks(
                fragmentMapBinding.marksLayout, box, mapView
        );
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onCameraPositionChanged(
            @NonNull Map map, @NonNull CameraPosition cameraPosition,
            @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        MarkManager.placeActiveMarks(
                fragmentMapBinding.marksLayout, box, mapView
        );
    }

    public MapView getMapView() {
        return mapView;
    }
}
