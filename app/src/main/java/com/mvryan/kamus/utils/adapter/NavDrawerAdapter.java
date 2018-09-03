package com.mvryan.kamus.utils.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvryan.kamus.R;
import com.mvryan.kamus.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by mvryan on 29/08/18.
 */

public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.NavDrawerViewHolder> {

    List<NavDrawerItem> items = Collections.emptyList();

    public NavDrawerAdapter(List<NavDrawerItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NavDrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_drawer, parent,false);
        NavDrawerViewHolder myViewHolder = new NavDrawerViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NavDrawerViewHolder holder, int position) {
        NavDrawerItem item = items.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NavDrawerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public NavDrawerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_nav_drawer);
        }
    }
}
