package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zhuchops.geomark.databinding.FragmentLayerViewBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LayerViewFragment extends Fragment
                                implements View.OnClickListener,
                                           LayerViewTextFragment.OnSaveListener {

    FragmentLayerViewBinding binding;
    GeoLayer layer;

    LayerViewMapFragment mapFragment;
    LayerViewTextFragment textFragment;
    DisplayActivity activity;
    Fragment active_window;

    public static final List<Fragment> fragmentList = new ArrayList<>();

    public LayerViewFragment() {
        super(R.layout.fragment_layer_view);
    }

    public String getLayerName() {
        return layer.getName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Fragment", "LayerViewFragment: is onCreateView");
        binding = FragmentLayerViewBinding.inflate(inflater, container, false);
        mapFragment = new LayerViewMapFragment();
        textFragment = new LayerViewTextFragment();

        fragmentList.add(textFragment);
        fragmentList.add(mapFragment);

        active_window = textFragment;
        binding.toTextViewButton.setActivated(true);

        binding.viewPager.setUserInputEnabled(false);
//        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                if (!fragmentList.get(position).equals(active_window))
//                    switch (position) {
//                        case 0:
//                            onClick(binding.toTextViewButton);
//                        case 1:
//                            onClick(binding.toMapViewButton);
//                    }
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Fragment", "LayerViewFragment: is onViewCreated");

        binding.viewPager.setAdapter(new FixedFragmentStateAdapter(activity));

        binding.toMapViewButton.setOnClickListener(this);
        binding.toTextViewButton.setOnClickListener(this);

        update();
    }

    public void setNewLayer(GeoLayer layer) {
        Log.i("Fragment", "LayerViewFragment: is setNewLayer");
        this.layer = layer;
        update();
    }

    public void update() {
        Log.i("Fragment", "LayerViewFragment: is update");
        mapFragment.setNewLayer(layer);
        textFragment.setNewLayer(layer);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(binding.toMapViewButton)) {
            if (active_window.equals(textFragment)) {
                binding.viewPager.setCurrentItem(1);
                active_window = mapFragment;
                binding.toTextViewButton.setActivated(false);
                binding.toMapViewButton.setActivated(true);
                activity.updateUpperPanel(mapFragment.getClass());
            }
        }
        if (v.equals(binding.toTextViewButton)) {
            if (active_window.equals(mapFragment)) {
                binding.viewPager.setCurrentItem(0);
                active_window = textFragment;
                binding.toMapViewButton.setActivated(false);
                binding.toTextViewButton.setActivated(true);
                activity.updateUpperPanel(textFragment.getClass());
            }
        }
    }

    public void setDescriptionEditable(boolean mode) {
        textFragment.setDescriptionEditable(mode);
    }

    @Override
    public void save(GeoLayer layer) {
        this.layer = layer;
        try {
            BoxClass.getInstance(null)
                    .changeGeoLayer(
                            layer.getId(),
                            layer
                    );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        setNewLayer(layer);
    }

    private static class FixedFragmentStateAdapter extends FragmentStateAdapter {

        public FixedFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }

    public String getIdOfLayer() {
        return layer.getId();
    }
}
