package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhuchops.geomark.databinding.FragmentNavigationBarBinding;

import java.util.HashMap;

public class NavigationBarFragment extends Fragment implements View.OnClickListener {

    private FragmentNavigationBarBinding binding;
    private DisplayActivity activity;
    public final HashMap<Integer, Class<?>> buttonToFragmentsHash = new HashMap<>();
    public final HashMap<Class<?>, Integer> fragmentsToButtonsHash = new HashMap<>();

    public Class<?> activeFragment;

    interface OnChangeWindowListener {
        void onChangeWindow(Class<?> activeFragment);
    }

    private OnChangeWindowListener changeWindowListener;

    public NavigationBarFragment() {
        super(R.layout.fragment_navigation_bar);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
        try {
            changeWindowListener = (OnChangeWindowListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNavigationBarBinding.inflate(inflater, container, false);
        activeFragment = MapFragment.class;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < binding.buttonsContainer.getChildCount(); i++) {
            ImageButton imageButton = (ImageButton) binding.buttonsContainer.getChildAt(i);
            imageButton.setOnClickListener(this);
        }
        binding.mapButton.setActivated(true);

        buttonToFragmentsHash.put(R.id.map_button, MapFragment.class);
        buttonToFragmentsHash.put(R.id.layers_button, LayersListFragment.class);
        buttonToFragmentsHash.put(R.id.profile_button, MapFragment.class);
        fragmentsToButtonsHash.put(MapFragment.class, R.id.map_button);
        fragmentsToButtonsHash.put(LayersListFragment.class, R.id.layers_button);
    }

    @Override
    public void onClick(View view) {
        changeWindowListener.onChangeWindow(buttonToFragmentsHash.get(view.getId()));
    }

    public void setNewActivated(Class<?> newId) {
        if (activeFragment.equals(MapFragment.class)) {
            binding.mapButton.setActivated(false);
        }
        else if (activeFragment.equals(LayersListFragment.class)) {
            binding.layersButton.setActivated(false);
        }
        if (newId.equals(MapFragment.class)) {
            binding.mapButton.setActivated(true);
            activeFragment = MapFragment.class;
        }
        else if (newId.equals(LayersListFragment.class)) {
            binding.layersButton.setActivated(true);
            activeFragment = LayersListFragment.class;
        }
    }
}
