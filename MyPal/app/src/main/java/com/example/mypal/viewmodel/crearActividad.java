package com.example.mypal.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class crearActividad extends AppCompatActivity {

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
    }
/*
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){

        }
    }*/


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        Log.d("mapas","prepara el mapa");
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                try {
                    Log.d("mapas","logra el try");
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //mMap.addMarker(new MarkerOptions().position(latLng).title("hola"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //Toast.makeText(MapActivity.this,  "ubicacion cambio", Toast.LENGTH_LONG).show(); //Correcto
                } catch (SecurityException e) {
                    //Log.d("mapas","pasa al catch");
                    e.printStackTrace();
                }
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d("mapas","continua");
        try {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }













    private void crearDatosGps(){

        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        UserLocation ul = new UserLocation();
        User u = new User();
        String user = "usuarioLider";
        String desc = DescripcionAct.getText().toString();
        int integrante = 1;
        MapActivity clase = new MapActivity();
        String nombre = nombreAct.getText().toString();


        Log.d("usuario","se tiene: " + latLng);

        //if(mAuth.getCurrentUser().getUid().toString() == null){
        //    user = "usuarioLider";
        //}
        //else{
        //    user = mAuth.getCurrentUser().getUid().toString();
        //}
        ul.setUser(user);
        ul.setNombreActividad(nombre);
        ul.setLatLng(latLng);
        ul.setDescription(desc);
        ul.setIntegrantes(integrante);
        //ul.setTimeStramp(time);

        dataBaseReference.child("Actividades").child(user).setValue(ul);

        //Log.d("usuario","se tiene: " + user + latLng); //configurando los datos gps y cosas para crear la actividad en un punto latlng y se lo paso para que crear actividad lo suba a DB
    }

    private void volverAlMapa(){
        Intent intent =new Intent(crearActividad.this, MapActivity.class);
        startActivity(intent);
    }


    public void crearActividad(View view) {
        crearDatosGps();
        volverAlMapa();
    }
}
