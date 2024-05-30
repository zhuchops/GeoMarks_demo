package com.zhuchops.geomark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhuchops.geomark.databinding.FragmentMarkViewBinding;

import org.json.JSONException;

public class MarkViewFragment extends Fragment
                            implements View.OnClickListener {

    DisplayActivity activity;
    FragmentMarkViewBinding binding;
    BoxClass box;
    GeoMark mark;

    public MarkViewFragment() {
        super(R.layout.fragment_mark_view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMarkViewBinding.inflate(inflater, container, false);
        try {
            box = BoxClass.getInstance(null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.closeButton.setOnClickListener(this);
        binding.changeButton.setOnClickListener(this);
        update();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (DisplayActivity) getActivity();
    }

    public void setNewMark(GeoMark mark) {
        this.mark = mark;
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void update() {
        if (this.mark != null) {
            if (this.isVisible()) {
                binding.markNumberView.setText(mark.getNumber());
                binding.markNameView.setText(mark.getName());
                binding.coordinatesView.setText(
                        String.format("Координаты: %s, %s", mark.getX(), mark.getY()));
                binding.descriptionContainer.setText(mark.getDescription());
            }
        }
    }

    public void setDescriptionEditable(boolean mode) {
        binding.descriptionContainer.setEnabled(mode);
        if (!mode) {
            saveDataToMark();
        }
    }

    public void saveDataToMark() {
        this.mark = new GeoMark (
                mark.getX(),
                mark.getY(),
                mark.getNumber(),
                mark.getName(),
                binding.descriptionContainer.getText().toString()
        );
    }

    public void save() {
        activity.saveMark(mark);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.closeButton) {
            close();
        }
        // добавь действие кнопки изменить
        if (v.getId() == R.id.changeButton) {
            Button changeButton = null;
            if (v instanceof Button) {
                changeButton = (Button) v;
            }
            if (changeButton.getTag().toString().equals(getString(R.string.none_editable))) {
                binding.descriptionContainer.setEnabled(true);
                binding.descriptionContainer.requestFocus();
                changeButton.setTag(getString(R.string.editable));
                changeButton.setText(getString(R.string.admit_changes_text));
            } else if (binding.changeButton.getTag().toString().equals(getString(R.string.editable))) {
                binding.descriptionContainer.setEnabled(false);
                binding.changeButton.setTag(getString(R.string.none_editable));
                binding.changeButton.setTag(getString(R.string.change_text));
                saveDataToMark();
            }
        }
    }

    public void close() {
        activity.onCloseMark();
    }
}
