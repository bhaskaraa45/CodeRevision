package com.bhaskar.aa45.coderevision.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhaskar.aa45.coderevision.MeFragment;
import com.bhaskar.aa45.coderevision.R;

import java.util.Objects;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("About");

        TextView bhaskar = view.findViewById(R.id.bhaskar);
        TextView github = view.findViewById(R.id.githubLink);

        TextView text1 = view.findViewById(R.id.textView4);
        TextView text2 = view.findViewById(R.id.textView2);
        TextView text3 = view.findViewById(R.id.textView5);
        RelativeLayout parent = view.findViewById(R.id.parent_about);

        //if darkMode
        if(MeFragment.isDark){
            int white = getResources().getColor(R.color.white);
            parent.setBackgroundColor(getResources().getColor(R.color.primary));
            text1.setTextColor(white);
            text2.setTextColor(white);
            text3.setTextColor(white);
        }else{//if light mode
            int black = getResources().getColor(R.color.black);
            parent.setBackgroundColor(getResources().getColor(R.color.white));
            text1.setTextColor(black);
            text2.setTextColor(black);
            text3.setTextColor(black);
        }

        bhaskar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkedIn = new Intent(Intent.ACTION_VIEW , Uri.parse("https://linkedin.com/in/bhaskaraa45"));
                startActivity(linkedIn);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bhaskaraa45/CodeRevision"));
                startActivity(goToGithub);
            }
        });
        return view;
    }
}