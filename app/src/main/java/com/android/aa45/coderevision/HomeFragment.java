package com.android.aa45.coderevision;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.aa45.coderevision.Adapters.FragPageAdapter;
import com.android.aa45.coderevision.FABfragments.AddSolvedFragment;
import com.android.aa45.coderevision.Fragments.SolvedFragment;
import com.android.aa45.coderevision.Fragments.TriedFragment;
import com.android.aa45.coderevision.Fragments.WishlistFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;
import java.util.Stack;

public class HomeFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Home");

        //for SlideBar - solved,tried,wishlist
        TabLayout tl = view.findViewById(R.id.tabLayout);
        ViewPager vp = view.findViewById(R.id.viewPager);
        tl.setupWithViewPager(vp);
        replaceFragment(new SolvedFragment());
        FragPageAdapter adapter = new FragPageAdapter(requireActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.fragmentAdd(new SolvedFragment(), "Solved");
        adapter.fragmentAdd(new TriedFragment(), "Tried");
        adapter.fragmentAdd(new WishlistFragment(), "Wishlist");
        vp.setAdapter(adapter);

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

       return view;
    }
    static Stack<Integer> tabNo = new Stack<>();

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.viewPager,fragment);
        fragmentTransaction.commit();
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_button);

        LinearLayout addSolved = dialog.findViewById(R.id.addSolved);
        LinearLayout addTried = dialog.findViewById(R.id.addTried);
        LinearLayout addWishlist = dialog.findViewById(R.id.addWishlist);
        addSolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(!tabNo.isEmpty()){
                    tabNo.empty();
                }
                tabNo.add(0);
                Intent intent = new Intent(getContext(),AddProblemActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"solved is Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        addTried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(!tabNo.isEmpty()){
                    tabNo.empty();
                }
                tabNo.add(1);
                Intent intent = new Intent(getContext(),AddProblemActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"tried is Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        addWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(!tabNo.isEmpty()){
                    tabNo.empty();
                }
                tabNo.add(2);
                Intent intent = new Intent(getContext(),AddProblemActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"wishlist is Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.FloatingActionButton;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}