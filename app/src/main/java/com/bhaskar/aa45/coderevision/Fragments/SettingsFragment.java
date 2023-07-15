package com.bhaskar.aa45.coderevision.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhaskar.aa45.coderevision.MeFragment;
import com.bhaskar.aa45.coderevision.R;
import com.bhaskar.aa45.coderevision.uiMode.changeText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    @SuppressLint({"UseCompatLoadingForDrawables", "RestrictedApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Settings");

        LinearLayout deleteSolved = rootView.findViewById(R.id.delete_solved);
        LinearLayout deleteTried = rootView.findViewById(R.id.delete_tried);
        LinearLayout deleteWishlist = rootView.findViewById(R.id.delete_wishlist);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout parent = rootView.findViewById(R.id.parent_settings);

        changeText.textColorChange(rootView, MeFragment.isDark , getContext());
        if(!MeFragment.isDark){
            parent.setBackgroundColor(requireActivity().getResources().getColor(R.color.white));
            deleteSolved.setBackground(getResources().getDrawable(R.drawable.edittet_shape));
            deleteTried.setBackground(getResources().getDrawable(R.drawable.edittet_shape));
            deleteWishlist.setBackground(getResources().getDrawable(R.drawable.edittet_shape));
        }else{
            parent.setBackgroundColor(requireActivity().getResources().getColor(R.color.primary));
            deleteSolved.setBackground(getResources().getDrawable(R.drawable.edit_form_shape));
            deleteTried.setBackground(getResources().getDrawable(R.drawable.edit_form_shape));
            deleteWishlist.setBackground(getResources().getDrawable(R.drawable.edit_form_shape));
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        String uid = FirebaseAuth.getInstance().getUid();

        deleteSolved.setOnClickListener(v -> {
            DatabaseReference solvedRef = db.getReference().child("user").child(uid).child("Solved");
            showAlert(solvedRef,"Solved");
        });
        deleteTried.setOnClickListener(v -> {
            DatabaseReference triedRef = db.getReference().child("user").child(uid).child("Tried");
            showAlert(triedRef,"Tried");
        });
        deleteWishlist.setOnClickListener(v -> {
            DatabaseReference wishlistRef = db.getReference().child("user").child(uid).child("Wishlist");
            showAlert(wishlistRef,"Wishlist");
        });


        return rootView;
    }

    private void showAlert(DatabaseReference ref , String s){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setMessage("Do you want to delete all " + s + " problems permanently?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref.setValue(null);
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}