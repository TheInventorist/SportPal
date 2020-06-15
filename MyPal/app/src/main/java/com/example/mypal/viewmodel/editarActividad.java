package com.example.mypal.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mypal.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editarActividad extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;

    public EditText nombreAct;
    public EditText DescripcionAct;

    private int integrantes;
    private String latitud;
    private String longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_actividad);

        nombreAct = (EditText) findViewById(R.id.nameEdit);
        DescripcionAct = (EditText) findViewById(R.id.descEdit);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();

    }

    public void guardarInfo(View view) {

        UserLocation ul = new UserLocation();

        Intent intentEditar = getIntent();


        integrantes = intentEditar.getIntExtra("integrantes", 0);
        latitud = intentEditar.getStringExtra("latitud");
        longitud = intentEditar.getStringExtra("longitud");



        String user = "usuarioLider";
        String desc = DescripcionAct.getText().toString();
        int integrante = integrantes;
        String nombre = nombreAct.getText().toString();


        ul.setUser(user);
        ul.setNombreActividad(nombre);
        ul.setLatitud(latitud);
        ul.setLongitud(longitud);
        ul.setDescription(desc);
        ul.setIntegrantes(integrante);

        dataBaseReference.child("Actividades").child(user).setValue(ul);

        Intent intent4 =new Intent(editarActividad.this, MapActivity.class);
        startActivity(intent4);

    }

    public void finalizarActividad(View view) {
        UserLocation ul = new UserLocation();

        Intent intentEditar = getIntent();


        integrantes = intentEditar.getIntExtra("integrantes", 0);
        latitud = intentEditar.getStringExtra("latitud");
        longitud = intentEditar.getStringExtra("longitud");



        String user = "usuarioLider";
        String desc = DescripcionAct.getText().toString();
        int integrante = 0;
        String nombre = nombreAct.getText().toString();


        ul.setUser(user);
        ul.setNombreActividad(nombre);
        ul.setLatitud(latitud);
        ul.setLongitud(longitud);
        ul.setDescription(desc);
        ul.setIntegrantes(integrante);

        dataBaseReference.child("Actividades").child(user).setValue(ul);

        Intent intent3 =new Intent(editarActividad.this, MapActivity.class);
        startActivity(intent3);
    }
}
