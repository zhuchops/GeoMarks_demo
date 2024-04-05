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
    private final HashMap<Integer, Integer> buttonToFragmentsHash = new HashMap<>();
    private final HashMap<Integer, Integer> fragmentsToButtonsHash = new HashMap<>();

    interface OnChangeWindowListener {
        void onChangeWindow(int windowId);
    }

    private OnChangeWindowListener changeWindowListener;

    public NavigationBarFragment() {
        super(R.layout.fragment_navigation_bar);
        buttonToFragmentsHash.put(R.id.map_button, R.id.mapFragment);
        buttonToFragmentsHash.put(R.id.layers_button, R.id.layersListFragment);
        buttonToFragmentsHash.put(R.id.profile_button, R.id.mapFragment);
        fragmentsToButtonsHash.put(R.id.mapFragment, R.id.map_button);
        fragmentsToButtonsHash.put(R.id.layersListFragment, R.id.layers_button);
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
    }

    @Override
    public void onClick(View view) {
        changeWindowListener.onChangeWindow(buttonToFragmentsHash.get(view.getId()));
    }

    public void setNewActivated(int oldId, int newId) {
        activity.findViewById(fragmentsToButtonsHash.get(oldId)).setActivated(false);
        activity.findViewById(fragmentsToButtonsHash.get(newId)).setActivated(true);
    }
}
