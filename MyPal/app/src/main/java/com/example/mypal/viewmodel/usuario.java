package com.example.mypal.viewmodel;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mypal.R;

public class usuario extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usuario);
        findViewById(R.id.btnPerfil).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnPerfil:
                Intent myIntent1 = new Intent(getBaseContext(), MapActivity.class);
                startActivity(myIntent1);
        }
    }
}

