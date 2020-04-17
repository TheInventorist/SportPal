package com.example.mypal.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mypal.R;

public class registro extends AppCompatActivity {

    public EditText emailRegistro;
    public EditText nombreRegistro;
    public EditText telefonoRegistro;
    public EditText clave1Registro;
    public EditText clave2Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreRegistro = (EditText) findViewById(R.id.nombreRegistro);
        emailRegistro = (EditText) findViewById(R.id.emailRegistro);
        telefonoRegistro = (EditText) findViewById(R.id.telefonoRegistro);
        clave1Registro = (EditText) findViewById(R.id.clave1Registro);
        clave2Registro= (EditText) findViewById(R.id.clave2Registro);

    }
    public void onButtonVerif(View v){
        Intent myIntent2 = new Intent(getBaseContext(), verificador.class);
        startActivity(myIntent2);
    }
}
