
package com.example.mypal.viewmodel;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
 public EditText emailLogin;
 public EditText claveLogin;

 private FirebaseDatabase fireBaseDataBase;
 private DatabaseReference dataBaseReference;
 private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        claveLogin = (EditText) findViewById(R.id.claveLogin);
        inicializarFireBase();
    }


    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();
        //mAuth = FirebaseAuth.getInstance();
    }


    public void onButtonClick(View v){
        Log.d("asd",emailLogin.getText().toString());
        Intent myIntent = new Intent(getBaseContext(), registro.class);
        startActivity(myIntent);


    }
    public void onButtonLogin(View v){
        Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
        startActivity(myIntent3);

    }

}
