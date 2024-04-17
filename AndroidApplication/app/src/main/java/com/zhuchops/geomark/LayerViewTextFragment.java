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

public class LayerViewTextFragment extends Fragment {

    private FragmentLayerViewTextBinding binding;
    private DisplayActivity activity;
    private BoxClass box;
    private GeoLayer layer;

    interface OnSaveListener {
        void save(GeoLayer layer);
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
            binding.editableDescriptionOfLayer.setText(
                    binding.descriptionOfLayer.getText()
            );
            binding.editableDescriptionOfLayer.setVisibility(View.VISIBLE);
            binding.descriptionOfLayer.setVisibility(View.GONE);
        } else {
            binding.descriptionOfLayer.setText(
                    binding.editableDescriptionOfLayer.getText()
            );
            binding.editableDescriptionOfLayer.setVisibility(View.GONE);
            binding.descriptionOfLayer.setVisibility(View.VISIBLE);
            save();
        }
    }

    public void save() {
        activity.save(
                new GeoLayer(
                        layer.getId(),
                        layer.getImageData(),
                        layer.getName(),
                        binding.descriptionOfLayer.getText().toString(),
                        layer.getLayer())
        );
    }
}
