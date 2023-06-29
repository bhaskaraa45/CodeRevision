package com.android.aa45.coderevision;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private RelativeLayout settings;
    private RelativeLayout feedback;
    private RelativeLayout share;
    private RelativeLayout logout;
    private RelativeLayout about;
    private RelativeLayout bugReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Profile");

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_me, container, false);

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

        ArrayList<String> userData = MainActivity.userDetails;
        if(userData.size()>0){
            usersName.setText(userData.get(0));
            emailId.setText(userData.get(1));
            Glide.with(requireActivity()).load(userData.get(2)).into(profilePic);
        }

        settings = rootView.findViewById(R.id.settings);
        feedback = rootView.findViewById(R.id.feedback);
        share = rootView.findViewById(R.id.share);
        logout = rootView.findViewById(R.id.logout);
        bugReport = rootView.findViewById(R.id.bugReport);
        about = rootView.findViewById(R.id.about);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Feedback", Toast.LENGTH_SHORT).show();
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
                signOut();
                Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });
        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Report a bug", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "About", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        startActivity(new Intent(getContext(),MainActivity.class));
        requireActivity().finish();

    }
}