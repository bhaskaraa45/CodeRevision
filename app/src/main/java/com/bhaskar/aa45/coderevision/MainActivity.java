package com.bhaskar.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bhaskar.aa45.coderevision.Firebase.LoginActivity;
import com.bhaskar.aa45.coderevision.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Context> MainActContext = new ArrayList<>();
    public static boolean dark;
    public static  BottomNavigationView navigationView ;
    public static int white;
    public static int black;
    public static int grey;
    public static int grey2;
    public static int sec;

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

    @SuppressLint("StaticFieldLeak")
    public static ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

        MainActContext.add(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        if (!NetworkIsConnected()) {
            Toast.makeText(this, "Failed To Connect Server", Toast.LENGTH_LONG).show();
        }

        navigationView=findViewById(R.id.bottomNavigationView);
        white = getResources().getColor(R.color.white);
        black = getResources().getColor(R.color.black);
        grey = getResources().getColor(R.color.grey_bg);
        grey2 = getResources().getColor(R.color.grey_bg_login);
        sec = getResources().getColor(R.color.secondary);

        //for bottom Navigation
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
    public static void changeBottomNavColor(boolean what){
//        Menu
        if(what){
            binding.bottomNavigationView.setItemTextColor(ColorStateList.valueOf(white));
            binding.bottomNavigationView.setBackgroundColor(black);
            binding.bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(grey));
        }else{
            binding.bottomNavigationView.setBackgroundColor(white);
            binding.bottomNavigationView.setItemTextColor(ColorStateList.valueOf(black));
            binding.bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(grey2));
        }
    }

}
