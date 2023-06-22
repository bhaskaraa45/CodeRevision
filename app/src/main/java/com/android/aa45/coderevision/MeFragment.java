package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;


@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MeFragment extends Fragment {
    private RelativeLayout settings;
    private RelativeLayout feedback;
    private RelativeLayout share;
    private RelativeLayout logout;
    private RelativeLayout about;
    private RelativeLayout bugReport;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Profile");

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        settings = rootView.findViewById(R.id.settings);
        feedback = rootView.findViewById(R.id.feedback);
        share = rootView.findViewById(R.id.share);
        logout = rootView.findViewById(R.id.logout);
        bugReport = rootView.findViewById(R.id.bugReport);
        about = rootView.findViewById(R.id.about);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Feedback", Toast.LENGTH_SHORT).show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Log Out", Toast.LENGTH_SHORT).show();
            }
        });
        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Report a bug", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "About", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}