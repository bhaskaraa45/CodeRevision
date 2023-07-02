package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aa45.coderevision.Adapters.recyclerAdapter_Topics;
import com.android.aa45.coderevision.Adapters.recyclerViewAdapter;
import com.android.aa45.coderevision.Fragments.SolvedFragment;

import java.util.HashMap;

public class TopicsFragment extends Fragment {
    private recyclerAdapter_Topics viewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_topics, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewGrid);

        HashMap<String,Integer> hm = SolvedFragment.topicFreq;

        viewAdapter = new recyclerAdapter_Topics(getContext(),hm);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(viewAdapter);
        return rootView;
    }
}