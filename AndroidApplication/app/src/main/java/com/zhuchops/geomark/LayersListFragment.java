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

import org.json.JSONException;

import java.util.ArrayList;

public class LayersListFragment extends Fragment
        implements View.OnClickListener,
        LayerAdapter.OnItemClickListener {

    FragmentLayersListBinding binding;
    DisplayActivity activity;

    @Override
    public void onItemClick(String id) {
        GeoLayer layer;
        try {
            layer = BoxClass.getInstance(null).getLayerWithId(id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        activity.onOpenLayer(layer);
    }

    interface OnOpenWindowListener {
        void onOpenLayer(GeoLayer layer);
        void onOpenMark(GeoMark mark);
    }

    private OnOpenWindowListener onOpenWindowListener;

    public LayersListFragment() {
        super(R.layout.fragment_layers_list);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
        try {
            onOpenWindowListener = (OnOpenWindowListener) context;
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
        try {
            this.update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        binding.addLayerButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            update();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() throws JSONException {
        BoxClass box = BoxClass.getInstance(null);
        ArrayList<String> ids = box.getIds();
        ArrayList<String> activeIds = box.getIdsOfActiveLayers();
        ArrayList<OuterItem> outerItems = new ArrayList<>(2);
        ArrayList<LayerElement> myLayers = new ArrayList<>();
        GeoLayer layer;
        for (int i = 0; i < ids.size(); i++) {
            layer = box.getLayerWithId(ids.get(i));
            myLayers.add(new LayerElement(
                    layer.getId(),
                    layer.getImageData(),
                    layer.getName(),
                    layer.getDescription(),
                    activeIds.contains(ids.get(i))
            ));
        }
        OuterItem myLayersContainer = new OuterItem(myLayers);
        OuterItem otherLayersContainer = new OuterItem(new ArrayList<LayerElement>());
        outerItems.add(myLayersContainer);
        outerItems.add(otherLayersContainer);
        binding.viewPager.setAdapter(new ViewPagerAdapter(this.getContext(), outerItems));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addLayerButton) {
            BoxClass box = null;
            try {
                box = BoxClass.getInstance(null);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            byte[] imageData = getActivity().getDrawable(R.drawable.no_image_sign).toString().getBytes();
            GeoLayer newLayer = new GeoLayer(
                    IdGenerator.generateId(),
                    imageData,
                    "New Layer",
                    "Nothing here",
                    new ArrayList<GeoMark>()
            );
            box.addNewLayer(newLayer);
            onOpenWindowListener.onOpenLayer(newLayer);
            try {
                update();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
