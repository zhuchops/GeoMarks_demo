package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhuchops.geomark.databinding.FragmentUpperPanelBinding;

public class UpperPanelFragment extends Fragment
                                implements View.OnClickListener {

    static final int MAP_MODE = 0;
    static final int LAYERS_LIST_MODE = 1;
    static final int LAYER_VIEW_TEXT_MODE = 2;
    static final int LAYER_VIEW_MAP_MODE = 3;
    static final int EDITING_STATE = 0;
    static final int READING_STATE = 1;

    private int currentMode;

    private int currentState;

    DisplayActivity activity;
    FragmentUpperPanelBinding binding;

    public interface OnChangeEditModeOfDescriptionListener {
        void onBeginEdit();
        void onEndEdit();
    }
    OnChangeEditModeOfDescriptionListener onChangeEditModeOfDescriptionListener;

    interface OnGetLayerNameListener {
        String getLayerName();
    }
    OnGetLayerNameListener onGetLayerNameListener;

    @Override
    public void onClick(View v) {
        if (v.equals(binding.settingsButton)) {
            if (currentMode == LAYER_VIEW_TEXT_MODE) {
                if (currentState == READING_STATE) {
                    if (binding.settingOptionsLayerTextViewContainer.getVisibility() == View.GONE)
                        binding.settingOptionsLayerTextViewContainer.setVisibility(View.VISIBLE);
                    else binding.settingOptionsLayerTextViewContainer.setVisibility(View.GONE);
                } else if (currentState == EDITING_STATE) {
                    activity.onEndEdit();
                    binding.settingsButton.
                            setImageDrawable(activity.getDrawable(R.drawable.option_gear_button));
                    currentState = READING_STATE;
                }
            }
        }
        if (v.equals(binding.changeDescriptionButton)) {
            if (currentState == READING_STATE) {
                activity.onBeginEdit();
                binding.settingsButton.setImageDrawable(activity.getDrawable(R.drawable.check_icon_done));
                currentState = EDITING_STATE;
                binding.settingsButton.setActivated(true);
                binding.settingOptionsLayerTextViewContainer.setVisibility(View.GONE);
            }
        }
    }

    public UpperPanelFragment() {
        super(R.layout.fragment_upper_panel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
        try {
            onChangeEditModeOfDescriptionListener = (OnChangeEditModeOfDescriptionListener) context;
            onGetLayerNameListener = (OnGetLayerNameListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " должен реализовывать интерфейс OnGetLayerNameListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpperPanelBinding.inflate(inflater, container, false);
        currentState = READING_STATE;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.settingsButton.setOnClickListener(this);
        binding.changeDescriptionButton.setOnClickListener(this);
    }

    public void setMode(int mode) {
        currentMode = mode;
        update();
    }

    private void update() {
        if (currentMode == MAP_MODE) {
            binding.layerNameView.setText(R.string.mapNameForUpperPanel);
            binding.upperBg.setBackground(activity.getDrawable(R.color.invisible));
        } else if (currentMode == LAYERS_LIST_MODE) {
            binding.layerNameView.setText(R.string.layersListNameForUpperPanel);
            binding.upperBg.setBackground(activity.getDrawable(R.color.upper_panel_bg));
        } else if (currentMode == LAYER_VIEW_TEXT_MODE) {
            binding.layerNameView.setText(onGetLayerNameListener.getLayerName());
            binding.upperBg.setBackground(activity.getDrawable(R.color.upper_panel_bg));
        } else if (currentMode == LAYER_VIEW_MAP_MODE) {
            binding.layerNameView.setText(onGetLayerNameListener.getLayerName());
            binding.upperBg.setBackground(activity.getDrawable(R.color.invisible));
        }
    }
}
