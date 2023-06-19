package com.android.aa45.coderevision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_bottom_nev_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.home) {
            Toast.makeText(getApplicationContext(), "home Selected", Toast.LENGTH_LONG).show();
            return true;
        }else if(id==R.id.search) {
            Toast.makeText(getApplicationContext(), "search Selected", Toast.LENGTH_LONG).show();
            return true;
        }else if(id== R.id.me){
            Toast.makeText(getApplicationContext(), "me Selected", Toast.LENGTH_LONG).show();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
        }
    }
