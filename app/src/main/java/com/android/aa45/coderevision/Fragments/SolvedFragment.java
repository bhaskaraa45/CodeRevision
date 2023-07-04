package com.android.aa45.coderevision.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aa45.coderevision.Adapters.recyclerViewAdapter;
import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SolvedFragment extends Fragment {
    public static HashMap<String , Integer> topicFreq;

    public static List<DataHolder> ItemList;
    private recyclerViewAdapter viewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_solved, container, false);

    swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

    if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
        ItemList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference uidRef = db.getReference().child("user").child(uid).child("Solved"); //root->user->uid->solved

        uidRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemList.clear();
                topicFreq =new HashMap<>();

                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    DataHolder dataS = userSnap.getValue(DataHolder.class);
                    ItemList.add(dataS);
                    String tag = ItemList.get(ItemList.size()-1).getTag();
                    if(topicFreq.containsKey(tag)){
                        Integer currCount = topicFreq.get(tag);
                        int newCount = currCount==null? 1 : currCount+1;
                        topicFreq.put(tag, (newCount));

                    }else{
                        topicFreq.put(tag , 1);
                    }
                }
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewAdapter = new recyclerViewAdapter(ItemList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewAdapter);

    }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                viewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }


}
