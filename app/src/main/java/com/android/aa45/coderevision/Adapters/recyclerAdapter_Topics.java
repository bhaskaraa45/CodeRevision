package com.android.aa45.coderevision.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.R;

import java.util.HashMap;
import java.util.List;

public class recyclerAdapter_Topics extends RecyclerView.Adapter<recyclerAdapter_Topics.ViewHolder> {
    Context context;
    HashMap<String,Integer> hm;

    public recyclerAdapter_Topics(Context context,HashMap<String,Integer> hm) {
        this.context = context;
        this.hm = hm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topics_grid_layout, parent, false);
        return new recyclerAdapter_Topics.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = (String) hm.keySet().toArray()[position];
        holder.topic_grid.setText(key);
        Integer freq = hm.get(key);
        int fr = freq==null?0:freq;
        String str = "Solved : "+fr;
        holder.totalNo.setText(str);

    }

    @Override
    public int getItemCount() {
        return hm.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView totalNo ;
        public TextView topic_grid ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalNo = itemView.findViewById(R.id.totalNo);
            topic_grid = itemView.findViewById(R.id.topic_grid);
        }
    }
}
