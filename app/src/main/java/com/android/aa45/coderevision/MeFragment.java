package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aa45.coderevision.Fragments.AboutFragment;
import com.android.aa45.coderevision.Fragments.FeedbackFragment;
import com.android.aa45.coderevision.Fragments.SettingsFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Profile");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        mAuth = FirebaseAuth.getInstance();


        //Add profile photo , name and email
        ImageView profilePic = (ImageView) rootView.findViewById(R.id.profilePic);
        TextView usersName = (TextView) rootView.findViewById(R.id.profile_name);
        TextView emailId = (TextView) rootView.findViewById(R.id.email);
        emailId.setTextColor(getResources().getColor(R.color.grey_text));

        ArrayList<String> userData = MainActivity.userDetails;
        if(userData.size()>0){
            usersName.setText(userData.get(0));
            emailId.setText(userData.get(1));
            Glide.with(requireActivity()).load(userData.get(2)).into(profilePic);
        }

        emailId.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("code",userData.get(1));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Email Copied", Toast.LENGTH_SHORT).show();
        });


        RelativeLayout settings = rootView.findViewById(R.id.settings);
        RelativeLayout feedback = rootView.findViewById(R.id.feedback);
        RelativeLayout share = rootView.findViewById(R.id.share);
        RelativeLayout logout = rootView.findViewById(R.id.logout);
        RelativeLayout bugReport = rootView.findViewById(R.id.report);
        RelativeLayout about = rootView.findViewById(R.id.about);
        Switch notification = rootView.findViewById(R.id.notificationSwitch);
        Switch darkMode = rootView.findViewById(R.id.darkModeSwitch);

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
        boolean dark = sharedPref.getBoolean("dark",true);
        boolean notify = sharedPref.getBoolean("notify",true);

        darkMode.setChecked(dark);
        notification.setChecked(notify);

        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                editor.putBoolean("dark",isChecked);
                editor.apply();
            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editor.putBoolean("notify",isChecked);
                editor.apply();
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SettingsFragment());

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FeedbackFragment());
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Download CodeRevision app & save solved problems to revise it\n\n https://bit.ly/code-revision-aa45");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent();
                String text = "mailto:" + Uri.encode("bhaskarmandal369@gmail.com") + "?subject=" +
                        Uri.encode("Code-Revision: Bug Report by " + userData.get(0) ) ;
                Uri uri = Uri.parse(text);
                email.setData(uri);
                email.setPackage("com.google.android.gm");
                startActivity(email);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AboutFragment());
            }
        });

        return rootView;
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Do you want to log out?");
        builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                signOut();
                Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        startActivity(new Intent(getContext(),MainActivity.class));
        requireActivity().finish();

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(false);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}