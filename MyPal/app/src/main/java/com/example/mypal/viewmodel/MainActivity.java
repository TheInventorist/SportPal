
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnCrearcuenta).setOnClickListener(this);

        inicializarFireBase();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) { //verifica si el usuario esta logeado
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                Intent myIntent1 = new Intent(getBaseContext(), usuario.class);
                startActivity(myIntent1);


            break;


            case R.id.btnCrearcuenta:
                Intent myIntent = new Intent(getBaseContext(), registro.class);
                startActivity(myIntent);
                break;
        }
    }






    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            chekearVerificacionEmail();

                            Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
                            startActivity(myIntent3);
                        } else {
                            Toast.makeText(MainActivity.this, "fallo de autenticacion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void chekearVerificacionEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            finish();
            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
        }
    }





    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

}
