package com.example.mypal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
    public void onButtonVerif(View v){
        Intent myIntent2 = new Intent(getBaseContext(), verificador.class);
        startActivity(myIntent2);
    }
}
