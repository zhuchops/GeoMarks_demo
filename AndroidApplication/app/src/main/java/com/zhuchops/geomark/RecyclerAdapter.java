package com.zhuchops.geomark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<OuterItem> outerItems;
    private final LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<OuterItem> outerItems) {
        this.outerItems = outerItems;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        OuterItem outerItem = outerItems.get(position);
        holder.recyclerView.setAdapter(new LayerAdapter(inflater.getContext(), outerItem.getInnerItems()));
        }

    @Override
    public int getItemCount() {
        return outerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.recyclerList);
        }
    }
}

