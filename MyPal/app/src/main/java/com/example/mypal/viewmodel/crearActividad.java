package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class crearActividad extends AppCompatActivity{

    public EditText nombreAct;
    public EditText DescripcionAct;

    private FirebaseAuth mAuth;
    private FirebaseDatabase fireBaseDataBase;
    private DatabaseReference dataBaseReference;
    public LatLng latLng;
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    public String latitud;
    public String longitud;
    public String nombreActividad;
    public String descripcionActividad;
    public int cantidadIntegrantes;
    public int actividadCreada = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_actividad);

        nombreAct = (EditText) findViewById(R.id.nombreAct);
        DescripcionAct = (EditText) findViewById(R.id.descAct);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        fireBaseDataBase = FirebaseDatabase.getInstance();
        dataBaseReference = fireBaseDataBase.getReference();



/*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Actividades").child("usuarioLider");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
    SelectSQL();
    }

    protected void SelectSQL(){

        SQLiteHelper userbdbh = new SQLiteHelper(this,"DBAdministracion",null,1); //Cambiar version para gatillar onUpgrade
        SQLiteDatabase db = userbdbh.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM datosActividad WHERE idDato = 1",null);
        cursor.moveToFirst();
        latitud = cursor.getString(1);
        longitud = cursor.getString(2);
        cursor.close();
    }

/*
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){

        }
    }*/





    private void crearDatosGps(){

        UserLocation ul = new UserLocation();
        User u = new User();
        String user = "usuarioLider";
        String desc = DescripcionAct.getText().toString();
        int integrante = 1;
        MapActivity clase = new MapActivity();
        String nombre = nombreAct.getText().toString();

        nombreActividad = nombre;
        descripcionActividad = desc;
        cantidadIntegrantes = integrante;

        //Log.d("usuario","se tiene: " + latLng);

        //if(mAuth.getCurrentUser().getUid().toString() == null){
        //    user = "usuarioLider";
        //}
        //else{
        //    user = mAuth.getCurrentUser().getUid().toString();
        //}
        ul.setUser(user);
        ul.setNombreActividad(nombre);
        ul.setLatitud(latitud);
        ul.setLongitud(longitud);
        ul.setDescription(desc);
        ul.setIntegrantes(integrante);
        //ul.setTimeStramp(time);

        dataBaseReference.child("Actividades").child(user).setValue(ul);

        //Log.d("usuario","se tiene: " + user + latLng); //configurando los datos gps y cosas para crear la actividad en un punto latlng y se lo paso para que crear actividad lo suba a DB
    }

    private void volverAlMapa(){
        Intent intent =new Intent(crearActividad.this, MapActivity.class);
        /*intent.putExtra("estado",actividadCreada);
        intent.putExtra("nombre",nombreActividad);
        intent.putExtra("descrip",descripcionActividad);
        intent.putExtra("integrantes",cantidadIntegrantes);
        intent.putExtra("latitud",Double.valueOf(latitud));
        intent.putExtra("longitud",Double.valueOf(longitud));*/
        startActivity(intent);
    }


    public void crearActividad(View view) {
        crearDatosGps();
        CrearPuntoActividad();
        volverAlMapa();

    }

    private void CrearPuntoActividad() {
        actividadCreada = 1;



    }


}
