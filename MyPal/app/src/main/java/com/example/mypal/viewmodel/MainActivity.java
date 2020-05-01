
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

import android.os.Bundle;
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

                String email = emailLogin.getText().toString();
                String pass = claveLogin.getText().toString();

                login(email, pass);
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
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();

                            if(chekearVerificacionEmail() ==  1){
                                if(isNew){
                                    Intent myIntent3 = new Intent(getBaseContext(), usuario.class);
                                    startActivity(myIntent3);
                                }
                                else{
                                    // aqui va el menu principal
                                }

                            }
                            else{
                                Toast.makeText(MainActivity.this, "valide su cuenta antes de entrar", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "El nombre de la cuenta y/o la contraseña que has introducido son incorrectos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private int chekearVerificacionEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            //finish();
            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            return 1;
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
            return 0;
        }
    }





    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

}
