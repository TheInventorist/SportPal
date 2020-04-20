package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class registro extends AppCompatActivity {

    public EditText emailRegistro;
    public EditText nombreRegistro;
    public EditText telefonoRegistro;
    public EditText clave1Registro;
    public EditText clave2Registro;

    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreRegistro = (EditText) findViewById(R.id.nombreRegistro);
        emailRegistro = (EditText) findViewById(R.id.emailRegistro);
        telefonoRegistro = (EditText) findViewById(R.id.telefonoRegistro);
        clave1Registro = (EditText) findViewById(R.id.clave1Registro);
        clave2Registro= (EditText) findViewById(R.id.clave2Registro);
        inicializarFireBase();

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
                            Toast.makeText(registro.this,"registrado, enviando email de verificacion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void onButtonVerif(View v){

        String nombre = nombreRegistro.getText().toString();
        String email = emailRegistro.getText().toString();
        String telefono = telefonoRegistro.getText().toString();
        String clave1 = clave1Registro.getText().toString();
        String clave2 = clave2Registro.getText().toString();

        if(clave1.equals(clave2)){
            User u = new User();

            u.setuId(UUID.randomUUID().toString());
            u.setNombre(nombre);
            u.setCorreo(email);
            u.setTelefono(telefono);
            u.setPassword(clave1);
            dataBaseReference.child("Usuarios").child(u.getuId()).setValue(u);
            Toast.makeText(this, "Verifique su email", Toast.LENGTH_LONG).show();

            Intent myIntent2 = new Intent(getBaseContext(), verificador.class);
            startActivity(myIntent2);
        }
        else{
            Toast.makeText(this, "Las claves no son iguales", Toast.LENGTH_LONG).show();
        }



    }




}
