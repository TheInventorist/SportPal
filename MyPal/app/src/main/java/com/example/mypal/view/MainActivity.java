package com.example.mypal.view;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;

import com.example.mypal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(), registro.class);
        startActivity(myIntent);
    }
}
