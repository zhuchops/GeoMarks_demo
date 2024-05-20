package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.zhuchops.geomark.databinding.FragmentChooseLocationBinding;

import java.util.HashMap;

public class ChooseLocationFragment extends Fragment
        implements CameraListener, View.OnClickListener {

    private FragmentChooseLocationBinding binding;
    private DisplayActivity activity;
    private MapView mapView;

    public interface OnAdmitListener {
        public void onAdmit(HashMap<String, String> result);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseLocationBinding.inflate(inflater, container, false);

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

        mapView.getMapWindow().getMap().addCameraListener(this);

        binding.admitButton.setOnClickListener(this);
        binding.cancelButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition,
                                        @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
    }

    @Override
    public void onClick(View v) {
        HashMap<String, String> result = new HashMap<>();
        if (v.equals(binding.admitButton)) {
            String location = "";
            Point centerPoint = mapView.getMapWindow().getMap().getCameraPosition().getTarget();
            location = centerPoint.getLatitude() + ";" + centerPoint.getLongitude();
            result.put("result", "admit");
            result.put("location", location);
        } else if (v.equals(binding.cancelButton)) {
            result.put("result", "cancel");
            result.put("location", "");
        }

        activity.onAdmit(result);
    }
}
