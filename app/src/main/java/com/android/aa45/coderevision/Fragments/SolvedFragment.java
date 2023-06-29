package com.android.aa45.coderevision.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.aa45.coderevision.Adapters.recyclerViewAdapter;
import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.Firebase.dataShowing;
import com.android.aa45.coderevision.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SolvedFragment extends Fragment {

    private Toolbar toolbar;
    private List<dataShowing> ItemList;
    private recyclerViewAdapter viewAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_solved, container, false);

        ItemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference uidRef = db.getReference().child("user").child(uid).child("Solved"); //root->user->uid->solved

        uidRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ItemList.clear();

                for (DataSnapshot userSnap : snapshot.getChildren()){
                    dataShowing dataS = userSnap.getValue(dataShowing.class);
                    ItemList.add(dataS);
                }
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewAdapter = new recyclerViewAdapter(ItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewAdapter);

        return view;
    }


}
