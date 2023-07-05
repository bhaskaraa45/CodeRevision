package com.android.aa45.coderevision.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aa45.coderevision.R;

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