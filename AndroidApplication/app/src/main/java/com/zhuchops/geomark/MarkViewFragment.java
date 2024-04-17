package com.zhuchops.geomark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhuchops.geomark.databinding.FragmentMarkViewBinding;

import org.json.JSONException;

public class MarkViewFragment extends Fragment {

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
    }

    public void setNewMark(GeoMark mark) {
        update();
    }

    public void update() {
        if (this.mark != null) {

        }
    }
}
