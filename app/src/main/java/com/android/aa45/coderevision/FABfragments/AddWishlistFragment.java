package com.android.aa45.coderevision.FABfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.aa45.coderevision.R;

public class AddWishlistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentreturn
        final View rootView = inflater.inflate(R.layout.fragment_add_wishlist, container, false);

        return rootView;
    }
}