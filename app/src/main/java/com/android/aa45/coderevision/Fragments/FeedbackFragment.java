package com.android.aa45.coderevision.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.aa45.coderevision.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class FeedbackFragment extends Fragment {

    private EditText feedbackMassage;
    private Button submit;
    private RatingBar rating;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feedback, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Feedback");

        submit = view.findViewById(R.id.feedbackSubmit);
        rating = view.findViewById(R.id.starRating);
        feedbackMassage = view.findViewById(R.id.feedbackText);

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO){
            feedbackMassage.setBackground(getResources().getDrawable(R.drawable.edittet_shape));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                float r = rating.getRating();
                msg += feedbackMassage.getText().toString();
                if(r==0 && msg.equals("")){
                    Toast.makeText(getContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                }else{
                    addFeedback(msg,r);
                    feedbackMassage.setText("");
                    rating.setRating(0);
                    Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void addFeedback(String text,Float rating){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference myRef = db.getReference().child("user").child(uid).child("Feedback"); //root->user->uid->feedback

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int slNo = (int) snapshot.getChildrenCount();
                String sl = ""+ (slNo+1);
                //root->user->uid->feedback->sl
                DatabaseReference finalRef = myRef.child(sl);
                //set data
                finalRef.child("Massage").setValue(text);
                finalRef.child("Rating").setValue(rating);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}