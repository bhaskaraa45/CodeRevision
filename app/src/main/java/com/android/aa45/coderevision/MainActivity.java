package com.android.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.aa45.coderevision.Firebase.LoginActivity;
import com.android.aa45.coderevision.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    static ArrayList<String> userDetails=new ArrayList<>(); // at 0-> name , at 1-> email , at 2-> photo url

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            //To store User Details in a ArrayList
                userDetails.clear();
                userDetails.add(user.getDisplayName());
                userDetails.add(user.getEmail());
                userDetails.add(String.valueOf(user.getPhotoUrl()));

        }
    }

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //for bottom Navigation
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            int id = item.getItemId();
            int home = R.id.home;
            int me = R.id.me;
            int search = R.id.search;

            if(id==home){
                replaceFragment(new HomeFragment());
            } else if (id==search) {
                replaceFragment(new SearchFragment());
            } else if (id==me) {
                replaceFragment(new MeFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }


    public void exitApplication(){
        finish();
        System.exit(0);
    }


}
