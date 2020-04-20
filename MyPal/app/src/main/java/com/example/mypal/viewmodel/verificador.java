package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class verificador extends AppCompatActivity {

    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificador);
        inicializarFireBase();
    }
    public void onButtonUsuario(View v){
        Log.d("asd","enviar codigo");
        enviarEmail();
        Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
        startActivity(myIntent3);
    }


    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();
    }

    private void enviarEmail(){
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(verificador.this,"registrado, enviando email de verificacion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}
