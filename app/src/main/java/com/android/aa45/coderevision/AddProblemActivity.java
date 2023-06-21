package com.android.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Stack;

public class AddProblemActivity extends AppCompatActivity {
    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        textView = findViewById(R.id.tv);
        Stack<Integer> x = HomeFragment.tabNo;

        int num = x.pop();
        if(num==0){
            textView.setText("Solved");
        }else if(num==1){
            textView.setText("Tried");
        }
    }
}