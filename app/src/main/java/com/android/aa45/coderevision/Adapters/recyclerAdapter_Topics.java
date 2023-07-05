package com.android.aa45.coderevision.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class recyclerAdapter_Topics extends RecyclerView.Adapter<recyclerAdapter_Topics.ViewHolder> {
    Context context;
    HashMap<String,Integer> hm;
    List<DataHolder> allItems;
    private recyclerViewAdapter viewAdapter ;
    private SwipeRefreshLayout swipeRefreshLayout;


    public recyclerAdapter_Topics(Context context,HashMap<String,Integer> hm,List<DataHolder> allItems) {
        this.context = context;
        this.hm = hm;
        this.allItems=allItems;
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
        String str = "Solved :  "+fr;
        holder.totalNo.setText(str);

        holder.gridCard.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
            dialog.setContentView(R.layout.dialog_topics_sections);

            RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView_topics);
            ImageView backTopic = dialog.findViewById(R.id.back_to_topic);
            TextView topicName = dialog.findViewById(R.id.topic_name);

            List<DataHolder> sortedList = new ArrayList<>();

            for (DataHolder item : allItems){
                if(Objects.equals(item.getTag(), key)){
                    sortedList.add(item);
                }
            }
            topicName.setText(key);

            backTopic.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO){
                backTopic.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow_black));
            }

            viewAdapter = new recyclerViewAdapter(sortedList,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(viewAdapter);

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return hm.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView totalNo ;
        public TextView topic_grid ;
        public CardView gridCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalNo = itemView.findViewById(R.id.totalNo);
            topic_grid = itemView.findViewById(R.id.topic_grid);
            gridCard = itemView.findViewById(R.id.GridCard);
        }
    }



}
