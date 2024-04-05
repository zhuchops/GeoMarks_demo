package com.zhuchops.geomark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<LayerElement> layers;

    public LayerAdapter(Context context, List<LayerElement> layers) {
        this.layers = layers;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (LayerAdapter.ViewHolder holder, int position) {
        LayerElement layerElement = layers.get(position);
        holder.imageView.setImageResource(R.drawable.no_image_sign);
        holder.nameView.setText(layerElement.getName());
        holder.descriptionView.setText(layerElement.getDescription());
        holder.checkBox.setActivated(layerElement.isChecked());
    }

    @Override
    public int getItemCount() {
        return layers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;
        final TextView descriptionView;
        final CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageViewOfLayer);
            nameView = view.findViewById(R.id.nameViewOfLayer);
            descriptionView = view.findViewById(R.id.descriptionViewOfList);
            checkBox = view.findViewById(R.id.checkBoxOfList);
        }
    }
}
