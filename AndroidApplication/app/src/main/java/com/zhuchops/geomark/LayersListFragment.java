package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhuchops.geomark.databinding.FragmentLayersListBinding;

import java.util.ArrayList;

public class LayersListFragment extends Fragment implements View.OnClickListener {

    FragmentLayersListBinding binding;
    DisplayActivity activity;

    interface OnAddNewLayerListener {
        void onAddNewLayer(GeoLayer layer);
    }

    private OnAddNewLayerListener onAddNewLayerListener;

    public LayersListFragment() {
        super(R.layout.fragment_layers_list);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
        try {
            onAddNewLayerListener = (OnAddNewLayerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
            + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLayersListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addLayerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addLayerButton) {
            BoxClass box = BoxClass.getInstance();
            GeoLayer newLayer = new GeoLayer(
                    IdGenerator.generateId(),
                    "New Layer",
                    "",
                    new ArrayList<GeoMark>()
            );
            box.addNewLayer(newLayer);
            onAddNewLayerListener.onAddNewLayer(newLayer);
        }
    }

}
