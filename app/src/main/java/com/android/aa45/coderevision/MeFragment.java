package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.util.Objects;


@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MeFragment extends Fragment {
    private RelativeLayout settings;
    private RelativeLayout feedback;
    private RelativeLayout share;
    private RelativeLayout logout;

    private ImageView settIcon ;
    private ImageView darkIcon;

    private ImageView fbackIcon ;
    private ImageView shareIcon;
    private ImageView lgoutIcon;

    //for darkmode
    private Switch switcher;
    boolean darkMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        settings = rootView.findViewById(R.id.settings);
        feedback = rootView.findViewById(R.id.feedback);
        share = rootView.findViewById(R.id.share);
        logout = rootView.findViewById(R.id.logout);
        settIcon = rootView.findViewById(R.id.settingsIcon);
        fbackIcon = rootView.findViewById(R.id.feedbackIcon);
        shareIcon = rootView.findViewById(R.id.shareIcon);
        lgoutIcon = rootView.findViewById(R.id.logoutIcon);

        switcher = rootView.findViewById(R.id.switcher);

        sharedPreferences = requireActivity().getSharedPreferences("MODE" , Context.MODE_PRIVATE);
        darkMode = sharedPreferences.getBoolean("night",false);
        if(darkMode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(darkMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });
        return rootView;
    }
}