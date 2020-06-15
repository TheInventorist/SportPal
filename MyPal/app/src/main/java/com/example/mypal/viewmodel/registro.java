package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.UUID;

public class registro extends AppCompatActivity implements View.OnClickListener {

    public EditText emailRegistro;
    public EditText nombreRegistro;
    public EditText telefonoRegistro;
    public EditText clave1Registro;
    public EditText clave2Registro;
    public static int nuevo = 0;

    private FirebaseAuth mAuth;
    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate;

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
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();



        mDisplayDate = (TextView)findViewById(R.id.fech_nacimiento);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(registro.this, android.R.style.Theme_DeviceDefault_Light,mDateSetListener,anio,mes,dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                mes = mes +1;
                String fecha = dia + "/" + mes + "/" + anio;
                mDisplayDate.setText(fecha);

            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //obtiene el usuario actual
    }

    public void crearUsuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            enviarEmailVerificacion();
                            crearDatosDePerfil();

                            Toast.makeText(registro.this, "Usuario Creado, verifique su email", Toast.LENGTH_LONG).show();
                            nuevo = 1;

                            Intent myIntent2 = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(myIntent2);
                        } else {
                            Toast.makeText(registro.this, "error de autenticacion",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private  void crearDatosDePerfil(){

        String fecha = mDisplayDate.getText().toString();
        String nombre = nombreRegistro.getText().toString();
        String telefono = telefonoRegistro.getText().toString();
        User u = new User();
        u.setuId(mAuth.getCurrentUser().getUid().toString());
        u.setNombre(nombre);
        u.setTelefono(telefono);

        u.setFecha(fecha);
        dataBaseReference.child("Usuarios").child(u.getuId()).setValue(u);


    }


    private void enviarEmailVerificacion(){
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(registro.this, MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(registro.this, "Error al enviar mail de confirmacion, compruebe su conexion", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }



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