package com.android.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Stack;

public class AddProblemActivity extends AppCompatActivity {

    private Button datePickerButton;
    private DatePickerDialog datePickerDialog;
    private String selectedDate;
    private int selectedDifficulty= -1;
    private final String[] diffItems = {"Basic", "Easy" , "Medium" , "Hard"};
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        TextView textView1 = findViewById(R.id.text_headline1);
        TextView textView2 = findViewById(R.id.text_headline2);
        TextView title = findViewById(R.id.title);
        TextView link = findViewById(R.id.link);
        TextView topic = findViewById(R.id.topic);
        Button addButton = findViewById(R.id.add_problem);
        AutoCompleteTextView difficultyLevel = findViewById(R.id.difficulty_level);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,R.layout.difficulty_list,diffItems);

        //set difficulty options
        difficultyLevel.setAdapter(stringArrayAdapter);
        difficultyLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedDifficulty = position;
            }
        });

        //select date
        initDatePicker();
        datePickerButton = findViewById(R.id.date_picker);
        selectedDate = getTodaysDate();
        datePickerButton.setText(selectedDate);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


        Stack<Integer> tab = HomeFragment.tabNo;
        int selectedTab = tab.pop();
        String name = MainActivity.userDetails.get(0);
        String[] splitName;
        String firstName;
        if(name!=null) {
            splitName = name.split(" ");
            firstName = splitName[0] ;
        }else{
            firstName = "";
        }

        switch (selectedTab){
            case 0 :
                textView1.setText("🎉 Wow! Solved a new problem");
                textView2.setText("Keep it up " + firstName +"!");
                break;
            case 1:
                textView1.setText("\uD83D\uDC4F Wow, You Tried to solve one");
                textView2.setText("question, Now Save it and try again later");
                break;
            case 2:
                textView1.setText("Add Problem to wishlist");
                textView2.setText("To solve it later \uD83D\uDE4C");
                break;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionTitle = title.getText().toString();
                String questionLink = link.getText().toString();
                String questionTag = topic.getText().toString();
                String date = selectedDate;
                int diff = selectedDifficulty;
                
                if(diff==-1 || questionLink==null || questionTag==null){
                    Toast.makeText(AddProblemActivity.this, "Please fill the form appropriately", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddProblemActivity.this, diff + "Problem Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });




    }

    @SuppressLint("SimpleDateFormat")
    public String getTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month + 1,year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                selectedDate = makeDateString(day,month +1 , year);
                datePickerButton.setText(selectedDate);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return (day + "-" + month + "-" + year);
    }

}