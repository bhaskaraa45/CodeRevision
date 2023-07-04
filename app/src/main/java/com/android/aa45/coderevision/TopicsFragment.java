package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.aa45.coderevision.Adapters.recyclerAdapter_Topics;
import com.android.aa45.coderevision.Adapters.recyclerViewAdapter;
import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.Fragments.SolvedFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TopicsFragment extends Fragment {


    private recyclerAdapter_Topics viewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_topics, container, false);
        HashMap<String, Integer> hm = SolvedFragment.topicFreq;

        if (hm != null) {

            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewGrid);
            swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout_topics);

            List<DataHolder> items = SolvedFragment.ItemList;

            viewAdapter = new recyclerAdapter_Topics(getContext(), hm, items);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(viewAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                viewAdapter.notifyDataSetChanged();
                refreshFragment(new TopicsFragment());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
        return rootView;
    }

    private void refreshFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}