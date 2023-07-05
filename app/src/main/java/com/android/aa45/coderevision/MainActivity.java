package com.android.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.aa45.coderevision.Firebase.LoginActivity;
import com.android.aa45.coderevision.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Context> MainActContext = new ArrayList<>();

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

        MainActContext.add(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        if(!NetworkIsConnected()){
            Toast.makeText(this, "Failed To Connect Server", Toast.LENGTH_LONG).show();
        }

        //for dark mode
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean dark = sharedPref.getBoolean("dark",true);
        if(dark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //for bottom Navigation
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            int id = item.getItemId();
            int home = R.id.home;
            int me = R.id.me;
            int search = R.id.search;
            int topics = R.id.tags;

            if(id==home){
                replaceFragment(new HomeFragment());
            } else if (id==search) {
                replaceFragment(new SearchFragment());
            } else if (id==me) {
                replaceFragment(new MeFragment());
            } else if (id==topics) {
                replaceFragment(new TopicsFragment());
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

    private boolean NetworkIsConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
