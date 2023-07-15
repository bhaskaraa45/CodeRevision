package com.bhaskar.aa45.coderevision;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bhaskar.aa45.coderevision.Adapters.recyclerViewAdapter;
import com.bhaskar.aa45.coderevision.Firebase.DataHolder;
import com.bhaskar.aa45.coderevision.Fragments.SolvedFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private recyclerViewAdapter viewAdapter;
    private int selectedPos =-1;
    private List<DataHolder> allData;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout spinner_rel_layout;
    LinearLayout parent;
    Spinner spinner;
    TextView text21;

    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search");

//        if(MeFragment.check!=-1)

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView_search);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_search);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.search_by, R.layout.spinner_text);
        spinner = (Spinner) view.findViewById(R.id.spinner_search);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner_rel_layout = view.findViewById(R.id.spinner_rel_layout);
        parent =view.findViewById(R.id.parent_search);
        text21 = view.findViewById(R.id.text21);
        uiModeChange(MeFragment.check);


        allData = new ArrayList<>();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            allData.addAll(SolvedFragment.ItemList);

            FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
            String uid = FirebaseAuth.getInstance().getUid();
            DatabaseReference myRef = db.getReference().child("user").child(uid); //root->user->uid
            DatabaseReference triedRef = myRef.child("Tried");
            DatabaseReference wishlistRef = myRef.child("Wishlist");

            //added tried data

            triedRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        DataHolder data = snap.getValue(DataHolder.class);
                        allData.add(data);
                    }
                    viewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //added wishlist's data
            wishlistRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        DataHolder data = snap.getValue(DataHolder.class);
                        allData.add(data);
                    }
                    viewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedPos =position;
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            List<DataHolder> newHolder = new ArrayList<>();
                            if(selectedPos ==1){
                                newHolder = searchByTopic(query);
                                setDataToRecyclerView(newHolder);
                            }else if(selectedPos==2){
                                newHolder = searchByDifficulty(query);
                                setDataToRecyclerView(newHolder);
                            }
                            else {
                                newHolder = searchByTitle(query);
                                setDataToRecyclerView(newHolder);
                            }

                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            List<DataHolder> newHolder = new ArrayList<>();
                            if(selectedPos ==1){
                                newHolder = searchByTopic(newText);
                                setDataToRecyclerView(newHolder);
                            }
                            else if(selectedPos==2){
                                newHolder = searchByDifficulty(newText);
                                setDataToRecyclerView(newHolder);
                            }else {
                                newHolder = searchByTitle(newText);
                                setDataToRecyclerView(newHolder);
                            }
                            return false;
                        }
                    });
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            setDataToRecyclerView(allData);
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

    private List<DataHolder> searchByTitle(String s) {
        s = s.toLowerCase();
        List<DataHolder> list = new ArrayList<>();
        for (DataHolder data : allData) {
            if (data.getTitle().toLowerCase().contains(s)) {
                list.add(data);
            }
        }
        return list;
    }

    private List<DataHolder> searchByTopic(String s) {
        s = s.toLowerCase();
        List<DataHolder> list = new ArrayList<>();
        for (DataHolder data : allData) {
            if (data.getTag().toLowerCase().contains(s)) {
                list.add(data);
            }

        }
        return list;
    }

    private List<DataHolder> searchByDifficulty(String s){
        s = s.toLowerCase();
        List<DataHolder> list = new ArrayList<>();
        for (DataHolder data : allData) {
            if (data.getDifficulty().toLowerCase().contains(s)) {
                list.add(data);
            }

        }
        return list;
    }

    private void setDataToRecyclerView(List<DataHolder> dataHolder){
        viewAdapter = new recyclerViewAdapter(dataHolder,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewAdapter);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void uiModeChange(int dark){
        if(dark==1){
            parent.setBackgroundColor(getResources().getColor(R.color.primary));
//            spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.color.primary));
            text21.setTextColor(getResources().getColor(R.color.white));
        }
        else if (dark==0){
            parent.setBackgroundColor(getResources().getColor(R.color.white));
//            spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.color.white));
            text21.setTextColor(getResources().getColor(R.color.black));
        }
    }
}