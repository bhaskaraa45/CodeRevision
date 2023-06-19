package com.android.aa45.coderevision;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private TabLayout tl;
    private ViewPager vp;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tl = view.findViewById(R.id.tabLayout);
        vp=view.findViewById(R.id.viewPager);

        tl.setupWithViewPager(vp);

        replaceFragment(new SolvedFragment());

        FragPageAdapter adapter = new FragPageAdapter(requireActivity().getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.fragmentAdd(new SolvedFragment(), "Solved");
        adapter.fragmentAdd(new TriedFragment(), "Tried");
        adapter.fragmentAdd(new WishlistFragment(), "Wishlist");
        vp.setAdapter(adapter);

        return view;
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager,fragment);
        fragmentTransaction.commit();
    }


}