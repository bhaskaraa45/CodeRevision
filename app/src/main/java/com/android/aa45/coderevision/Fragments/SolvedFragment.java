package com.android.aa45.coderevision.Fragments;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aa45.coderevision.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SolvedFragment extends Fragment {

    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_solved, container, false);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference=db.getReference();
        return view;
    }


}
