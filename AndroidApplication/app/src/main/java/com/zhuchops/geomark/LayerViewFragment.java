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
    Fragment activeWindow;

    public void onOptionClick(String text) {
        if (activeWindow instanceof LayerViewTextFragment) {
            ((LayerViewTextFragment) activeWindow).onOptionClick(text);
        }
    }

    interface OnSaveListener {
        void saveLayer(GeoLayer layer);
    }

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

        activeWindow = textFragment;
        binding.toTextViewButton.setActivated(true);

        binding.viewPager.setUserInputEnabled(false);

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
            if (activeWindow.equals(textFragment)) {
                binding.viewPager.setCurrentItem(1);
                activeWindow = mapFragment;
                binding.toTextViewButton.setActivated(false);
                binding.toMapViewButton.setActivated(true);
            }
        }
        if (v.equals(binding.toTextViewButton)) {
            if (activeWindow.equals(mapFragment)) {
                binding.viewPager.setCurrentItem(0);
                activeWindow = textFragment;
                binding.toMapViewButton.setActivated(false);
                binding.toTextViewButton.setActivated(true);
            }
        }
    }

    public void setDescriptionEditable(boolean mode) {
        textFragment.setDescriptionEditable(mode);
    }

    @Override
    public void saveLayer(GeoLayer layer) {
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

    public void saveMark(GeoMark mark) {
        layer.changeMark(mark.getNumber() - 1, mark);
        saveLayer(this.layer);
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

    public void onOpenMark(GeoMark mark) {
        mapFragment.onOpenMark(mark);
    }
}
