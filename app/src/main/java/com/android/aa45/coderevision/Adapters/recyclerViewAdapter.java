package com.android.aa45.coderevision.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.R;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    List<DataHolder> items ;

    public recyclerViewAdapter(List<DataHolder> items) {
        this.items = items;
    }
    int redColor,blueColor,greenColor,whiteColor,orangeColor;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        redColor = parent.getContext().getResources().getColor(R.color.red);
        blueColor = parent.getContext().getResources().getColor(R.color.blue_);
        greenColor = parent.getContext().getResources().getColor(R.color.green);
        whiteColor = parent.getContext().getResources().getColor(R.color.white);
        orangeColor = parent.getContext().getResources().getColor(R.color.orange);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataHolder dataHolder = items.get(position);
        holder.setTitle.setText(dataHolder.getTitle());
        holder.setDate.setText(dataHolder.getDate());
        holder.setSlNo.setText(dataHolder.getslNo()+".");
        holder.setDiff.setText(dataHolder.getDifficulty());
        holder.setTopic.setText(dataHolder.getTag());

        switch (dataHolder.getDifficulty()) {
            case "Hard":
                holder.setDiff.setTextColor(redColor);
                break;
            case "Easy":
            case "Medium":
                holder.setDiff.setTextColor(greenColor);
                break;
            default:
                holder.setDiff.setTextColor(whiteColor);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView setTitle;
        public TextView setSlNo;
        public TextView setDate;
        public TextView setTopic;
        public TextView setDiff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setTitle = itemView.findViewById(R.id.setTitle);
            setSlNo = itemView.findViewById(R.id.setSlNo);
            setDate = itemView.findViewById(R.id.setDate);
            setTopic = itemView.findViewById(R.id.setTopic);
            setDiff = itemView.findViewById(R.id.setDiff);
        }
    }
}
