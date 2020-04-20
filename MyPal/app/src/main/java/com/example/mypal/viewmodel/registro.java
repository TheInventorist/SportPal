package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class registro extends AppCompatActivity implements View.OnClickListener {

    public EditText emailRegistro;
    public EditText nombreRegistro;
    public EditText telefonoRegistro;
    public EditText clave1Registro;
    public EditText clave2Registro;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreRegistro = (EditText) findViewById(R.id.nombreRegistro);
        emailRegistro = (EditText) findViewById(R.id.emailRegistro);
        telefonoRegistro = (EditText) findViewById(R.id.telefonoRegistro);
        clave1Registro = (EditText) findViewById(R.id.clave1Registro);
        clave2Registro= (EditText) findViewById(R.id.clave2Registro);

        findViewById(R.id.btnRegistro).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void crearUsuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("registro", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendVerificationEmail();
                            Log.d("registro", "se debio haber enviado el email");
                            //updateUI(user);
                            Toast.makeText(registro.this, "Usuario Creado", Toast.LENGTH_LONG).show();
                            Intent myIntent2 = new Intent(getBaseContext(), verificador.class);
                            startActivity(myIntent2);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("registro", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }




    private void sendVerificationEmail()
    {
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(registro.this, MainActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }





    //private void inicializarFireBase() {
    //    FirebaseApp.initializeApp(this);
    //    fireBaseDataBase = FirebaseDatabase.getInstance();
    //    dataBaseReference = fireBaseDataBase.getReference();
    //}



    //public void onButtonVerif(View v){
    //
    //    String nombre = nombreRegistro.getText().toString();
    //    String email = emailRegistro.getText().toString();
    //    String telefono = telefonoRegistro.getText().toString();
    //    String clave1 = clave1Registro.getText().toString();
    //    String clave2 = clave2Registro.getText().toString();
    //    if(clave1.equals(clave2)){
    //        //User u = new User();

            //u.setuId(UUID.randomUUID().toString());
            //u.setNombre(nombre);
            //u.setCorreo(email);
            //u.setTelefono(telefono);
            //u.setPassword(clave1);
            //dataBaseReference.child("Usuarios").child(u.getuId()).setValue(u);
            //Toast.makeText(this, "Verifique su email", Toast.LENGTH_LONG).show();


           //crearUsuario(email,clave1);

            //Toast.makeText(this, "Usuario Creado", Toast.LENGTH_LONG).show();
            //Intent myIntent2 = new Intent(getBaseContext(), verificador.class);
            //startActivity(myIntent2);
        //}
        //else{
         //   Toast.makeText(this, "Las claves no son iguales", Toast.LENGTH_LONG).show();
        //}




    //}


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnRegistro:
                String email = emailRegistro.getText().toString();
                String clave1 = clave1Registro.getText().toString();
                String clave2 = clave2Registro.getText().toString();
                if(clave1.equals(clave2)){

                    crearUsuario(email,clave1);
                }
                else{
                    Toast.makeText(this, "Las claves no son iguales", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
