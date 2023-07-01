package com.android.aa45.coderevision.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.MainActivity;
import com.android.aa45.coderevision.R;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    Context mainContext;
    List<DataHolder> items ;
    Activity activity;
    Context context;

    public recyclerViewAdapter(List<DataHolder> items,Context context) {
        this.items = items;
        this.context =context;
    }
    int redColor,blueColor,greenColor,whiteColor,orangeColor;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        redColor = parent.getContext().getResources().getColor(R.color.red);
        blueColor = parent.getContext().getResources().getColor(R.color.blue);
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
        holder.setSlNo.setText((position+1)+".");
        holder.setDiff.setText(dataHolder.getDifficulty());
        holder.setTopic.setText(dataHolder.getTag());
        holder.setTopic.setTextColor(blueColor);
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

        mainContext = MainActivity.MainActContext.get(0);

        holder.rowCard.setOnClickListener(view -> {
            Dialog dialog = new Dialog(mainContext,R.style.Base_Theme_CodeRevision);
            dialog.setContentView(R.layout.dialog_row_card);

            TextView slPlusTitle = dialog.findViewById(R.id.sl_plus_title);
            TextView topic = dialog.findViewById(R.id.topic);
            TextView difficulty = dialog.findViewById(R.id.difficulty);
            TextView date = dialog.findViewById(R.id.date);
            TextView link = dialog.findViewById(R.id.link);
            WebView code = dialog.findViewById(R.id.code);
            RelativeLayout edit = dialog.findViewById(R.id.edit);
            RelativeLayout delete = dialog.findViewById(R.id.delete);
            ImageView back = dialog.findViewById(R.id.back);

            link.setText(dataHolder.getLink());
            slPlusTitle.setText((position+1) + ". "+dataHolder.getTitle());
            topic.setText("Topic : " + dataHolder.getTag());
            date.setText("Date : " + dataHolder.getDate());
            difficulty.setText("Difficulty : "+dataHolder.getDifficulty());


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url;
                    if (!dataHolder.getLink().startsWith("http://") && !dataHolder.getLink().startsWith("https://")) {
                        url = "http://" + dataHolder.getLink();
                    }else{
                        url = dataHolder.getLink();
                    }
                    Intent problemLink = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(problemLink);
                }
            });


            dialog.show();
        });
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
        public CardView rowCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setTitle = itemView.findViewById(R.id.setTitle);
            setSlNo = itemView.findViewById(R.id.setSlNo);
            setDate = itemView.findViewById(R.id.setDate);
            setTopic = itemView.findViewById(R.id.setTopic);
            setDiff = itemView.findViewById(R.id.setDiff);
            rowCard = itemView.findViewById(R.id.rowCard);
        }
    }

}
