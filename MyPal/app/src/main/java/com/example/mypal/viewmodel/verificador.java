package com.example.mypal.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mypal.R;
import com.example.mypal.viewmodel.usuario;


public class verificador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificador);
    }
    public void onButtonUsuario(View v){
        Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
        startActivity(myIntent3);
    }
}
