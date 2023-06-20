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

import com.android.aa45.coderevision.Adapters.FragPageAdapter;
import com.android.aa45.coderevision.Fragments.SolvedFragment;
import com.android.aa45.coderevision.Fragments.TriedFragment;
import com.android.aa45.coderevision.Fragments.WishlistFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    private FloatingActionButton addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //for SlideBar - solved,tried,wishlist
        TabLayout tl = view.findViewById(R.id.tabLayout);
        ViewPager vp = view.findViewById(R.id.viewPager);
        tl.setupWithViewPager(vp);
        replaceFragment(new SolvedFragment());
        FragPageAdapter adapter = new FragPageAdapter(requireActivity().getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.fragmentAdd(new SolvedFragment(), "Solved");
        adapter.fragmentAdd(new TriedFragment(), "Tried");
        adapter.fragmentAdd(new WishlistFragment(), "Wishlist");
        vp.setAdapter(adapter);

        addButton = view.findViewById(R.id.addButton);


        return view;
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager,fragment);
        fragmentTransaction.commit();
    }


}