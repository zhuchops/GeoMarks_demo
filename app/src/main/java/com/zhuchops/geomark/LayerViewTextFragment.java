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

import com.zhuchops.geomark.databinding.FragmentLayerViewTextBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class LayerViewTextFragment extends Fragment {

    private FragmentLayerViewTextBinding binding;
    private DisplayActivity activity;
    private BoxClass box;
    private GeoLayer layer;

    public void onOptionClick(String text) {
        if (text.equals(getString(R.string.change_description_text))) {
            setDescriptionEditable(true);
        } else if (text.equals(getString(R.string.delete_layer_text))) {
            deleteCurrentLayer();
        } else if (text.equals(getString(R.string.change_name_text))) {

        } else if (text.equals(getString(R.string.change_image_text))) {

        }
    }

    interface OnSaveListener {
        void saveLayer(GeoLayer layer);
        void saveMark(GeoMark mark);
    }

    public LayerViewTextFragment() {
        super(R.layout.fragment_layer_view_text);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLayerViewTextBinding.inflate(inflater, container, false);
        Log.i("Fragment", "LayerViewTextFragment: is onCreateView");
        try {
            box = BoxClass.getInstance(null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("Fragment", "LayerViewTextFragment: is onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        update();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.change_name_text));
        options.add(getString(R.string.change_image_text));
        options.add(getString(R.string.change_description_text));
        options.add(getString(R.string.delete_layer_text));
        activity.updateUpperPanel(LayersListFragment.class, options);
    }

    public void setNewLayer(GeoLayer layer) {
        Log.i("Fragment", "LayerViewTextFragment: is setNewLayer");
        this.layer = layer;
        if (this.isVisible()) {
            update();
        }
    }

    public void update() {
        Log.i("Fragment", "LayerViewTextFragment: is update");
        if (this.layer != null) {
            binding.image.setImageDrawable(activity.getDrawable(R.drawable.no_image_sign));
            binding.descriptionOfLayer.setText(layer.getDescription());
        }
    }

    public void setDescriptionEditable(boolean mode) {
        if (mode) {
            binding.descriptionOfLayer.setEnabled(true);
        } else {
            binding.descriptionOfLayer.setEnabled(false);
            saveDataToLayer();
            save();
        }
    }

    public void saveDataToLayer() {
        this.layer = new GeoLayer(
                layer.getId(),
                layer.getImageData(),
                layer.getName(),
                binding.descriptionOfLayer.getText().toString(),
                layer.getMarks()
        );
    }

    public void save() {
        activity.saveLayer(layer);
    }

    public void deleteCurrentLayer() {
        try {
            BoxClass.getInstance(null).deleteLayer(layer);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        activity.onChangeWindow(LayersListFragment.class);
    }
}
